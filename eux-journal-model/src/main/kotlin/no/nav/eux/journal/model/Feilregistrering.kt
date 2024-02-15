package no.nav.eux.journal.model

data class Feilregistrering(
    val feilregistreringStatus: FeilregistreringStatus,
    val beskrivelse: String,
    val dokumentInfoId: String,
    val journalpostId: String,
)
