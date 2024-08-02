package no.nav.eux.journal.integration.client.eux.navrinasak

import no.nav.eux.journal.integration.config.get
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.toEntity

@Component
class EuxNavRinasakClient(
    @Value("\${endpoint.euxnavrinasak}")
    val euxNavRinasakUrl: String,
    val euxNavRinasakRestTemplate: RestTemplate
) {

    fun finn(rinasakId: Int): EuxNavRinasak = euxNavRinasakRestTemplate
        .get()
        .uri("${euxNavRinasakUrl}/api/v1/rinasaker/${rinasakId}")
        .retrieve()
        .toEntity<EuxNavRinasak>()
        .body!!
}
