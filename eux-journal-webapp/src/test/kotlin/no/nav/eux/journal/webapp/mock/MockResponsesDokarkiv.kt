package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse

fun response200() =
    MockResponse().apply {
        setResponseCode(200)
    }
