package no.nav.eux.journal.webapp

import no.nav.eux.journal.model.entity.FeilregistreringStatus.FEILREGISTRERING_FEILET
import no.nav.eux.journal.model.entity.FeilregistreringStatus.OPPGAVEFLYTT_FEILET
import no.nav.eux.journal.model.entity.FeilregistreringStatus.OPPGAVE_FLYTTET
import no.nav.eux.journal.model.entity.FeilregistreringStatus.SATT_TIL_STATUS_AVBRYT
import no.nav.eux.journal.openapi.model.FeilregistreringStatus
import no.nav.eux.journal.openapi.model.RinasakFeilregistrerJournalposterResponsOpenApiType
import no.nav.eux.journal.openapi.model.RinasakFeilregistreringOpenApiType
import no.nav.eux.journal.webapp.common.journalposterFeilregistrerUrl
import no.nav.eux.journal.webapp.common.toJsonNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class RinasakerApiImplTest : AbstractApiImplTest() {

    @Test
    fun `POST feilregistrer - 200`() {
        val response = restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444520")
            .header("Authorization", bearerToken(mapOf("NAVident" to "Z123456")))
            .exchange()
            .expectStatus().isOk
            .expectBody(RinasakFeilregistrerJournalposterResponsOpenApiType::class.java)
            .returnResult()
            .responseBody!!

        assertThat(response.feilregistreringer).containsExactly(
            RinasakFeilregistreringOpenApiType(
                status = FeilregistreringStatus.SATT_TIL_STATUS_AVBRYT,
                beskrivelse = "Journalpost satt til status avbryt.",
                dokumentInfoId = "454221906",
                journalpostId = "453802638",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f071"),
            ),
            RinasakFeilregistreringOpenApiType(
                status = FeilregistreringStatus.OPPGAVE_FLYTTET,
                beskrivelse = "Oppgave flyttet til enhetsnr 2950.",
                dokumentInfoId = "454221907",
                journalpostId = "453802639",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f072"),
            ),
        )
        assertThat(
            requestBodies["/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt"]
        ).isEmpty()
        assertThat(requestBodies["/api/v1/oppgaver/tildelEnhetsnr"]?.toJsonNode()).isEqualTo(
            """
            {
              "journalpostId": "453802639",
              "tildeltEnhetsnr": "2950",
              "kommentar": "Den mottatte SEDen kan ikke journalføres. Dokumentet skal derfor settes til 'utgått' i Joark."
            }
            """.trimIndent().toJsonNode()
        )
        assertThat(requestBodies.all("/graphql")).hasSize(2)
        assertThat(lagredeFeilregistreringer()).containsExactly(
            LagretFeilregistrering(
                status = SATT_TIL_STATUS_AVBRYT,
                beskrivelse = "Journalpost satt til status avbryt.",
                dokumentInfoId = "454221906",
                journalpostId = "453802638",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f071"),
                opprettetBruker = "Z123456",
            ),
            LagretFeilregistrering(
                status = OPPGAVE_FLYTTET,
                beskrivelse = "Oppgave flyttet til enhetsnr 2950.",
                dokumentInfoId = "454221907",
                journalpostId = "453802639",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f072"),
                opprettetBruker = "Z123456",
            ),
        )
    }

    @Test
    fun `POST feilregistrer - feil og filtrering - 200`() {
        val response = restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444521")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isOk
            .expectBody(RinasakFeilregistrerJournalposterResponsOpenApiType::class.java)
            .returnResult()
            .responseBody!!

        assertThat(response.feilregistreringer).containsExactly(
            RinasakFeilregistreringOpenApiType(
                status = FeilregistreringStatus.FEILREGISTRERING_FEILET,
                beskrivelse = "Kunne ikke sette til status avbryt",
                dokumentInfoId = "454221908",
                journalpostId = "453802640",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f073"),
            ),
            RinasakFeilregistreringOpenApiType(
                status = FeilregistreringStatus.OPPGAVEFLYTT_FEILET,
                beskrivelse = "Kunne ikke flytte oppgave",
                dokumentInfoId = "454221909",
                journalpostId = "453802641",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f074"),
            ),
            RinasakFeilregistreringOpenApiType(
                status = FeilregistreringStatus.FEILREGISTRERING_FEILET,
                beskrivelse = "Journalpost er av type N",
                dokumentInfoId = "454221910",
                journalpostId = "453802642",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f075"),
            ),
        )
        assertThat(
            requestBodies["/rest/journalpostapi/v1/journalpost/453802640/feilregistrer/settStatusAvbryt"]
        ).isEmpty()
        assertThat(requestBodies["/api/v1/oppgaver/tildelEnhetsnr"]?.toJsonNode()).isEqualTo(
            """
            {
              "journalpostId": "453802641",
              "tildeltEnhetsnr": "2950",
              "kommentar": "Den mottatte SEDen kan ikke journalføres. Dokumentet skal derfor settes til 'utgått' i Joark."
            }
            """.trimIndent().toJsonNode()
        )
        assertThat(
            requestBodies["/rest/journalpostapi/v1/journalpost/453802643/feilregistrer/settStatusAvbryt"]
        ).isNull()
        assertThat(requestBodies.all("/graphql")).hasSize(5)
        assertThat(lagredeFeilregistreringer()).containsExactly(
            LagretFeilregistrering(
                status = FEILREGISTRERING_FEILET,
                beskrivelse = "Kunne ikke sette til status avbryt",
                dokumentInfoId = "454221908",
                journalpostId = "453802640",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f073"),
                opprettetBruker = "ukjent",
            ),
            LagretFeilregistrering(
                status = OPPGAVEFLYTT_FEILET,
                beskrivelse = "Kunne ikke flytte oppgave",
                dokumentInfoId = "454221909",
                journalpostId = "453802641",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f074"),
                opprettetBruker = "ukjent",
            ),
            LagretFeilregistrering(
                status = FEILREGISTRERING_FEILET,
                beskrivelse = "Journalpost er av type N",
                dokumentInfoId = "454221910",
                journalpostId = "453802642",
                sedId = UUID.fromString("0eab91a0-5415-4357-8c59-3f7d7764f075"),
                opprettetBruker = "ukjent",
            ),
        )
    }

    @Test
    fun `POST feilregistrer - ingen dokumenter - 200`() {
        val response = restTestClient
            .post()
            .uri(journalposterFeilregistrerUrl, "1444522")
            .header("Authorization", bearerToken())
            .exchange()
            .expectStatus().isOk
            .expectBody(RinasakFeilregistrerJournalposterResponsOpenApiType::class.java)
            .returnResult()
            .responseBody!!

        assertThat(response.feilregistreringer).isEmpty()
        assertThat(requestBodies.all("/graphql")).isEmpty()
        assertThat(lagredeFeilregistreringer()).isEmpty()
    }
}
