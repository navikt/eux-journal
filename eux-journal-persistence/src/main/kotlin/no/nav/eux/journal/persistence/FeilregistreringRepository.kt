package no.nav.eux.journal.persistence

import no.nav.eux.journal.model.entity.Feilregistrering
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeilregistreringRepository : JpaRepository<Feilregistrering, UUID>