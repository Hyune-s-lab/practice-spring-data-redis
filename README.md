# practice-spring-data-redis

### environment

- spring boot 3.1.5 + kotlin
- redis 7.0.4

```shell
# redis docker 실행
$ ./run_redis.sh
```

## feature

### v1

- 항상 실패하는 V 코드 사용 기능 구현
- 동일한 userId 로 사용 불가능한 V 코드를 사용하는 경우 방어로직
    - 사용자별 누적 5회 사용 실패시 blocked user 처리
    - blocked user 는 V 코드 사용 시도 불가

### v2

- 동일한 userId 로 사용 불가능한 V 코드를 사용하는 경우 방어로직
    - redis 로 reported count 를 관리 합니다.
    - reported count 는 1분의 expired time 을 가집니다. 
