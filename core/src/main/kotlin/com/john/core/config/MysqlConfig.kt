package com.john.core.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaDialect
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.vault.annotation.VaultPropertySource
import javax.sql.DataSource

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Configuration
@EnableTransactionManagement
@VaultPropertySource("\${vault.secret.appconfig}")
@EnableJpaRepositories(
    entityManagerFactoryRef = "lottoEntityManagerFactory",
    transactionManagerRef = "lottoTransactionManager",
    basePackages = ["com.john.core"]
)
class MysqlConfig(
    private val env: Environment
) {

    @Bean
    fun lottoDataSource(): DataSource =
        HikariDataSource(
            HikariConfig().apply {
                driverClassName = "com.mysql.cj.jdbc.Driver"
                jdbcUrl = env.getProperty("mysql.poc.url")
                username = env.getProperty("mysql.poc.username")
                password = env.getProperty("mysql.poc.password")
                connectionTimeout = env.getProperty("spring.datasource.hikari.connection-timeout")!!.toLong()
                maximumPoolSize = env.getProperty("spring.datasource.hikari.maximum-pool-size")!!.toInt()
                minimumIdle = env.getProperty("spring.datasource.hikari.minimum-idle")!!.toInt()
            }
        )

    @Bean
    fun lottoEntityManagerFactory(): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            dataSource = lottoDataSource()
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            jpaDialect = HibernateJpaDialect()
            setPackagesToScan("com.john.core")
            setJpaPropertyMap(
                mapOf(
                    "hibernate.physical_naming_strategy" to env.getProperty("hibernate.physical_naming_strategy"),
                    "hibernate.show_sql" to env.getProperty("hibernate.show-sql"),
                    "hibernate.format_sql" to env.getProperty("hibernate.format_sql")
                )
            )
        }

    @Bean
    fun lottoTransactionManager(): JpaTransactionManager =
        JpaTransactionManager().apply { entityManagerFactory = lottoEntityManagerFactory().`object` }
}