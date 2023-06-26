# language: ko
기능: 로또번호 조회 API 통합테스트
  모든 사용자는 로또번호 조회 API를 호출할 수 있다.

  @number
  시나리오 개요: 로또번호조회 API를 호출한다.
    먼저 로또번호조회API 호출을 위한 <drwtNo> 있다
    만약 로또번호조회API "<url>" 요청하면
    그러면 로또번호조회API 호출결과 <statusCode> 확인한다

    예:
      | drwtNo | url         | statusCode |
      | 1072   | /api/number | 200        |
      | 9999   | /api/number | 400        |