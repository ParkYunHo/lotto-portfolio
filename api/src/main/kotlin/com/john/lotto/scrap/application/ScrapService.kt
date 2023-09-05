package com.john.lotto.scrap.application

import com.john.lotto.amount.AmountRepository
import com.john.lotto.common.constants.CommCode
import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.drwtstore.DrwtStoreRepository
import com.john.lotto.scrap.StoreScrapRepository
import com.john.lotto.scrap.adatper.`in`.web.dto.ScrapResult
import com.john.lotto.scrap.application.port.`in`.DeleteStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.FindStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.RegisterScrapUseCase
import com.john.lotto.scrap.dto.StoreScrapDto
import com.john.lotto.store.StoreRepositoryImpl
import com.john.lotto.store.application.dto.LottoStoreTotalInfo
import com.john.lotto.store.dto.LottoStoreDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.07.23
 */
@Service
class ScrapService(
    private val storeScrapRepository: StoreScrapRepository,
    private val storeRepositoryImpl: StoreRepositoryImpl,
    private val drwtStoreRepository: DrwtStoreRepository,
    private val amountRepository: AmountRepository
): RegisterScrapUseCase, FindStoreScrapUseCase, DeleteStoreScrapUseCase {

    /**
     * 판매점스크랩 등록
     *
     * @param userId [String]
     * @param rtlrid [String]
     * @return [Mono]<[StoreScrapDto]>
     * @author yoonho
     * @since 2023.07.23
     */
    override fun register(userId: String, rtlrid: String): Mono<StoreScrapDto> {
        val existsScrap = storeScrapRepository.findStoreScrap(userId = userId, rtlrid = rtlrid)
        if(existsScrap != null) {
            throw BadRequestException("이미 등록된 판매점입니다 - userId: $userId, rtlrid: $rtlrid")
        }

        val param = StoreScrapDto(
            userId = userId,
            storeId = rtlrid,
            updatedAt = null,
            createdAt = LocalDateTime.now()
        )
        storeScrapRepository.insertStoreScrap(input = param)

        return Mono.just(param)
    }

    /**
     * 판매점스크랩 조회
     *
     * @param userId [String]
     * @return [Flux]<[LottoStoreTotalInfo]>
     * @author yoonho
     * @since 2023.09.05
     */
    override fun search(userId: String): Flux<LottoStoreTotalInfo> {
        val lottoStoreTotalInfoList = mutableListOf<LottoStoreTotalInfo>()

        // 스크랩정보 조회
        val scrapInfo = storeScrapRepository.findStoreScrapList(userId = userId)

        // 스크랩된 판매점 정보 조회
        val stores = storeRepositoryImpl.findLottoStoreByStoreId(storeIds = scrapInfo.map { it.storeId })
        val drwtStores = drwtStoreRepository.findLottoDrwtStore(ids = stores.map { it.rtlrid!! })
        val winAmounts = amountRepository.findLottoWinAmountList(drwtNos = drwtStores.map { it.drwtNo!! })

        for(store: LottoStoreDto in stores) {
            val drwtNos = drwtStores.filter { it.rtlrid == store.rtlrid }.map { it.drwtNo!! }
            val winAmount = winAmounts.filter { drwtNos.contains(it.drwtNo!!) }
            val drwtInfos: List<LottoStoreTotalInfo.DrwtInfo> = winAmount.map {
                LottoStoreTotalInfo.DrwtInfo(
                    drwtNo = it.drwtNo!!,
                    drwtDate = it.drwtDate!!,
                    firstWinAmount = it.firstWinamnt!!,
                    firstWinCount = it.firstPrzwnerCo!!
                )
            }

            lottoStoreTotalInfoList.add(
                LottoStoreTotalInfo(
                    storeId = store.rtlrid,

                    latitude = store.latitude,
                    longitude = store.longitude,

                    address1 = store.bplclocplc1,
                    address2 = store.bplclocplc2,
                    address3 = store.bplclocplc3,
                    address4 = store.bplclocplc4,

                    newAddress = store.bplcdorodtladres,
                    oldAddress = store.bplclocplcdtladres,
                    phoneNo = store.rtlrstrtelno,
                    storeName = store.firmnm,

                    isGoodPlace = if(drwtInfos.size >= CommCode.goodPlaceCnt) true else false,

                    drwtInfos = drwtInfos,
                )
            )
        }

        return Flux.fromIterable(lottoStoreTotalInfoList)
    }

    /**
     * 판매점스크랩 탈퇴
     *
     * @param userId [String]
     * @param storeId [String]
     * @return [Mono]<[ScrapResult]>
     * @author yoonho
     * @since 2023.07.23
     */
    override fun delete(userId: String, storeId: String): Mono<ScrapResult> {
        val result = storeScrapRepository.deleteStoreScrap(userId = userId, rtlrid = storeId)
        if(result <= 0) {
            throw BadRequestException("미등록된 판매점스크랩입니다 - userId: $userId, rtlrid: $storeId")
        }

        return Mono.just(ScrapResult(userId = userId, storeId = storeId))
    }
}