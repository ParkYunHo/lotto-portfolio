package com.john.lotto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.22
 */
@Configuration
class H2Config {

    /**
     * BATCH_JOB_INSTANCE 저장용도 (h2)
     *
     * @return [DataSource]
     * @author yoonho
     * @since 2023.06.29
     */
    @Primary
    @Bean
    fun batchDataSource(): DataSource =
        EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .generateUniqueName(true)
            .build()
}