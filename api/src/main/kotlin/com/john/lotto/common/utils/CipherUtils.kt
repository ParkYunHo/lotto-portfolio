package com.john.lotto.common.utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author yoonho
 * @since 2023.07.19
 */
object CipherUtils {

    fun encode(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(str.toByteArray())
        return BigInteger(1, md.digest()).toString()
    }

    fun validate(sourceText: String, encodedText: String): Boolean {
        val md = MessageDigest.getInstance("MD5")
        md.update(sourceText.toByteArray())

        val source = BigInteger(1, md.digest()).toString()

        return source == encodedText
    }
}