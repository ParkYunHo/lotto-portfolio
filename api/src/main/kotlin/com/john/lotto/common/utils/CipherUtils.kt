package com.john.lotto.common.utils

import com.john.lotto.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * @author yoonho
 * @since 2023.07.09
 */
object CipherUtils {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun encode(token: String): String {
        try {
            val cipher = Cipher.getInstance("AES")
            val secretKey = EnvironmentUtils.getProperty("auth.key.secret-key", "")
            val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            return Base64.getEncoder().encodeToString(cipher.doFinal(token.toByteArray())).toString()
        }catch (e: Exception) {
            log.info(" >>> [encode] Exception occurs - message: ${e.message}")
            throw BadRequestException("")
        }
    }

    fun decode(token: String): String {
        try {
            val cipher = Cipher.getInstance("AES")
            val secretKey = EnvironmentUtils.getProperty("auth.key.secret-key", "")
            val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            return cipher.doFinal(Base64.getDecoder().decode(token.toByteArray())).toString()
        }catch (e: Exception) {
            throw BadRequestException("")
        }
    }
}