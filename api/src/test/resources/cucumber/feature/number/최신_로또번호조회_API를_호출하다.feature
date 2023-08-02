# language: ko
기능: 최신 로또번호 조회 API 통합테스트
  모든 사용자는 최신 로또번호 조회 API를 호출할 수 있다.

  @number-latest @docs
  시나리오 개요: 최신 로또번호조회 API를 호출한다.
    먼저 최신 로또번호조회API 호출을 한다
    만약 최신 로또번호조회API "<url>" 요청하면
    그러면 최신 로또번호조회API 호출결과 <statusCode> 확인한다

    예:
      | url                | statusCode |
      | /api/number/latest | 200        |