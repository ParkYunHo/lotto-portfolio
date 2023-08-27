package com.john.lotto.store.application

import com.john.lotto.amount.AmountRepository
import com.john.lotto.common.constants.CommCode
import com.john.lotto.drwtstore.DrwtStoreRepository
import com.john.lotto.store.StoreRepositoryImpl
import com.john.lotto.store.application.dto.LottoStoreTotalInfo
import com.john.lotto.store.application.port.`in`.FindLocationUseCase
import com.john.lotto.store.application.port.`in`.FindStoreUseCase
import com.john.lotto.store.dto.LocationDto
import com.john.lotto.store.dto.LottoStoreDto
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * @author yoonho
 * @since 2023.07.18
 */
@Service
class StoreService(
    private val storeRepositoryImpl: StoreRepositoryImpl,
    private val drwtStoreRepository: DrwtStoreRepository,
    private val amountRepository: AmountRepository
): FindStoreUseCase, FindLocationUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 로또 판매점 조회
     *
     * @param location [String]
     * @param subLocation [String]
     * @param sort [String]
     * @param option [String]
     * @return [Flux]<[LottoStoreTotalInfo]>
     * @author yoonho
     * @since 2023.07.18
     */
    @Cacheable(cacheNames = ["store.common"], key = "#location + ':' + #subLocation + ':' + #sort + ':' + #option", unless = "#result == null")
    override fun findStore(location: String, subLocation: String, sort: String, option: String): Flux<LottoStoreTotalInfo> {
        val lottoStoreTotalInfoList = mutableListOf<LottoStoreTotalInfo>()

        val stores = storeRepositoryImpl.findLottoStore(location = location, subLocation = subLocation)
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

                    drwtInfos = drwtInfos,
                )
            )
        }

        val result = this.sortStore(stores = lottoStoreTotalInfoList, sort = sort, option = option)
        log.info(" >>> [findStore] result: $result")
        return Flux.fromIterable(result)
    }

    private fun sortStore(stores: List<LottoStoreTotalInfo>, sort: String, option: String): List<LottoStoreTotalInfo> {
        // 명당,전체 조회 옵션처리
        var result = when(option) {
            CommCode.Option.GOOD_PLACE.code -> {
                stores.filter {
                    it.drwtInfos.size >= CommCode.goodPlaceCnt
                }
            }
            CommCode.Option.ALL_EXCEPT_GOOD_PLACE.code -> {
                stores.filter {
                    it.drwtInfos.size < CommCode.goodPlaceCnt
                }
            }
            else -> stores
        }

        // 정렬옵션
        result = when(sort) {
            CommCode.Sort.NAME.code -> {
                result.sortedBy { it.storeName }
            }
            CommCode.Sort.PRICE.code -> {
                result.sortedByDescending { store ->
                    store.drwtInfos.sumOf { it.firstWinAmount }
                }
            }
            CommCode.Sort.LATEST.code -> {
                result.sortedByDescending { store ->
                    store.drwtInfos.sumOf { it.drwtNo }
                }
            }
            else -> result.sortedBy { it.storeName }
        }

        return result
    }

    /**
     * 판매점 위치정보 조회
     *
     * @return [Flux]<[LocationDto]>
     * @author yoonho
     * @since 2023.07.18
     */
    @Cacheable(cacheNames = ["store.location"], unless = "#result == null")
    override fun findLocation(): Flux<LocationDto> {
        val result = storeRepositoryImpl.findLocation()
            .filter { it.address1 != "" && it.address2 != "" }

        log.info(" >>> [findLocation] result: $result")
        return Flux.fromIterable(result)
    }
}