# Spring Boot 프로젝트 배포 가이드

직접 배포를 원하는 학생을 위한 가이드입니다.

## Railway 배포 (추천)

### 1. 준비

- GitHub 저장소 public
- `build.gradle` 확인
- `application.yml` 환경변수 분리

### 2. Railway 가입

1. [railway.app](https://railway.app) 접속
2. GitHub 계정으로 가입
3. 무료 $5 크레딧 받기

### 3. 배포

1. "New Project" 클릭
2. "Deploy from GitHub repo" 선택
3. 저장소 선택
4. 자동 배포 시작

### 4. 환경변수 설정
```
DATABASE_URL=...
SPRING_PROFILES_ACTIVE=prod
```

### 5. 도메인 확인

`your-project.railway.app` 자동 할당

## Render 배포

(추후 작성)

## 문제 해결

### 빌드 실패
```bash
# build.gradle에 추가
tasks.named('test') {
    useJUnitPlatform()
}
```

### DB 연결 오류

application.yml 확인...

---

[처음으로](../README.md)
