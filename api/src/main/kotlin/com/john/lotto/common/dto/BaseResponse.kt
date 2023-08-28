package com.john.lotto.common.dto

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.06.22
 */
data class BaseResponse (
    val code: String?,
    val message: String?,
    val status: HttpStatus,
    val data: Any?
): Serializable {
    constructor(): this(code = "0", message = "Success", status = HttpStatus.OK, null)
    constructor(code: String?, message: String?, status: HttpStatus): this(code = code, message = message, status = status, null)

//    fun error(status: HttpStatus, message: String) =
//        ServerResponse.status(status)
//            .bodyValue(
//                BaseResponse(
//                    message = message,
//                    status = status,
//                    null
//                )
//            )

    fun success(data: Any?) =
        ServerResponse.ok().body(
            BodyInserters.fromValue(
                BaseResponse(
                    code = "0",
                    message = "Success",
                    status = HttpStatus.OK,
                    data = data
                )
            )
        )

//    fun successNoContent() =
//        ServerResponse.ok().body(
//            BodyInserters.fromValue(
//                BaseResponse(
//                    message = "Success",
//                    status = HttpStatus.OK,
//                    null
//                )
//            )
//        )
}