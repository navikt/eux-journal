package no.nav.eux.journal.service

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import no.nav.eux.journal.integration.client.dokarkiv.DokarkivClient
import no.nav.eux.journal.integration.client.eux.navrinasak.Dokument
import no.nav.eux.journal.integration.client.eux.navrinasak.EuxNavRinasakClient
import no.nav.eux.journal.integration.client.eux.oppgave.EuxOppgaveClient
import no.nav.eux.journal.integration.client.saf.SafClient
import no.nav.eux.journal.integration.client.saf.SafJournalpost
import no.nav.eux.journal.integration.client.saf.SafJournalposttype.I
import no.nav.eux.journal.integration.client.saf.SafJournalposttype.U
import no.nav.eux.journal.model.entity.Feilregistrering
import no.nav.eux.journal.model.entity.FeilregistreringStatus.*
import no.nav.eux.journal.persistence.FeilregistreringRepository
import org.springframework.stereotype.Component

@Component
class FeilregistrerJournalpostService(
    val dokarkivClient: DokarkivClient,
    val safClient: SafClient,
    val euxOppgaveClient: EuxOppgaveClient,
    val euxNavRinasakClient: EuxNavRinasakClient,
    val repository: FeilregistreringRepository,
    val context: TokenContextService
) {

    val log = logger {}

    fun feilregistrerJournalposter(rinasakId: Int) =
        euxNavRinasakClient
            .finn(rinasakId)
            .dokumenter
            ?.mapNotNull { it med safClient.firstTilknyttetJournalpostOrNull(it.dokumentInfoId) }
            ?.filterNot { it.journalpost.journalstatus.erJournalfoert }
            ?.map { it.mdc().feilregistrer() }
            ?: emptyList()

    fun DokumentPair.feilregistrer(): Feilregistrering {
        log.info { "Feilregistrerer dokument." }
        val feilregistrering = when (journalpost.journalposttype) {
            I -> settStatusAvbryt()
            U -> tildelEnhetsnr()
            else -> ukjentJournalposttype()
        }
        return repository.save(feilregistrering)
    }

    fun DokumentPair.settStatusAvbryt() =
        try {
            dokarkivClient.settStatusAvbryt(journalpost.journalpostId)
            val beskrivelse = "Journalpost satt til status avbryt."
            log.info { beskrivelse }
            Feilregistrering(
                feilregistreringStatus = SATT_TIL_STATUS_AVBRYT,
                beskrivelse = beskrivelse,
                dokumentInfoId = dokument.dokumentInfoId,
                journalpostId = journalpost.journalpostId,
                opprettetBruker = context.navIdent
            )
        } catch (e: RuntimeException) {
            val beskrivelse = "Kunne ikke sette til status avbryt"
            log.error(e) { beskrivelse }
            Feilregistrering(
                feilregistreringStatus = FEILREGISTRERING_FEILET,
                beskrivelse = beskrivelse,
                dokumentInfoId = dokument.dokumentInfoId,
                journalpostId = journalpost.journalpostId,
                opprettetBruker = context.navIdent
            )
        }

    fun DokumentPair.tildelEnhetsnr() =
        try {
            euxOppgaveClient.tildelEnhetsnr(
                journalpostId = journalpost.journalpostId,
                enhetsnr = navDriftOgUtviklingAdministrativeTjenester,
            )
            val beskrivelse = "Oppgave flyttet til enhetsnr $navDriftOgUtviklingAdministrativeTjenester."
            log.info { beskrivelse }
            Feilregistrering(
                feilregistreringStatus = OPPGAVE_FLYTTET,
                beskrivelse = beskrivelse,
                dokumentInfoId = dokument.dokumentInfoId,
                journalpostId = journalpost.journalpostId,
                opprettetBruker = context.navIdent
            )
        } catch (e: RuntimeException) {
            val beskrivelse = "Kunne ikke flytte oppgave"
            log.error(e) { beskrivelse }
            Feilregistrering(
                feilregistreringStatus = OPPGAVEFLYTT_FEILET,
                beskrivelse = beskrivelse,
                dokumentInfoId = dokument.dokumentInfoId,
                journalpostId = journalpost.journalpostId,
                opprettetBruker = context.navIdent
            )
        }

    fun DokumentPair.ukjentJournalposttype(): Feilregistrering {
        val beskrivelse = "Journalpost er av type ${journalpost.journalposttype}"
        log.error { beskrivelse }
        return Feilregistrering(
            feilregistreringStatus = FEILREGISTRERING_FEILET,
            beskrivelse = beskrivelse,
            dokumentInfoId = dokument.dokumentInfoId,
            journalpostId = journalpost.journalpostId,
            opprettetBruker = context.navIdent
        )
    }

    infix fun Dokument.med(journalpost: SafJournalpost?) =
        if (journalpost == null) {
            log.error { "Dokument mangler journalpost." }
            null
        } else {
            log.info { "Journalpost funnet." }
            DokumentPair(dokument = this, journalpost = journalpost)
        }
}

data class DokumentPair(
    val dokument: Dokument,
    val journalpost: SafJournalpost
)

const val navDriftOgUtviklingAdministrativeTjenester = "2950"
