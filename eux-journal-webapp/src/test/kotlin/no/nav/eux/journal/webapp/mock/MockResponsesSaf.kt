package no.nav.eux.journal.webapp.mock

import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

fun safResponse(body: String) =
    when {
        body.contains("454221906") -> resourceResponse("/dataset/saf/get-response-body-454221906.json")
        body.contains("454221907") -> resourceResponse("/dataset/saf/get-response-body-454221907.json")
        body.contains("454221908") -> journalpostResponse("453802640", "MOTTATT", "U", "454221908")
        body.contains("454221909") -> journalpostResponse("453802641", "MOTTATT", "I", "454221909")
        body.contains("454221910") -> journalpostResponse("453802642", "MOTTATT", "N", "454221910")
        body.contains("454221911") -> journalpostResponse("453802643", "JOURNALFOERT", "U", "454221911")
        body.contains("454221912") -> jsonResponse("""{"data":{"tilknyttedeJournalposter":[]}}""")
        body.contains("454221913") ->
            jsonResponse("""{"data":null,"errors":[{"message":"SAF kunne ikke hente journalpost"}]}""")
        else -> defaultResponse()
    }

private fun journalpostResponse(
    journalpostId: String,
    journalstatus: String,
    journalposttype: String,
    dokumentInfoId: String,
) = jsonResponse(
    """
    {
      "data": {
        "tilknyttedeJournalposter": [
          {
            "journalpostId": "$journalpostId",
            "journalstatus": "$journalstatus",
            "journalposttype": "$journalposttype",
            "eksternReferanseId": "test",
            "dokumenter": [
              {
                "dokumentInfoId": "$dokumentInfoId",
                "tittel": "Testdokument",
                "brevkode": "TEST"
              }
            ]
          }
        ]
      }
    }
    """.trimIndent()
)

private fun resourceResponse(resource: String) =
    jsonResponse(Any::class::class.java.getResource(resource)!!.readText())

private fun jsonResponse(body: String) =
    MockResponse()
        .setResponseCode(200)
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(body)
