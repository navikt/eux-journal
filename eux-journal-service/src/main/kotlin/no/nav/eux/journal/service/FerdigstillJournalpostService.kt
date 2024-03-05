package no.nav.eux.journal.service

import io.github.oshai.kotlinlogging.KotlinLogging
import no.nav.eux.journal.integration.client.dokarkiv.DokarkivClient
import org.springframework.stereotype.Service

@Service
class FerdigstillJournalpostService(
    val dokarkivClient: DokarkivClient
) {

    val log = KotlinLogging.logger {}

    fun ferdigstill(journalpostId: String) {
        dokarkivClient.ferdigstill(journalpostId)
        log.info { "Ferdigstilte $journalpostId" }
    }
}
