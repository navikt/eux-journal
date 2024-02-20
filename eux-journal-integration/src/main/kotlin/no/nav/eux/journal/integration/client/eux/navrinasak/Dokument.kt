package no.nav.eux.journal.integration.client.eux.navrinasak

import java.util.*

data class Dokument(
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String,
    val dokumentInfoId: String
)
