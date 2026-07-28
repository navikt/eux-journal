package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

fun getEuxNavRinasakResponse(path: String) =
    when (path) {
        "/api/v1/rinasaker/1444520" ->
            jsonResponse("/dataset/eux-nav-rinasak/get-response-body.json")
        "/api/v1/rinasaker/1444521" ->
            jsonResponse("/dataset/eux-nav-rinasak/get-response-body-error-branches.json")
        "/api/v1/rinasaker/1444522" ->
            jsonResponse("/dataset/eux-nav-rinasak/get-response-body-no-documents.json")
        "/api/v1/rinasaker/1444523" ->
            jsonResponse("/dataset/eux-nav-rinasak/get-response-body-saf-error.json")
        else -> defaultResponse()
    }

private fun jsonResponse(resource: String) =
    MockResponse()
        .setResponseCode(200)
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(Any::class::class.java.getResource(resource)!!.readText())
