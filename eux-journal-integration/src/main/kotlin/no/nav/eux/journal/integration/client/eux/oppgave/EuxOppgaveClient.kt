package no.nav.eux.journal.integration.client.eux.oppgave

import no.nav.eux.journal.integration.config.patch
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class EuxOppgaveClient(
    @Value("\${endpoint.euxoppgave}")
    val euxOppgaveUrl: String,
    val euxOppgaveRestTemplate: RestTemplate
) {

    fun tildelEnhetsnr(enhetsnr: String){
        euxOppgaveRestTemplate
            .patch()
            .uri("${euxOppgaveUrl}/api/v1/oppgave/tildelEnhetsnr")
            .accept(MediaType.ALL)
            .retrieve()
            .toBodilessEntity()
    }
}
