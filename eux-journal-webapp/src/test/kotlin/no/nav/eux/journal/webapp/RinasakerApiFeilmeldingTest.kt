package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.journalposterFeilregistrerUrl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RinasakerApiFeilmeldingTest : AbstractApiImplTest() {

    @Test
    fun `POST feilregistrer - SAF feil - 400`() {
        restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444523")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors[0]").isEqualTo("SAF kunne ikke hente journalpost")

        assertThat(lagredeFeilregistreringer()).isEmpty()
    }
}
