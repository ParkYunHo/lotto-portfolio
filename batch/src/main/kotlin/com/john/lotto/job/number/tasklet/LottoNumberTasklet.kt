package com.john.lotto.job.number.tasklet

import com.john.lotto.number.NumberRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
@StepScope
class LottoNumberTasklet(
    private val numberRepository: NumberRepository
): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        super.beforeStep(stepExecution)
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [execute] BATCH START ########")
        val stepContext = chunkContext.stepContext
        val jobParameters = stepContext.jobParameters

        val result = numberRepository.findLottoNumber(1720L)
        log.info(" >>> [execute] result: $result")

        log.info(" >>> [execute] BATCH END ########")
        return RepeatStatus.FINISHED
    }

}