package no.nav.eux.journal.integration.client.eux.navrinasak

import java.time.OffsetDateTime

data class NavRinasakSearchCriteria(
    val rinasakId: Int?,
    val fraOpprettetDato: OffsetDateTime?,
    val tilOpprettetDato: OffsetDateTime?
)
