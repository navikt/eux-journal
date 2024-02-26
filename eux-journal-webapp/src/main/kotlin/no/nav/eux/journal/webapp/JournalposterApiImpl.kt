package no.nav.eux.journal.webapp

import no.nav.eux.journal.openapi.api.JournalposterApi
import no.nav.eux.journal.openapi.model.SettStatusAvbrytRequestOpenApiType
import no.nav.eux.journal.service.FeilregistrerJournalpostService
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.web.bind.annotation.RestController

@RestController
class JournalposterApiImpl(
    val service: FeilregistrerJournalpostService
) : JournalposterApi {

    @Unprotected
    override fun settStatusAvbryt(
        settStatusAvbrytRequestOpenApiType: SettStatusAvbrytRequestOpenApiType
    ) =
        service
            .settStatusAvbryt(settStatusAvbrytRequestOpenApiType.journalpostIder)
            .toEmptyResponseEntity()
}