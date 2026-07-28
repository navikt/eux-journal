package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.journalposterFeilregistrerUrl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RinasakerApiValidationTest : AbstractApiImplTest() {

    @Test
    fun `POST feilregistrer - ikke autentisert - 401`() {
        restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444520")
            .exchange()
            .expectStatus().isUnauthorized

        assertNoSideEffects()
    }

    @Test
    fun `POST feilregistrer - ugyldig rinasakId - 400`() {
        restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "ugyldig")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isBadRequest

        assertNoSideEffects()
    }

    private fun assertNoSideEffects() {
        assertThat(requestBodies.paths()).isEmpty()
        assertThat(lagredeFeilregistreringer()).isEmpty()
    }
}
