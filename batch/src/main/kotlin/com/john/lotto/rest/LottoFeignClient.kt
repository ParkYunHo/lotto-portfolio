package com.john.lotto.rest

import com.john.lotto.config.OpenFeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author yoonho
 * @since 2023.06.28
 */
@FeignClient(name = "lottoFeignClient", url = "https://www.dhlottery.co.kr", configuration = [OpenFeignConfig::class])
interface LottoFeignClient {

    /**
     * 회차별 당첨금액 조회
     *
     * @param method [String]
     * @param drwNo [String]
     * @return [String]
     * @author yoonho
     * @since 2023.06.28
     */
    @GetMapping("/common.do")
    fun lottoNumber(
        @RequestParam method: String,
        @RequestParam drwNo: String
    ): String
}