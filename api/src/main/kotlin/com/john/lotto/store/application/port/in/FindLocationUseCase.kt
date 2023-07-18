package com.john.lotto.store.application.port.`in`

import com.john.lotto.store.dto.LocationDto
import reactor.core.publisher.Flux

/**
 * @author yoonho
 * @since 2023.07.18
 */
interface FindLocationUseCase {

    /**
     * 판매점 위치정보 조회
     *
     * @return [Flux]<[Flux]>
     * @author yoonho
     * @since 2023.07.18
     */
    fun findLocation(): Flux<LocationDto>
}