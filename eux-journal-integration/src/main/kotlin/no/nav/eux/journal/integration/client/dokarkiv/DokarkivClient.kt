package no.nav.eux.journal.integration.client.dokarkiv

import no.nav.eux.journal.integration.config.DualDokarkivRestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class DokarkivClient(
    @Value("\${endpoint.dokarkiv}")
    val dokarkivUrl: String,
    val dokarkivRestTemplate: DualDokarkivRestTemplate
) {

    val uri: String by lazy { "$dokarkivUrl/rest/journalpostapi/v1/journalpost" }

    fun settStatusAvbryt(journalpostId: String) {
        dokarkivRestTemplate
            .patch()
            .uri("${uri}/${journalpostId}/feilregistrer/settStatusAvbryt")
            .accept(MediaType.ALL)
            .retrieve()
            .toBodilessEntity()
    }
}
