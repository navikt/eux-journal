package no.nav.eux.journal.webapp

import no.nav.eux.journal.webapp.common.settStatusAvbrytUrl
import no.nav.eux.journal.webapp.dataset.testModelSettStatusAvbrytRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity

class JournalposterTest : AbstractOppgaverApiImplTest() {

    @Test
    fun `POST settStatusAvbryt - 204`() {
        val createResponse = restTemplate
            .postForEntity<String>(
                settStatusAvbrytUrl,
                testModelSettStatusAvbrytRequest.httpEntity,
                "1444520"
            )
        println("Følgende requests ble utført:")
        requestBodies.forEach { println("Path: ${it.key}, body: ${it.value}") }
        assertThat(createResponse.statusCode.value()).isEqualTo(204)
        assertThat(requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"])
            .isNotNull()
    }

    @Test
    fun `POST ferdigstillJournalpost - 204`() {

    }
}
