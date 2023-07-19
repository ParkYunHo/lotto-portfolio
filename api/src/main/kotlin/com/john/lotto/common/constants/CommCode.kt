package com.john.lotto.common.constants

/**
 * @author yoonho
 * @since 2023.07.19
 */
class CommCode {

    companion object {
        val goodPlaceCnt: Int = 3
    }

    /**
     * 로또판매점 조회시 정렬옵션
     *
     * @author yoonho
     * @since 2023.07.19
     */
    enum class Sort(val code: String, val desc: String) {
        NAME("0", "판매점이름 오름차순(default)"),
        PRICE("1", "1등당첨금액별 오름차순"),
        LATEST("2", "최신회차 당첨별 오름차순")
        ;
    }

    /**
     * 로또판매점 조회시 명당,전체 조회옵션
     *
     * @author yoonho
     * @since 2023.07.19
     */
    enum class Option(val code: String, val desc: String) {
        ALL("0", "명당을 포함한 전체 판매점(default)"),
        GOOD_PLACE("1", "명당만 포함한 판매점"),
        ALL_EXCEPT_GOOD_PLACE("2", "명당을 제외한 판매점")
        ;
    }

    enum class Social(val code: String, val desc: String) {
        KAKAO("kakao", "카카오")
        ;
    }
}