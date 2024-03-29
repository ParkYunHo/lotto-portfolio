package com.john.lotto.job.store

import com.john.lotto.common.tasklet.NoticeResultTasklet
import com.john.lotto.job.store.tasklet.LottoStoreTasklet
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
 * @since 2023.06.29
 */
@Configuration
class LottoStoreBatchJob (
    private val lottoStoreTasklet: LottoStoreTasklet,
    private val noticeResultTasklet: NoticeResultTasklet,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun lottoStoreJob(jobRepository: JobRepository, transactionManager: JpaTransactionManager): Job =
        JobBuilder("lottoStoreJob", jobRepository)
            .incrementer(RunIdIncrementer())
            // 로또판매점 조회
            .start(lottoStoreStep(jobRepository, transactionManager))
            // 배치결과 알림전송
            .next(lottoStoreNoticeResultStep(jobRepository, transactionManager))
            .build()

    @Bean
    fun lottoStoreStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step =
        StepBuilder("lottoStoreStep", jobRepository)
            .tasklet(lottoStoreTasklet, transactionManager)
            .build()

    @Bean
    fun lottoStoreNoticeResultStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step =
        StepBuilder("lottoStoreNoticeResultStep", jobRepository)
            .tasklet(noticeResultTasklet, transactionManager)
            .build()
}