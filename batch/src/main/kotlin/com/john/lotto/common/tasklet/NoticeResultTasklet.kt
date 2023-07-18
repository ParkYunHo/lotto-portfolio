package com.john.lotto.common.tasklet

import com.john.lotto.common.utils.NoticeMessageUtils
import com.john.lotto.rest.NoticeFeignClient
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
class NoticeResultTasklet(
    private val noticeFeignClient: NoticeFeignClient
): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    private var noticeMessage: String = ""

    override fun beforeStep(stepExecution: StepExecution) {
        val jobExecution = stepExecution.jobExecution
        val jobExecutionContext = jobExecution.executionContext

        val status = jobExecutionContext.get("status")?.toString() ?: ""
        val job = jobExecutionContext.get("job")?.toString() ?: ""
        val step = jobExecutionContext.get("step")?.toString() ?: ""
        val message = jobExecutionContext.get("message")?.toString() ?: ""

        noticeMessage = NoticeMessageUtils.MESSAGE_TEMPLATE
            .replace("{status}", status)
            .replace("{step}", step)
            .replace("{job}", job)
            .replace("{message}", message)
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [notice] BATCH START ########")

        try {
            val input = mapOf(
                "username" to NoticeMessageUtils.NOTICE_USER_NAME,
                "text" to noticeMessage
            )
            noticeFeignClient.slack(input = input)

        }catch (e: Exception) {
            log.error(" >>> [notice] Exception occurs - message: ${e.message}")
            contribution.exitStatus = ExitStatus.FAILED
            return RepeatStatus.FINISHED
        }

        log.info(" >>> [notice] BATCH END ########")
        return RepeatStatus.FINISHED
    }
}