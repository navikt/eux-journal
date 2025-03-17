package no.nav.eux.journal.advice

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import no.nav.eux.journal.integration.client.dokarkiv.DokarkivBadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DokarkivBadRequestAdvice {

    val log = logger {}

    @ExceptionHandler(value = [DokarkivBadRequestException::class])
    fun dokarkivBadRequestException(exception: DokarkivBadRequestException) =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.dokarkivErrorResponse)
            .also { log.error { "Kall mot dokarkiv feilet: ${exception.dokarkivErrorResponse.message}" } }
}
