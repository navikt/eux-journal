package no.nav.eux.journal.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*
import java.util.UUID.randomUUID

@Entity
data class Feilregistrering(
    @Id
    val feilregistreringUuid: UUID = randomUUID(),
    val feilregistreringStatus: FeilregistreringStatus,
    val beskrivelse: String,
    val dokumentInfoId: String,
    val journalpostId: String,
    @Column(updatable = false)
    val opprettetBruker: String,
    @Column(updatable = false)
    val opprettetTidspunkt: LocalDateTime = LocalDateTime.now(),
)
