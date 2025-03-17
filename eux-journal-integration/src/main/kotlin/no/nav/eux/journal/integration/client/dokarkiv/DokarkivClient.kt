package no.nav.eux.journal.integration.client.dokarkiv

import no.nav.eux.journal.integration.config.DualDokarkivRestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException

@Component
class DokarkivClient(
    @Value("\${endpoint.dokarkiv}")
    val dokarkivUrl: String,
    val dokarkivRestTemplate: DualDokarkivRestTemplate
) {

    val uri: String by lazy { "$dokarkivUrl/rest/journalpostapi/v1/journalpost" }

    fun settStatusAvbryt(journalpostId: String) = tryWithDokarkivErrorHandling{
        dokarkivRestTemplate
            .patch()
            .uri("${uri}/${journalpostId}/feilregistrer/settStatusAvbryt")
            .accept(MediaType.ALL)
            .retrieve()
            .toBodilessEntity()
    }

    fun ferdigstill(journalpostId: String) = tryWithDokarkivErrorHandling {
        dokarkivRestTemplate
            .patch()
            .uri("${uri}/${journalpostId}/ferdigstill")
            .body(FerdigstillBody())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .retrieve()
            .toBodilessEntity()
    }

    data class FerdigstillBody(
        val journalfoerendeEnhet: String = "9999"
    )

    private inline fun <T> tryWithDokarkivErrorHandling(kall: () -> T): T =
        try {
            kall()
        } catch (e: HttpClientErrorException) {
            if (e.statusCode == BAD_REQUEST) {
                val oppgaveUgyldigRequest = e.getResponseBodyAs(DokarkivErrorResponse::class.java)!!
                throw DokarkivBadRequestException(oppgaveUgyldigRequest)
            } else {
                throw e
            }
        }

}
