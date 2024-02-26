package no.nav.eux.journal.integration.config

import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate

@Component
class DualDokarkivRestTemplate(
    val tokenValidationContextHolder: TokenValidationContextHolder,
    val dokarkivRestTemplatePrivateKeyJwt: RestTemplate,
    val dokarkivRestTemplateClientSecretBasic: RestTemplate,
) {

    fun restClient() = RestClient.create(restTemplate())

    fun post() = restClient().post()

    fun patch() = restClient().patch()

    fun get() = restClient().get()

    fun restTemplate() =
        when (contextHasNavIdent()) {
            true -> dokarkivRestTemplateClientSecretBasic
            false -> dokarkivRestTemplatePrivateKeyJwt
        }

    fun contextHasNavIdent(): Boolean =
        tokenValidationContextHolder
            .getTokenValidationContext()
            .firstValidToken
            ?.jwtTokenClaims
            ?.get("NAVident") != null
}
