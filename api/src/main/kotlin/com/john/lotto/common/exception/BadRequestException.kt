package com.john.lotto.common.exception

/**
 * @author yoonho
 * @since 2023.06.22
 */
class BadRequestException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}