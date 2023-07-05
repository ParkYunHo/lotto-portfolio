package com.john.lotto.common.utils

import org.springframework.batch.item.ExecutionContext

/**
 * @author yoonho
 * @since 2023.07.05
 */
class NoticeMessageUtils {

    companion object {
        val NOTICE_USER_NAME = "Lotto Batch Notification"
        var MESSAGE_TEMPLATE = "\uFE0F\uD83C\uDF1F로또배치 실행 {status}!\uD83C\uDF1F\n\n" +
                "- Step : {step}\n" +
                "- 메세지 : {message}\n" +
                "- url: {url}"

        fun setSuccessMessage(jobExecutionContext: ExecutionContext, step: String) {
            jobExecutionContext.put("message", "SUCCESS")
            jobExecutionContext.put("step", step)
            jobExecutionContext.put("status", "성공")
        }

        fun setFailMessage(jobExecutionContext: ExecutionContext, step: String, message: String) {
            jobExecutionContext.put("message", message)
            jobExecutionContext.put("step", step)
            jobExecutionContext.put("status", "실패")
        }
    }

}