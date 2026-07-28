package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

fun dokarkivResponse(path: String) =
    when (path) {
        "/rest/journalpostapi/v1/journalpost/453802638/feilregistrer/settStatusAvbryt" ->
            MockResponse().setResponseCode(204)
        "/rest/journalpostapi/v1/journalpost/453802640/feilregistrer/settStatusAvbryt" ->
            MockResponse().setResponseCode(500)
        "/rest/journalpostapi/v1/journalpost/453802638/ferdigstill" ->
            MockResponse().setResponseCode(204)
        "/rest/journalpostapi/v1/journalpost/453802640/ferdigstill" ->
            MockResponse()
                .setResponseCode(400)
                .setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody(dokarkivBadRequestResponse)
        else -> defaultResponse()
    }

private val dokarkivBadRequestResponse = """
    {
      "type": "about:blank",
      "title": "Bad Request",
      "status": 400,
      "detail": "Kunne ikke ferdigstille journalpost med journalpostId=453802640. Journalposten mangler følgende felter: avsenderMottaker.navn",
      "instance": "/rest/journalpostapi/v1/journalpost/453802640/ferdigstill",
      "timestamp": "2026-07-28T09:00:00+02:00",
      "message": "Kunne ikke ferdigstille journalpost med journalpostId=453802640. Journalposten mangler følgende felter: avsenderMottaker.navn",
      "error": "Bad Request",
      "path": "/rest/journalpostapi/v1/journalpost/453802640/ferdigstill"
    }
""".trimIndent()
