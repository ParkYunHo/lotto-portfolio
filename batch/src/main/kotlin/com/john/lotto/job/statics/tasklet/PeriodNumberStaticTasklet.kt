package com.john.lotto.job.statics.tasklet

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

/**
 * @author yoonho
 * @since 2023.07.05
 */
@Component
@StepScope
class PeriodNumberStaticTasklet(

): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        super.beforeStep(stepExecution)
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [periodNumberStatic] BATCH START ########")



        log.info(" >>> [periodNumberStatic] BATCH END ########")
        return RepeatStatus.FINISHED
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        return super.afterStep(stepExecution)
    }
}