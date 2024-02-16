package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse

fun postSettStatusAvbrytResponse() =
    MockResponse().apply {
        setResponseCode(200)
    }
