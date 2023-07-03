package com.john.lotto.job.drwtstore

import com.john.lotto.job.drwtstore.tasklet.LottoDrwtStoreTasklet
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
 * @since 2023.07.02
 */
@Configuration
class LottoDrwtStoreBatchJob(
    private val lottoDrwtStoreTasklet: LottoDrwtStoreTasklet
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun lottoDrwtStoreJob(jobRepository: JobRepository, transactionManager: JpaTransactionManager): Job =
            JobBuilder("lottoDrwtStoreJob", jobRepository)
                    .incrementer(RunIdIncrementer())
                    .start(lottoDrwtStoreStep(jobRepository, transactionManager))
                    .build()

    @Bean
    fun lottoDrwtStoreStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step =
            StepBuilder("lottoNumberStep", jobRepository)
                    .tasklet(lottoDrwtStoreTasklet, transactionManager)
                    .build()
}