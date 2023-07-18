package com.john.lotto.store.application

import com.john.lotto.drwtstore.DrwtStoreRepository
import com.john.lotto.store.StoreRepositoryImpl
import com.john.lotto.store.application.dto.LottoStoreTotalInfo
import com.john.lotto.store.application.port.`in`.FindLocationUseCase
import com.john.lotto.store.application.port.`in`.FindStoreUseCase
import com.john.lotto.store.dto.LocationDto
import com.john.lotto.store.dto.LottoStoreDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 * @author yoonho
 * @since 2023.07.18
 */
@Service
class StoreService(
    private val storeRepositoryImpl: StoreRepositoryImpl,
    private val drwtStoreRepository: DrwtStoreRepository
): FindStoreUseCase, FindLocationUseCase {

    /**
     * 로또 판매점 조회
     *
     * @param location [String]
     * @param subLocation [String]
     * @return [Flux]<[LottoStoreTotalInfo]>
     * @author yoonho
     * @since 2023.07.18
     */
    override fun findStore(location: String, subLocation: String): Flux<LottoStoreTotalInfo> {
        val result = mutableListOf<LottoStoreTotalInfo>()

        val stores = storeRepositoryImpl.findLottoStore(location = location, subLocation = subLocation)
        val drwtStores = drwtStoreRepository.findLottoDrwtStore(ids = stores.map { it.rtlrid!! })

        for(store: LottoStoreDto in stores) {
            result.add(
                LottoStoreTotalInfo(
                    rtlrid = store.rtlrid,

                    latitude = store.latitude,
                    longitude = store.longitude,

                    bplclocplc1 = store.bplclocplc1,
                    bplclocplc2 = store.bplclocplc2,
                    bplclocplc3 = store.bplclocplc3,
                    bplclocplc4 = store.bplclocplc4,

                    bplcdorodtladres = store.bplcdorodtladres,
                    bplclocplcdtladres = store.bplclocplcdtladres,
                    rtlrstrtelno = store.rtlrstrtelno,
                    firmnm = store.firmnm,

                    drwtNos = drwtStores.filter { it.rtlrid == store.rtlrid }.map { it.drwtNo!! },
                )
            )
        }

        return Flux.fromIterable(result)
    }

    /**
     * 판매점 위치정보 조회
     *
     * @return [Flux]<[Flux]>
     * @author yoonho
     * @since 2023.07.18
     */
    override fun findLocation(): Flux<LocationDto> {
        val result = storeRepositoryImpl.findLocation()
            .filter { it.bplclocplc1 != "" && it.bplclocplc2 != "" }
        return Flux.fromIterable(result)
    }
}