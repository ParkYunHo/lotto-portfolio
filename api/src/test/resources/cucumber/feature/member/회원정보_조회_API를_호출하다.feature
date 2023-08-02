# language: ko
기능: 회원정보 조회 API 통합테스트
  모든 사용자는 회원정보 조회 API를 호출할 수 있다.

  @number @docs
  시나리오 개요: 회원정보 조회 API를 호출한다.
    먼저 회원정보조회API 호출을 한다
    만약 회원정보조회API "<url>""<method>" 요청하면
    그러면 회원정보조회API 호출결과 <statusCode> 확인한다

    예:
      | url         | method | statusCode |
      | /api/member | get    | 200        |