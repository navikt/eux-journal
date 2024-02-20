package no.nav.eux.journal.integration.client.saf

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue

enum class SafJournalposttype {
    I, U, N,

    @JsonEnumDefaultValue
    UKJENT;

}
