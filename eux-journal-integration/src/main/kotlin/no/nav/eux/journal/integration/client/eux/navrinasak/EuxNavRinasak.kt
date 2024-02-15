package no.nav.eux.journal.integration.client.eux.navrinasak

data class EuxNavRinasak(
    val rinasakId: Int,
    val opprettetBruker: String,
    val opprettetTidspunkt: java.time.OffsetDateTime,
    val overstyrtEnhetsnummer: String? = null,
    val initiellFagsak: Fagsak? = null,
    val dokumenter: List<Dokument>? = null
)
