package no.nav.eux.journal.integration.client.eux.oppgave

data class TildelEnhetsnr(
    val journalpostId: String,
    val tildeltEnhetsnr: String,
    val kommentar: OppgavePatchKommentar,
)

data class OppgavePatchKommentar(
    val tekst: String,
    val automatiskGenerert: Boolean = false
)