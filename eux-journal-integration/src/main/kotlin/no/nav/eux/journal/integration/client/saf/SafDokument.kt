package no.nav.eux.journal.integration.client.saf

data class SafDokument(
    val dokumentInfoId: String,
    val tittel: String,
    val brevkode: String?
)
