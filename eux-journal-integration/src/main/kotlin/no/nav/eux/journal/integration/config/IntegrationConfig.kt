package no.nav.eux.journal.integration.config

import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate

@Configuration
class IntegrationConfig(
    private val restTemplateBuilder: RestTemplateBuilder,
    private val authorizedClientManager: OAuth2AuthorizedClientManager
) {

    @Bean
    fun euxOppgaveRestTemplate() = restTemplate("eux-oppgave-credentials")

    @Bean
    fun euxNavRinasakRestTemplate() = restTemplate("eux-nav-rinasak-credentials")

    @Bean
    fun safRestTemplate() = restTemplate("saf-credentials")

    @Bean
    fun dokarkivRestTemplatePrivateKeyJwt() = restTemplate("dokarkiv-private-key-jwt-credentials")

    @Bean
    fun dokarkivRestTemplateClientSecretBasic() = restTemplate("dokarkiv-client-secret-basic-credentials")

    private fun restTemplate(registrationId: String): RestTemplate =
        restTemplateBuilder
            .additionalInterceptors(bearerTokenInterceptor(registrationId))
            .build()

    private fun bearerTokenInterceptor(registrationId: String) =
        ClientHttpRequestInterceptor { request, body, execution ->
            val authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(registrationId)
                .principal("system")
                .build()
            authorizedClientManager.authorize(authorizeRequest)?.accessToken?.tokenValue?.let {
                request.headers.setBearerAuth(it)
            }
            execution.execute(request, body)
        }
}

fun RestTemplate.restClient(): RestClient = RestClient.create(this)
fun RestTemplate.post() = restClient().post()
fun RestTemplate.get() = restClient().get()
fun RestTemplate.patch() = restClient().patch()

