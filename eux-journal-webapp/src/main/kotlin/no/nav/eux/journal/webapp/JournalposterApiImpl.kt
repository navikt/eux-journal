package no.nav.eux.journal.webapp

import no.nav.eux.journal.openapi.api.JournalposterApi
import no.nav.eux.journal.openapi.model.SettStatusAvbrytRequestOpenApiType
import no.nav.eux.journal.service.FeilregistrerJournalpostService
import no.nav.eux.journal.service.FerdigstillJournalpostService
import no.nav.eux.logging.mdc
import org.springframework.web.bind.annotation.RestController

@RestController
class JournalposterApiImpl(
    val feilregistrerJournalpostService: FeilregistrerJournalpostService,
    val ferdigstillJournalpostService: FerdigstillJournalpostService
) : JournalposterApi {

    override fun settStatusAvbryt(
        settStatusAvbrytRequestOpenApiType: SettStatusAvbrytRequestOpenApiType
    ) =
        feilregistrerJournalpostService
            .settStatusAvbryt(settStatusAvbrytRequestOpenApiType.journalpostIder)
            .toEmptyResponseEntity()

    override fun ferdigstillJournalpost(journalpostId: String) =
        ferdigstillJournalpostService
            .mdc(journalpostId = journalpostId)
            .ferdigstill(journalpostId)
            .toEmptyResponseEntity()
}
