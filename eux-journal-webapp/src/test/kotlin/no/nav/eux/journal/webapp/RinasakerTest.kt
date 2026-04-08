package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.journalposterFeilregistrerUrl
import no.nav.eux.journal.webapp.common.token
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RinasakerTest : AbstractOppgaverApiImplTest() {

    @Test
    fun `POST feilregistrer - ok - 200`() {
        restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444520")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(200)
        println("Følgende requests ble utført:")
        requestBodies.forEach { println("Path: ${it.key}, body: ${it.value}") }
        assertThat(requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"])
            .isNotNull()
        assertThat(requestBodies["/api/v1/oppgaver/tildelEnhetsnr"])
            .isNotNull()
    }
}
