package no.nav.eux.journal.webapp.mock

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

@Component
class RequestBodies {

    private val requestBodies = ConcurrentHashMap<String, CopyOnWriteArrayList<String>>()

    fun record(path: String, body: String) {
        requestBodies
            .computeIfAbsent(path) { CopyOnWriteArrayList() }
            .add(body)
    }

    operator fun get(path: String): String? =
        requestBodies[path]?.lastOrNull()

    fun all(path: String): List<String> =
        requestBodies[path]?.toList().orEmpty()

    fun paths(): Set<String> =
        requestBodies.keys

    fun clear() {
        requestBodies.clear()
    }
}
