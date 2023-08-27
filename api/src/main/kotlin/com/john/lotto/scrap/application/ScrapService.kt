package com.john.lotto.scrap.application

import com.john.lotto.common.exception.BadRequestException
import com.john.lotto.scrap.StoreScrapRepository
import com.john.lotto.scrap.adatper.`in`.web.dto.ScrapResult
import com.john.lotto.scrap.application.port.`in`.DeleteStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.FindStoreScrapUseCase
import com.john.lotto.scrap.application.port.`in`.RegisterScrapUseCase
import com.john.lotto.scrap.dto.StoreScrapDto
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
    private val storeScrapRepository: StoreScrapRepository
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
     * @return [Flux]<[StoreScrapDto]>
     * @author yoonho
     * @since 2023.07.23
     */
    override fun search(userId: String): Flux<StoreScrapDto> {
        val scrapInfo = storeScrapRepository.findStoreScrapList(userId = userId)
        return Flux.fromIterable(scrapInfo)
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