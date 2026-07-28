package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.ferdigstillUrl
import org.junit.jupiter.api.Test

class JournalposterApiFeilmeldingTest : AbstractApiImplTest() {

    @Test
    fun `PATCH ferdigstill - dokarkiv bad request - 400`() {
        restTestClient
            .patch()
            .uri(ferdigstillUrl, "453802640")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.type").isEqualTo("about:blank")
            .jsonPath("$.title").isEqualTo("Bad Request")
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.detail").isEqualTo(
                "Kunne ikke ferdigstille journalpost med journalpostId=453802640. " +
                        "Journalposten mangler følgende felter: avsenderMottaker.navn"
            )
            .jsonPath("$.instance")
            .isEqualTo("/rest/journalpostapi/v1/journalpost/453802640/ferdigstill")
            .jsonPath("$.message").isEqualTo(
                "Kunne ikke ferdigstille journalpost med journalpostId=453802640. " +
                        "Journalposten mangler følgende felter: avsenderMottaker.navn"
            )
            .jsonPath("$.error").isEqualTo("Bad Request")
            .jsonPath("$.path")
            .isEqualTo("/rest/journalpostapi/v1/journalpost/453802640/ferdigstill")
    }
}
