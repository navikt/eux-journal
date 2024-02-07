package no.nav.eux.journal

import com.zaxxer.hikari.HikariConfig
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource")
data class DataSourceProperties(
    var hikari: HikariConfig
)
