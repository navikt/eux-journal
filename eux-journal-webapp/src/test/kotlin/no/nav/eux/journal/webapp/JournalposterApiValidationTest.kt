package no.nav.eux.journal.webapp

import no.nav.eux.journal.openapi.model.SettStatusAvbrytRequestOpenApiType
import no.nav.eux.journal.webapp.common.ferdigstillUrl
import no.nav.eux.journal.webapp.common.settStatusAvbrytUrl
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON

class JournalposterApiValidationTest : AbstractApiImplTest() {

    @Test
    fun `POST settStatusAvbryt - ikke autentisert - 401`() {
        restTestClient
            .post()
            .uri(settStatusAvbrytUrl)
            .body(SettStatusAvbrytRequestOpenApiType(listOf("453802638")))
            .exchange()
            .expectStatus().isUnauthorized

        assertNoDownstreamRequests()
    }

    @Test
    fun `PATCH ferdigstill - ikke autentisert - 401`() {
        restTestClient
            .patch()
            .uri(ferdigstillUrl, "453802638")
            .exchange()
            .expectStatus().isUnauthorized

        assertNoDownstreamRequests()
    }

    @Test
    fun `POST settStatusAvbryt - ugyldig request - 400`() {
        restTestClient
            .post()
            .uri(settStatusAvbrytUrl)
            .header("Authorization", bearerToken())
            .contentType(APPLICATION_JSON)
            .body(mapOf("journalpostIder" to "453802638"))
            .exchange()
            .expectStatus().isBadRequest

        assertNoDownstreamRequests()
    }

    @Test
    fun `PATCH ferdigstill - ugyldig journalpostId - 400`() {
        restTestClient
            .patch()
            .uri(ferdigstillUrl, "123")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isBadRequest

        assertNoDownstreamRequests()
    }

    private fun assertNoDownstreamRequests() {
        org.assertj.core.api.Assertions.assertThat(requestBodies.paths()).isEmpty()
        org.assertj.core.api.Assertions.assertThat(lagredeFeilregistreringer()).isEmpty()
    }
}
