package no.nav.eux.journal.integration.client.saf

data class SafSak(
    val fagsakId: String?,
    val sakstype: String? = null,
    val tema: String? = null,
    val fagsaksystem: String? = null,
    val arkivsaksnummer: String? = null,
    val arkivsaksystem: String? = null,
)
