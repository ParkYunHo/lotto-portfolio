package com.john.lotto.job.store.tasklet

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.john.lotto.common.utils.NoticeMessageUtils
import com.john.lotto.entity.LottoStore
import com.john.lotto.rest.LottoFeignClient
import com.john.lotto.rest.dto.LottoStoreInfoDto
import com.john.lotto.rest.dto.LottoStoreInput
import com.john.lotto.store.StoreRepository
import com.john.lotto.store.StoreRepositoryImpl
import com.john.lotto.store.dto.LottoStoreDto
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * @author yoonho
 * @since 2023.06.29
 */
@Component
@StepScope
class LottoStoreTasklet(
        private val storeRepository: StoreRepository,
        private val lottoFeignClient: LottoFeignClient,
        private val om: ObjectMapper
): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    var jobExecutionContext: ExecutionContext? = null

    override fun beforeStep(stepExecution: StepExecution) {
        val jobExecution = stepExecution.jobExecution
        this.jobExecutionContext = jobExecution.executionContext
    }

    @Transactional
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [store] BATCH START ########")
        val stepContext = chunkContext.stepContext
        val jobParameters = stepContext.jobParameters

        val result = mutableListOf<LottoStoreDto>()
        var nowPage = 1
        do {
            val input = LottoStoreInput(
                    searchType = "3",
                    nowPage = nowPage.toString(),
                    rtlrSttus = "001",
                    sltSIDO2 = "",
                    sltGUGUN2 = ""
            )
            val responseStr = lottoFeignClient.lottoStore(method = "sellerInfo645Result", input = input.asMap())
            if(responseStr.isNullOrEmpty()) {
                log.warn(" >>> [store] Call Lotto API fail - input: $input")
                contribution.exitStatus = ExitStatus.FAILED
                return RepeatStatus.FINISHED
            }
            val response = Gson().fromJson(JsonParser.parseString(responseStr), LottoStoreInfoDto::class.java)

            // 로또판매점 리스트 매핑
            val storeInfo = response.arr.map { om.convertValue(it, object: TypeReference<LottoStoreDto>(){}) }
            result.addAll(storeInfo)

            // 페이지 재설정
            nowPage = response.nowPage + 1
        } while (response.pageIsNext)

        log.info(" >>> [store] size: ${result.size}")

        try {
            // 전체 로또판매점데이터 삭제
            storeRepository.deleteAll()
            log.info(" >>> [store] DeleteAll LottoStore Data")

            // Entity 매핑
            val lottoStoreEntityList = mutableListOf<LottoStore>()
            for(lottoStore: LottoStoreDto in result) {
                if(lottoStore.latitude == -1F || lottoStore.longitude == -1F) {
                    log.warn(" >>> [store] Lotto Store Mapping fail - lottoStore: $lottoStore")
                    continue
                }

                // 특수문자 제거
                lottoStore.bplcdorodtladres = this.replaceTag(lottoStore.bplcdorodtladres ?: "")
                lottoStore.bplclocplcdtladres = this.replaceTag(lottoStore.bplclocplcdtladres ?: "")
                lottoStore.firmnm = this.replaceTag(lottoStore.firmnm ?: "")

                val entity = lottoStore.toEntity()
                lottoStoreEntityList.add(entity)
            }
            log.info(" >>> [store] LottoStore Entity Mapping")

            // 전체 로또판매점데이터 저장
            storeRepository.saveAll(lottoStoreEntityList)
            log.info(" >>> [store] SaveAll LottoStore Data")
        }catch (e: Exception) {
            log.error(" >>> [store] Exception occurs - message: ${e.message}")
            NoticeMessageUtils.setFailMessage(
                jobExecutionContext = jobExecutionContext!!,
                step = "lottoStoreStep",
                message = e.message ?: "[store] Exception occurs"
            )
            contribution.exitStatus = ExitStatus.FAILED
            return RepeatStatus.FINISHED
        }

        log.info(" >>> [store] BATCH END ########")
        NoticeMessageUtils.setSuccessMessage(
            jobExecutionContext = jobExecutionContext!!,
            step = "lottoStoreStep"
        )
        return RepeatStatus.FINISHED
    }

    /**
     * 특수문자 제거
     *
     * @param str [String]
     * @return [String]
     * @author yoonho
     * @since 2023.07.02
     */
    private fun replaceTag(str: String): String =
            str
                .replace("&&#35;40;", "(")
                .replace("&&#35;41;", ")")
}