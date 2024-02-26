package no.nav.eux.journal.webapp

import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity

fun <T> T.toOkResponseEntity() = ResponseEntity<T>(this, OK)

fun Any.toEmptyResponseEntity() = ResponseEntity.noContent().build<Unit>()
