package com.john.lotto.common.exception

/**
 * @author yoonho
 * @since 2023.07.11
 */
class UnAuthorizedException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(msg: String?, code: String?): super(msg) {
        this.code = code
    }
    constructor(): super()

    var code: String? = ""
}