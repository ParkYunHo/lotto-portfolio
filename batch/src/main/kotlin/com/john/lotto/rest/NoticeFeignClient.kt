package com.john.lotto.rest

import com.john.lotto.config.OpenFeignConfig
import com.john.lotto.rest.dto.LottoStoreInput
import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author yoonho
 * @since 2023.06.28
 */
@FeignClient(name = "noticeFeignClient", url = "https://hooks.slack.com", configuration = [OpenFeignConfig::class])
interface NoticeFeignClient {

    /**
     * Slack webhook
     *
     * @param input [LottoStoreInput]
     * @return [String]
     * @author yoonho
     * @since 2023.07.05
     */
    @PostMapping("/services/T04GGF8E1NE/B05GA7DV0EL/egILLKJMwnO1Utlvo4LU19CI", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun slack(
        @RequestBody input: Map<String, Any?>?
    ): String
}