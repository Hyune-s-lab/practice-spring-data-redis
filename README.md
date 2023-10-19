# practice-spring-data-redis

### feature

- V 코드 (UUID 난수코드) 생성 및 저장
- 동일한 userId 로 사용 불가능한 V 코드를 사용하는 경우 방어로직
    - 사용자별 사용 실패 누적 횟수는 10분간 유지
    - 사용자별 누적 5회 사용 실패시 10분간 V 코드 사용 시도 불가
    - (optional) V 코드 사용 성공시 실패 횟수 초기화

### environment

- spring boot 3.1.5 + kotlin
