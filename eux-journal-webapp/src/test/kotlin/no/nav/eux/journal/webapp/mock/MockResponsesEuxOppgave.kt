package no.nav.eux.journal.webapp.mock

import tools.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse

fun tildelEnhetsnrResponse(body: String): MockResponse {
    val request = ObjectMapper().readTree(body)
    val journalpostId = request.findValue("journalpostId").asString()
    val tildeltEnhetsnr = request.findValue("tildeltEnhetsnr").asString()
    return if (journalpostId == "453802639" && tildeltEnhetsnr == "2950") {
        MockResponse().apply {
            setResponseCode(200)
        }
    } else {
        MockResponse().apply {
            setResponseCode(500)
        }
    }
}
