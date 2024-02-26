package no.nav.eux.journal.integration.client.eux.oppgave

import no.nav.eux.journal.integration.config.post
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class EuxOppgaveClient(
    @Value("\${endpoint.euxoppgave}")
    val euxOppgaveUrl: String,
    val euxOppgaveRestTemplate: RestTemplate
) {

    fun tildelEnhetsnr(journalpostId: String, enhetsnr: String, kommentar: String) {
        euxOppgaveRestTemplate
            .post()
            .uri("${euxOppgaveUrl}/api/v1/oppgaver/tildelEnhetsnr")
            .body(TildelEnhetsnr(
                journalpostId = journalpostId,
                tildeltEnhetsnr = enhetsnr,
                kommentar = OppgavePatchKommentar(tekst = kommentar)
            ))
            .contentType(APPLICATION_JSON)
            .accept(MediaType.ALL)
            .retrieve()
            .toBodilessEntity()
    }
}
