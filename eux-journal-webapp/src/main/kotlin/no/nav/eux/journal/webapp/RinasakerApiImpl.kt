package no.nav.eux.journal.webapp

import no.nav.eux.journal.openapi.api.RinasakerApi
import no.nav.eux.journal.service.FeilregistrerJournalpostService
import no.nav.eux.journal.service.mdc
import no.nav.security.token.support.core.api.Protected
import org.springframework.web.bind.annotation.RestController

@RestController
class RinasakerApiImpl(
    val feilregistrerJournalpostService: FeilregistrerJournalpostService
) : RinasakerApi {

    @Protected
    override fun feilregistrerJournalposter(
        rinasakId: Int
    ) =
        feilregistrerJournalpostService
            .mdc(rinasakId = rinasakId)
            .feilregistrerJournalposter(rinasakId)
            .toRinasakFeilregistrerJournalposterResponsOpenApiType()
            .toOkResponseEntity()

}
