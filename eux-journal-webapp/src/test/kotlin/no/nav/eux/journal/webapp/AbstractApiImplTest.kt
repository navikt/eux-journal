package no.nav.eux.journal.webapp

import no.nav.eux.journal.Application
import no.nav.eux.journal.model.entity.FeilregistreringStatus
import no.nav.eux.journal.persistence.FeilregistreringRepository
import no.nav.eux.journal.webapp.common.token
import no.nav.eux.journal.webapp.mock.RequestBodies
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.client.RestTestClient
import java.util.UUID

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@EnableMockOAuth2Server
@AutoConfigureRestTestClient
abstract class AbstractApiImplTest {

    @Autowired
    lateinit var mockOAuth2Server: MockOAuth2Server

    @Autowired
    lateinit var restTestClient: RestTestClient

    @Autowired
    lateinit var feilregistreringRepository: FeilregistreringRepository

    @Autowired
    lateinit var requestBodies: RequestBodies

    @BeforeEach
    fun resetTestState() {
        feilregistreringRepository.deleteAll()
        requestBodies.clear()
    }

    fun bearerToken(claims: Map<String, Any> = emptyMap()) =
        "Bearer ${mockOAuth2Server.token(claims)}"

    fun lagredeFeilregistreringer() =
        feilregistreringRepository
            .findAll()
            .sortedBy { it.dokumentInfoId }
            .map {
                LagretFeilregistrering(
                    status = it.feilregistreringStatus,
                    beskrivelse = it.beskrivelse,
                    dokumentInfoId = it.dokumentInfoId,
                    journalpostId = it.journalpostId,
                    sedId = it.sedId,
                    opprettetBruker = it.opprettetBruker,
                )
            }
}

data class LagretFeilregistrering(
    val status: FeilregistreringStatus,
    val beskrivelse: String,
    val dokumentInfoId: String,
    val journalpostId: String,
    val sedId: UUID,
    val opprettetBruker: String,
)
