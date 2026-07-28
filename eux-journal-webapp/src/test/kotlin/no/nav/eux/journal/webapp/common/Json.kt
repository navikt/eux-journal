package no.nav.eux.journal.webapp.common

import tools.jackson.databind.ObjectMapper

private val objectMapper = ObjectMapper()

fun String.toJsonNode() = objectMapper.readTree(this)
