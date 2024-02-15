package no.nav.eux.journal.integration.client.saf

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue

enum class SafJournalposttype {
    I, U, N,

    @JsonEnumDefaultValue
    UKJENT;

}

fun safJournalposttypeOf(name: String) =
    SafJournalposttype.values()
        .firstOrNull { journalposttype: SafJournalposttype -> journalposttype.name == name }
        ?: SafJournalposttype.UKJENT
