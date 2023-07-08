package com.john.lotto.common.exception

/**
 * @author yoonho
 * @since 2023.06.21
 */
class InternalServerException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}