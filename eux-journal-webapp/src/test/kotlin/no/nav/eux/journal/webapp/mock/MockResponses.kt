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
        "/api/v1/oppgaver/tildelEnhetsnr" -> tildelEnhetsnrResponse(body)
        else -> defaultResponse()
    }

fun mockResponsePatch(request: RecordedRequest) =
    dokarkivResponse(request.uriEndsWith)

fun mockResponseGet(request: RecordedRequest) =
    getEuxNavRinasakResponse(request.uriEndsWith)

fun defaultResponse() =
    MockResponse().apply {
        setHeader(CONTENT_TYPE, TEXT_PLAIN)
        setBody("no mock defined")
        setResponseCode(500)
    }

val RecordedRequest.uriEndsWith get() = requestUrl.toString().split("/mock")[1]
