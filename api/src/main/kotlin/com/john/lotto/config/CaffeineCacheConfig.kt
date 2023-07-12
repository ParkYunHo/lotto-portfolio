package com.john.lotto.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * @author yoonho
 * @since 2023.07.12
 */
@EnableCaching
@Configuration
class CaffeineCacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val caches = this.caffeineCacheListBuilder()

        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caches)

        return cacheManager
    }

    private fun caffeineCacheListBuilder() =
        listOf(
            // openid publicKey
            CaffeineCache(
                "auth.keys",
                Caffeine.newBuilder().recordStats()
                    .initialCapacity(100)
                    .maximumSize(10000)
                    .expireAfterWrite(Duration.ofMinutes(60L))      // 1시간
                    .build()
            ),
            // 당첨번호 통계
            CaffeineCache(
                "statics.common",
                Caffeine.newBuilder().recordStats()
                    .initialCapacity(100)
                    .maximumSize(10000)
                    .expireAfterWrite(Duration.ofMinutes(60L))      // 1시간
                    .build()
            ),
            // 당첨번호 조회 (특정번호 조회)
            CaffeineCache(
                "number.one",
                Caffeine.newBuilder().recordStats()
                    .initialCapacity(100)
                    .maximumSize(10000)
                    .expireAfterWrite(Duration.ofMinutes(60L))      // 1시간
                    .build()
            ),
            CaffeineCache(
                "number.latest",
                Caffeine.newBuilder().recordStats()
                    .initialCapacity(100)
                    .maximumSize(10000)
                    .expireAfterWrite(Duration.ofMinutes(10L))      // 10분
                    .build()
            ),
        )
}