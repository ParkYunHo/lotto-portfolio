package com.john.lotto.job.number.tasklet

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.john.lotto.amount.AmountRepository
import com.john.lotto.amount.dto.LottoWinAmountDto
import com.john.lotto.number.NumberRepository
import com.john.lotto.number.dto.LottoNumberDto
import com.john.lotto.rest.LottoFeignClient
import com.john.lotto.rest.dto.TotalLottoNumberDto
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
@StepScope
class LottoNumberTasklet(
    private val numberRepository: NumberRepository,
    private val amountRepository: AmountRepository,

    private val lottoFeignComponent: LottoFeignClient,
): Tasklet, StepExecutionListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        super.beforeStep(stepExecution)
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info(" >>> [number] BATCH START ########")
        val stepContext = chunkContext.stepContext
        val jobParameters = stepContext.jobParameters

        val startDrwtNo = jobParameters.getOrDefault("startDrwtNo", "").toString()
        val endDrwtNo = jobParameters.getOrDefault("endDrwtNo", "").toString()

        if(startDrwtNo == "" && endDrwtNo == "") {
            try {
                // 마지막 로또회차 조회
                val lastDrwtNo = numberRepository.findLastDrwtNo() ?: 0L

                // 로또 당첨정보 조회 (by, 로또API)
                val responseStr = lottoFeignComponent.lottoNumber(method = "getLottoNumber", drwNo = lastDrwtNo.plus(1).toString())
                val response = Gson().fromJson(JsonParser.parseString(responseStr), TotalLottoNumberDto::class.java)
                log.info(" >>> [number] response: $response")

                if(response.returnValue == "success") {
                    // 로또번호 저장
                    val lottoNumberDto = LottoNumberDto(
                        drwtNo = response.drwNo,
                        drwtNo1 = response.drwtNo1,
                        drwtNo2 = response.drwtNo2,
                        drwtNo3 = response.drwtNo3,
                        drwtNo4 = response.drwtNo4,
                        drwtNo5 = response.drwtNo5,
                        drwtNo6 = response.drwtNo6,
                        bnusNo = response.bnusNo,
                    )
                    numberRepository.insertLottoNumber(input = lottoNumberDto)
                    log.info(" >>> [number] Save lottoNumber - input: $lottoNumberDto")

                    // 로또 당첨금 저장
                    val lottoWinAmountDto = LottoWinAmountDto(
                        drwtNo = response.drwNo,
                        drwtDate = LocalDate.parse(response.drwNoDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        totSellamnt = response.totSellamnt,
                        firstWinamnt = response.firstWinamnt,
                        firstPrzwnerCo = response.firstPrzwnerCo,
                        firstAccumamnt = response.firstAccumamnt
                    )
                    amountRepository.insertLottoWinAmount(input = lottoWinAmountDto)
                    log.info(" >>> [number] Save lottoWinAmount - input: $lottoWinAmountDto")
                }else {
                    log.warn(" >>> [number] Call Lotto API fail - lastDrwtNo: $lastDrwtNo")
                    contribution.exitStatus = ExitStatus.FAILED
                    return RepeatStatus.FINISHED
                }
            }catch (e: Exception) {
                log.error(" >>> [number] Exception occurs - message: ${e.message}")
                contribution.exitStatus = ExitStatus.FAILED
                return RepeatStatus.FINISHED
            }
        }else {
            // 수동배치처리
            try{
                log.info(" >>> [number][manual] MANUAL MODE !! - startDrwtNo: $startDrwtNo, endDrwtNo: $endDrwtNo")

                if(startDrwtNo == "" || endDrwtNo == "") {
                    log.warn(" >>> [number][manual] invalid parameter - startDrwtNo: $startDrwtNo, endDrwtNo: $endDrwtNo")
                    contribution.exitStatus = ExitStatus.FAILED
                    return RepeatStatus.FINISHED
                }




            }catch (e: Exception) {
                log.error(" >>> [number][manual] Exception occurs - message: ${e.message}")
                contribution.exitStatus = ExitStatus.FAILED
                return RepeatStatus.FINISHED
            }
        }

        log.info(" >>> [number] BATCH END ########")
        return RepeatStatus.FINISHED
    }

}