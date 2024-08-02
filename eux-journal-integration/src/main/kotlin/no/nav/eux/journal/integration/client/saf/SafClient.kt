package no.nav.eux.journal.integration.client.saf

import no.nav.eux.journal.integration.config.post
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.body

@Component
class SafClient(
    @Value("\${endpoint.saf}")
    val safUrl: String,
    val safRestTemplate: RestTemplate
) {

    fun safSakerRoot(fnr: String): List<SafSak> =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(sakerQuery(fnr))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .body<SafRoot<SafSakerData>>()
            .safData()
            .saker

    fun safJournalpost(journalpostId: String): SafJournalpost =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(journalpostQuery(journalpostId))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .body<SafRoot<SafJournalpostData>>()
            .safData()
            .journalpost

    fun firstTilknyttetJournalpostOrNull(dokumentInfoId: String): SafJournalpost? =
        tilknyttedeJournalposterRoot(dokumentInfoId)
            .firstOrNull()

    fun tilknyttedeJournalposterRoot(dokumentInfoId: String): List<SafJournalpost> =
        safRestTemplate
            .post()
            .uri("$safUrl/graphql")
            .body(tilknyttedeJournalposterQuery(dokumentInfoId))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .retrieve()
            .body<SafRoot<SafTilknyttedeJournalposterData>>()
            .safData()
            .tilknyttedeJournalposter

    fun <T> SafRoot<T>?.safData(): T =
        when (this!!.data) {
            null -> throw SafErrorException(
                "Feil fra SAF: ${errors?.joinToString { it.message }}",
                errors ?: emptyList()
            )
            else -> data!!
        }
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
