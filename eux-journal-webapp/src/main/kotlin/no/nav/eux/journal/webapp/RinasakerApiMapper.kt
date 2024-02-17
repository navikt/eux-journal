package no.nav.eux.journal.webapp

import no.nav.eux.journal.model.entity.Feilregistrering
import no.nav.eux.journal.model.common.toEnum
import no.nav.eux.journal.openapi.model.RinasakFeilregistrerJournalposterResponsOpenApiType
import no.nav.eux.journal.openapi.model.RinasakFeilregistreringOpenApiType

fun List<Feilregistrering>.toRinasakFeilregistrerJournalposterResponsOpenApiType() =
    RinasakFeilregistrerJournalposterResponsOpenApiType(rinasakFeilregistreringOpenApiTypes)

val List<Feilregistrering>.rinasakFeilregistreringOpenApiTypes
    get() = map { it.toRinasakFeilregistreringOpenApiType() }

fun Feilregistrering.toRinasakFeilregistreringOpenApiType() =
    RinasakFeilregistreringOpenApiType(
        status = feilregistreringStatus.name.toEnum(),
        beskrivelse = beskrivelse,
        dokumentInfoId = dokumentInfoId,
        journalpostId = journalpostId
    )
