package no.nav.eux.journal.integration.client.saf

import no.nav.eux.journal.integration.config.post
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.toEntity

@Component
class SafClient(
    @Value("\${endpoint.saf}")
    val safUrl: String,
    val safRestTemplate: RestTemplate
) {

    fun safSakerRoot(fnr: String): SafSakerRoot =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(sakerQuery(fnr))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .toEntity<SafSakerRoot>()
            .body!!

    fun safJournalpost(journalpostId: String): SafJournalpostRoot =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(journalpostQuery(journalpostId))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .toEntity<SafJournalpostRoot>()
            .body!!

    fun firstTilknyttetJournalpostOrNull(dokumentInfoId: String): SafJournalpost? =
        tilknyttedeJournalposterRoot(dokumentInfoId)
            .data
            .tilknyttedeJournalposter
            .firstOrNull()

    fun tilknyttedeJournalposterRoot(dokumentInfoId: String): SafTilknyttedeJournalposterRoot =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(tilknyttedeJournalposterQuery(dokumentInfoId))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .toEntity<SafTilknyttedeJournalposterRoot>()
            .body!!
}

fun journalpostQuery(journalpostId: String) = GraphQlQuery(
    """query {
          journalpost(journalpostId: "$journalpostId") {
              journalpostId
              tittel
              journalposttype
              journalstatus
              eksternReferanseId
              tema
              dokumenter {
                dokumentInfoId
                tittel
                brevkode
              }
          }
        }""".trimIndent()
)

fun tilknyttedeJournalposterQuery(dokumentInfoId: String) = GraphQlQuery(
    """query {
          tilknyttedeJournalposter(dokumentInfoId: "$dokumentInfoId", tilknytning: GJENBRUK) {
              journalpostId
              tittel
              journalposttype
              journalstatus
              eksternReferanseId
              tema
              dokumenter {
                dokumentInfoId
                tittel
                brevkode
              }
          }
        }""".trimIndent()
)

fun sakerQuery(fnr: String) = GraphQlQuery(
    """query {
          saker(brukerId: { id: "$fnr", type:FNR } ) {
            tema
            fagsakId
            fagsaksystem
            arkivsaksnummer
            arkivsaksystem
            sakstype
          }
        }""".trimIndent()
)

data class GraphQlQuery(
    val query: String
)
