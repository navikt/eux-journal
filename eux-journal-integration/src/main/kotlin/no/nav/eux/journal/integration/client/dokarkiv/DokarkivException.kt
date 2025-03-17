package no.nav.eux.journal.integration.client.dokarkiv

data class DokarkivErrorResponse(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
    val timestamp: String,
    val message: String,
    val error: String,
    val path: String
)

class DokarkivBadRequestException(
    val dokarkivErrorResponse: DokarkivErrorResponse,
    override val message: String = "",
) : RuntimeException(message)
