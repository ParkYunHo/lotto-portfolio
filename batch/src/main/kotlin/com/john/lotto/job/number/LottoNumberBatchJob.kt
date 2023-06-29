package com.john.lotto.job.number

import com.john.lotto.job.number.tasklet.LottoNumberTasklet
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager

/**
 * @author yoonho
 * @since 2023.06.21
 */
@Configuration
class LottoNumberBatchJob (
    private val lottoNumberTasklet: LottoNumberTasklet
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun lottoNumberJob(jobRepository: JobRepository, transactionManager: JpaTransactionManager): Job =
        JobBuilder("lottoNumberJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(lottoNumberStep(jobRepository, transactionManager))
            .build()

    @Bean
    fun lottoNumberStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step =
        StepBuilder("lottoNumberStep", jobRepository)
            .tasklet(lottoNumberTasklet, transactionManager)
            .build()

}