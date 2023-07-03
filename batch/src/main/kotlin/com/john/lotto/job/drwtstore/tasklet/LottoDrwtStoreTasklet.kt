package com.john.lotto.job.drwtstore.tasklet

import com.john.lotto.drwtstore.DrwtStoreRepository
import com.john.lotto.drwtstore.dto.DrwtStoreDto
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.02
 */
@Component
@StepScope
class LottoDrwtStoreTasklet(
    private val drwtStoreRepository: DrwtStoreRepository
): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        super.beforeStep(stepExecution)
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [drwtStore] BATCH START ########")
        val stepContext = chunkContext.stepContext
        val jobParameters = stepContext.jobParameters

        val startDrwtNoParam = jobParameters.getOrDefault("startDrwtNo", "").toString()
        val endDrwtNoParam = jobParameters.getOrDefault("endDrwtNo", "").toString()

        if(startDrwtNoParam == "" && endDrwtNoParam == "") {
            try {
                // 로또당첨판매점 크롤링
                val drwtStoreList = this.crawlingDrwtStore("")
                if(drwtStoreList.isEmpty()) {
                    log.error(" >>> [drwtStore] Fail Crawling DrwtStore")
                    contribution.exitStatus = ExitStatus.FAILED
                    return RepeatStatus.FINISHED
                }

                // 로또당첨판매점 저장
                for(drwtStore: DrwtStoreDto in drwtStoreList) {
                    drwtStoreRepository.insertLottoDrwtStore(input = drwtStore)
                }
            }catch (e: Exception) {
                log.error(" >>> [drwtStore] Exception occurs - message: ${e.message}")
                contribution.exitStatus = ExitStatus.FAILED
                return RepeatStatus.FINISHED
            }
        }else {
            // ::::::::::::::: 수동배치처리 :::::::::::::::
            try {
                log.info(" >>> [drwtStore][manual] MANUAL MODE !! - startDrwtNo: $startDrwtNoParam, endDrwtNo: $endDrwtNoParam")

                if(startDrwtNoParam == "" || endDrwtNoParam == "") {
                    log.warn(" >>> [drwtStore][manual] invalid parameter - startDrwtNo: $startDrwtNoParam, endDrwtNo: $endDrwtNoParam")
                    contribution.exitStatus = ExitStatus.FAILED
                    return RepeatStatus.FINISHED
                }

                val startDrwtNo = startDrwtNoParam.toLong()
                val endDrwtNo = endDrwtNoParam.toLong()

                if(startDrwtNo > endDrwtNo) {
                    // 유효성체크
                    log.warn(" >>> [drwtStore][manual] invalid parameter - startDrwtNo: $startDrwtNo, endDrwtNo: $endDrwtNo")
                    contribution.exitStatus = ExitStatus.FAILED
                    return RepeatStatus.FINISHED
                }else if(startDrwtNo == endDrwtNo) {
                    // startDrwtNo와 endDrwtNo가 동일한 회차인 경우
                    // 로또당첨판매점 크롤링
                    val drwtStoreList = this.crawlingDrwtStore(drwtNoParam = endDrwtNo.toString())
                    if(drwtStoreList.isEmpty()) {
                        log.error(" >>> [drwtStore][manual] Fail Crawling DrwtStore")
                        contribution.exitStatus = ExitStatus.FAILED
                        return RepeatStatus.FINISHED
                    }

                    // 로또당첨판매점 저장
                    for(drwtStore: DrwtStoreDto in drwtStoreList) {
                        drwtStoreRepository.insertLottoDrwtStore(input = drwtStore)
                    }
                }else {
                    // startDrwtNo와 endDrwtNo가 다른 회차인 경우
                    for(drwtNo: Long in startDrwtNo..endDrwtNo) {
                        // 로또당첨판매점 크롤링
                        val drwtStoreList = this.crawlingDrwtStore(drwtNoParam = drwtNo.toString())
                        if(drwtStoreList.isEmpty()) {
                            log.error(" >>> [drwtStore][manual] Fail Crawling DrwtStore")
                            contribution.exitStatus = ExitStatus.FAILED
                            return RepeatStatus.FINISHED
                        }

                        // 로또당첨판매점 저장
                        for(drwtStore: DrwtStoreDto in drwtStoreList) {
                            drwtStoreRepository.insertLottoDrwtStore(input = drwtStore)
                        }
                    }
                }
            }catch (e: Exception) {
                log.error(" >>> [drwtStore][manual] Exception occurs - message: ${e.message}")
                contribution.exitStatus = ExitStatus.FAILED
                return RepeatStatus.FINISHED
            }
            // ::::::::::::::: // 수동배치처리 :::::::::::::::
        }

        log.info(" >>> [drwtStore] BATCH END ########")
        return RepeatStatus.FINISHED
    }

    /**
     * 로또당첨판매점 크롤링
     *
     * @param drwtNoParam [String]
     * @return [List]<[DrwtStoreDto]>
     * @author yoonho
     * @since 2023.07.03
     */
    private fun crawlingDrwtStore(drwtNoParam: String): List<DrwtStoreDto> {
        val result = mutableListOf<DrwtStoreDto>()

        try {
            // 타겟서버 호출
            val docs = Jsoup
                .connect("https://www.dhlottery.co.kr/store.do?method=topStore&pageGubun=L645")
                .timeout(3000)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .data("drwNo", drwtNoParam)
                .post()

            // 로또회차 설정
            var drwtNo = 0L
            if(drwtNoParam.isEmpty()) {
                val drwtNos = docs.select("#drwNo")[0].select("option")
                for(item: Element in drwtNos) {
                    if(item.hasAttr("selected")) {
                        drwtNo = item.text().toLong()
                    }
                }
            }else {
                drwtNo = drwtNoParam.toLong()
            }

            // 당첨로또판매점정보 크롤링
            val tbody = docs.select("tbody")
            if(tbody.size > 0) {
                val trs = tbody[1].select("tr")
                for(tr: Element in trs) {
                    val td = tr.select("td")

                    val drwtStoreDto = DrwtStoreDto(
                        drwtNo = drwtNo,
                        drwtOrder = td[0].text().toLong(),
                        drwtRank = 1,
                        rtlrid = td[4].children().attr("onclick").replace("javascript:showMapPage('", "").replace("')", ""),
                        firmnm = td[1].text(),
                        bplcdorodtladres = td[3].text(),
                        drwtType = td[2].text(),
                        createdAt = LocalDateTime.now()
                    )

                    log.info(" >>> [crawlingDrwtStore] drwtStoreDto: $drwtStoreDto")
                    result.add(drwtStoreDto)
                }
            }

            return result
        }catch (e: Exception) {
            log.error(" >>> [crawlingDrwtStore] Exception occurs - message: ${e.message}")
            return listOf()
        }
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        return super.afterStep(stepExecution)
    }
}