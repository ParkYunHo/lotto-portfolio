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

//    @Bean
//    fun cacheManager(): CacheManager {
////        val caches = listOf(
////            CaffeineCache(
////                "auth-keys",
////                Caffeine.newBuilder().recordStats()
////                    .expireAfterWrite(60 * 30, TimeUnit.SECONDS)
////                    .maximumSize(10000)
////                    .build()
////            )
////        )
////
////        val cacheManager = SimpleCacheManager()
////        cacheManager.setCaches(caches)
//
//        val cacheManager = CaffeineCacheManager("baseCache")
//        cacheManager.setCaffeine(this.caffeineCacheBuilder())
//        return cacheManager
//    }
//
//    private fun caffeineCacheBuilder(): Caffeine<Any, Any> =
//        Caffeine.newBuilder()
//            .initialCapacity(100)
//            .maximumSize(1000)
//            .expireAfterWrite(Duration.ofMinutes(10L))

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
                    .expireAfterWrite(Duration.ofMinutes(30L))
                    .build()
            )
        )
}