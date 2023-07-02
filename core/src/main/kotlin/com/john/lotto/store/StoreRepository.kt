package com.john.lotto.store

import com.john.lotto.entity.LottoStore
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author yoonho
 * @since 2023.07.02
 */
interface StoreRepository: JpaRepository<LottoStore, Float> {
}