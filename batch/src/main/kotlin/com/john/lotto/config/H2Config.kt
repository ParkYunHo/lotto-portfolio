package com.john.lotto.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.jdbc.support.JdbcTransactionManager
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.22
 */
@Configuration
class H2Config {

    @Primary
    @Bean
    fun batchDataSource(): DataSource =
        EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .generateUniqueName(true)
            .build()

    @Primary
    @Bean
    fun batchTransactionManager(batchDataSource: DataSource): JdbcTransactionManager =
        JdbcTransactionManager(batchDataSource)
}