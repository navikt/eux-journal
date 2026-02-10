package no.nav.eux.journal.integration.config

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate

@Component
class DualDokarkivRestTemplate(
    private val dokarkivRestTemplatePrivateKeyJwt: RestTemplate,
    private val dokarkivRestTemplateClientSecretBasic: RestTemplate,
) {
    private val jwt: Jwt?
        get() = SecurityContextHolder.getContext().authentication?.principal as? Jwt

    private val hasNavIdent: Boolean
        get() = jwt?.getClaim<String>("NAVident") != null

    fun restTemplate(): RestTemplate =
        if (hasNavIdent) dokarkivRestTemplateClientSecretBasic else dokarkivRestTemplatePrivateKeyJwt

    fun restClient(): RestClient = RestClient.create(restTemplate())
    fun post() = restClient().post()
    fun patch() = restClient().patch()
    fun get() = restClient().get()
}
