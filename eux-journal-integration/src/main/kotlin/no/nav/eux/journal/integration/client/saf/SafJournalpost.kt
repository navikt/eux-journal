package no.nav.eux.journal.integration.client.saf

data class SafJournalpost(
    val journalpostId: String,
    val journalposttype: SafJournalposttype = SafJournalposttype.UKJENT,
    val journalstatus: SafJournalstatus = SafJournalstatus.UKJENT,
    val eksternReferanseId: String = "",
    val dokumenter: List<SafDokument>
)
