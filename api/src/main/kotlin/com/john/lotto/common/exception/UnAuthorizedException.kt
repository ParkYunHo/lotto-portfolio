package com.john.lotto.common.exception

/**
 * @author yoonho
 * @since 2023.07.11
 */
class UnAuthorizedException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}