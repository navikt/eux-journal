package no.nav.eux.journal.integration.client.saf

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue

enum class SafJournalstatus(
    val erJournalfoert: Boolean
) {
    JOURNALFOERT(true),
    FERDIGSTILT(true),
    EKSPEDERT(true),
    AVBRUTT(true),
    UTGAAR(true),
    UKJENT_BRUKER(true),
    FEILREGISTRERT(false),
    MOTTATT(false),

    @JsonEnumDefaultValue
    UKJENT(false)
}

fun safJournalstatusOf(name: String) = SafJournalstatus.values()
    .firstOrNull { safJournalstatus: SafJournalstatus -> safJournalstatus.name == name }
    ?: SafJournalstatus.UKJENT
