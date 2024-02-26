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
    UNDER_ARBEID(false),

    @JsonEnumDefaultValue
    UKJENT(false)
}
