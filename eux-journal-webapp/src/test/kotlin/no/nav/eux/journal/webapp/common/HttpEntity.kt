package no.nav.eux.journal.webapp.common

import com.nimbusds.jose.JOSEObjectType
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback

fun MockOAuth2Server.token(claims: Map<String, Any> = emptyMap()): String =
    issueToken(
        "issuer1",
        "theclientid",
        DefaultOAuth2TokenCallback(
            "issuer1",
            "subject1",
            JOSEObjectType.JWT.type,
            listOf("demoapplication"),
            claims,
            3600
        )
    )
        .serialize()
