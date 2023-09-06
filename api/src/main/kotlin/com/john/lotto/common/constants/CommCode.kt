package com.john.lotto.common.constants

/**
 * @author yoonho
 * @since 2023.07.19
 */
class CommCode {

    companion object {
        val goodPlaceCnt: Int = 3

        val taxLimitAmount: Long = 300000000    // 당첨금 세금부과 기준금액
        val taxOverLimit: Float = 0.33F      // 당첨금 3억 초과시 33% 세금 공제
        val taxUnderLimit: Float = 0.22F     // 당첨금 3억 이하시 22% 세금 공제
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

    /**
     * 소셜로그인 구분
     *
     * @author yoonho
     * @since 2023.08.28
     */
    enum class Social(val code: String, val desc: String) {
        KAKAO("kakao", "카카오")
        ;
    }

    /**
     * 인증에러코드 구분
     *
     * @author yoonho
     * @since 2023.08.28
     */
    enum class ErrorCode(val code: String, val desc: String) {
        NOT_AUTHORIZED_EXPIRED("100", "만료시간이 지난 토큰입니다"),
        NOT_AUTHORIZED_ISSUER("101", "유효한 발급인증기관이 아닙니다"),
        NOT_AUTHORIZED_AUDIENCE("102", "유효한 서비스앱키가 아닙니다"),
        NOT_AUTHORIZED_SIGN("103", "유효한 서명이 아닙니다"),
        NOT_AUTHORIZED_NOT_FOUND_HEADER("104", "Authorization 헤더가 존재하지 않습니다"),
        NOT_AUTHORIZED_NOT_FOUND_MEMBER("105", "등록된 회원이 아닙니다"),
        NOT_AUTHORIZED_NOT_FOUND_TOKEN("106", "필수 인증정보가 존재하지 않습니다"),
        ;
    }
}