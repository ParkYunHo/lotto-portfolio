# language: ko
기능: 인가코드요청 API 통합테스트
  모든 사용자는 인가코드요청 API를 호출할 수 있다.

  @auth-authorize @docs
  시나리오 개요: 인가코드요청 API를 호출한다.
    먼저 인가코드요청API 호출을 한다
    만약 인가코드요청API "<url>" 요청하면
    그러면 인가코드요청API 호출결과 <statusCode> 확인한다

    예:
      | url             | statusCode |
      | /auth/authorize | 307        |