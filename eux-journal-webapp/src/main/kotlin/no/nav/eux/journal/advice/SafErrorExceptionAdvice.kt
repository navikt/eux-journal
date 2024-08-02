package no.nav.eux.journal.advice

import no.nav.eux.journal.integration.client.saf.SafErrorException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@RestControllerAdvice
class SafErrorExceptionAdvice {

    @ExceptionHandler(value = [SafErrorException::class])
    fun handleMethodArgumentValidationExceptions(
        exception: SafErrorException,
        webRequest: WebRequest
    ) = ResponseEntity
        .status(BAD_REQUEST)
        .body(exception.safError)

    val SafErrorException.safError
        get() = SafError(errors = errors.map { it.message })

    data class SafError(
        val timestamp: LocalDateTime = now(),
        val errors: List<String>
    )
}
