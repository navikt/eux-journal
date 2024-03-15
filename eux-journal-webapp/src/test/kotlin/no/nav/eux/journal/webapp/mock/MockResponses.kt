package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.TEXT_PLAIN

fun mockResponse(request: RecordedRequest, body: String) =
    when (request.method) {
        HttpMethod.POST.name() -> mockResponsePost(request, body)
        HttpMethod.GET.name() -> mockResponseGet(request)
        HttpMethod.PATCH.name() -> mockResponsePatch(request)
        else -> defaultResponse()
    }

fun mockResponsePost(request: RecordedRequest, body: String) =
    when (request.uriEndsWith) {
        "/oauth2/v2.0/token" -> tokenResponse()
        "/graphql" -> safResponse(body)
        "/api/v1/oppgaver" -> oppgaverResponse()
        "/api/v1/oppgaver/tildelEnhetsnr" -> tildelEnhetsnrResponse(body)
        else -> defaultResponse()
    }

fun mockResponsePatch(request: RecordedRequest) =
    when (request.uriEndsWith) {
        "/journalpost/453802638/feilregistrer/settStatusAvbryt" -> response200()
        "/journalpost/453802638/ferdigstill" -> response200()
        "/api/v1/oppgaver/190402" -> oppgaverResponse()
        else -> defaultResponse()
    }

fun mockResponseGet(request: RecordedRequest) =
    when (request.uriEndsWith) {
        "/api/v1/rinasaker/1444520" -> getEuxNavRinasakResponse()
        getOppgaverUri -> getOppgaverResponse()
        else -> defaultResponse()
    }

fun defaultResponse() =
    MockResponse().apply {
        setHeader(CONTENT_TYPE, TEXT_PLAIN)
        setBody("no mock defined")
        setResponseCode(500)
    }

val RecordedRequest.uriEndsWith get() = requestUrl.toString().split("/mock")[1]
