package com.john.lotto.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Configuration
class QueryDslConfig {
    @PersistenceContext(unitName = "lottoEntityManagerFactory")
    lateinit var lottoEntityManager: EntityManager

    @Bean
    fun lottoQueryFactory() = JPAQueryFactory(lottoEntityManager)
}