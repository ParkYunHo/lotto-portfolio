# language: ko
기능: 로또번호 조회 API 통합테스트 edge
  모든 사용자는 로또번호 조회 API 호출에 실패할 수 있다.

  @number @docs @edge
  시나리오 개요: 로또번호조회 API를 호출한다.
    먼저 실패 로또번호조회API 호출을 위한 <drwtNo> 있다
    만약 실패 로또번호조회API "<url>" 요청하면
    그러면 실패 로또번호조회API 호출결과 <statusCode> 확인한다

    예:
      | drwtNo | url         | statusCode |
      | 9999   | /api/number | 400        |