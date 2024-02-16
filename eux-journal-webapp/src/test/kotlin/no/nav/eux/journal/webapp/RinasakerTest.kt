package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.journalposterFeilregistrerUrl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity

class RinasakerTest : AbstractOppgaverApiImplTest() {

    @Test
    fun `POST feilregistrer - ok - 200`() {
        val createResponse = restTemplate
            .postForEntity<String>(
                journalposterFeilregistrerUrl,
                httpEntity(),
                "1444520"
            )
        println("Følgende requests ble utført:")
        requestBodies.forEach { println("Path: ${it.key}, body: ${it.value}") }
        assertThat(createResponse.statusCode.value()).isEqualTo(200)
        assertThat(requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"])
            .isNotNull()
        assertThat(requestBodies["/api/v1/oppgave/tildelEnhetsnr"])
            .isNotNull()
    }
}
