package no.nav.eux.journal.webapp

import no.nav.eux.journal.openapi.model.SettStatusAvbrytRequestOpenApiType
import no.nav.eux.journal.webapp.common.ferdigstillUrl
import no.nav.eux.journal.webapp.common.settStatusAvbrytUrl
import no.nav.eux.journal.webapp.common.toJsonNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JournalposterApiImplTest : AbstractApiImplTest() {

    @Test
    fun `POST settStatusAvbryt - 204`() {
        restTestClient
            .post()
            .uri(settStatusAvbrytUrl)
            .header("Authorization", bearerToken())
            .body(SettStatusAvbrytRequestOpenApiType(listOf("453802638")))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty

        assertThat(
            requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"]
        ).isEmpty()
        assertThat(lagredeFeilregistreringer()).isEmpty()
    }

    @Test
    fun `POST settStatusAvbryt - dokarkiv feiler - 204`() {
        restTestClient
            .post()
            .uri(settStatusAvbrytUrl)
            .header("Authorization", bearerToken())
            .body(SettStatusAvbrytRequestOpenApiType(listOf("453802640")))
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty

        assertThat(
            requestBodies["/rest/journalpostapi/v1/journalpost/453802640/feilregistrer/settStatusAvbryt"]
        ).isEmpty()
        assertThat(lagredeFeilregistreringer()).isEmpty()
    }

    @Test
    fun `PATCH ferdigstill - 204`() {
        restTestClient
            .patch()
            .uri(ferdigstillUrl, "453802638")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isNoContent
            .expectBody().isEmpty

        assertThat(requestBodies["/rest/journalpostapi/v1/journalpost/453802638/ferdigstill"]?.toJsonNode())
            .isEqualTo("""{"journalfoerendeEnhet":"9999"}""".toJsonNode())
        assertThat(lagredeFeilregistreringer()).isEmpty()
    }
}
