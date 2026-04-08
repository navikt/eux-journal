package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.settStatusAvbrytUrl
import no.nav.eux.journal.webapp.common.token
import no.nav.eux.journal.webapp.dataset.testModelSettStatusAvbrytRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JournalposterTest : AbstractOppgaverApiImplTest() {

    @Test
    fun `POST settStatusAvbryt - 204`() {
        restTestClient
            .post()
            .uri(settStatusAvbrytUrl, "1444520")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(testModelSettStatusAvbrytRequest)
            .exchange()
            .expectStatus().isEqualTo(204)
        println("Følgende requests ble utført:")
        requestBodies.forEach { println("Path: ${it.key}, body: ${it.value}") }
        assertThat(requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"])
            .isNotNull()
    }

}
