# 📋 API 목록

## 더미 데이터 생성

### 👤 한국 이름 생성기

랜덤 한국 이름을 생성합니다. 토이 프로젝트의 더미 데이터로 활용하세요.

**엔드포인트**: `/dummy/korean-names`

#### 기능
- 랜덤 이름 생성 (남성/여성)
- 성별별 이름 생성
- 대량 생성 (최대 100개)

#### 사용 예시

```bash
# 랜덤 이름 1개
GET https://api.junkinone.com/dummy/korean-names/random

# 남성 이름
GET https://api.junkinone.com/dummy/korean-names/random/male

# 여성 이름
GET https://api.junkinone.com/dummy/korean-names/random/female

# 대량 생성 (10개)
GET https://api.junkinone.com/dummy/korean-names/bulk?count=10
```

#### 응답 예시

```json
{
  "name": "김민준",
  "gender": "male"
}
```

---

## 🚧 준비 중인 API

다음 API들이 곧 추가될 예정입니다:

- 한국 주소 생성기
- 한국 전화번호 생성기
- 이메일 생성기
- 회사명 생성기
- 주민등록번호 검증기
- 사업자등록번호 검증기
- 한국 공휴일 조회
- 한국 은행 코드
- 로또 번호 생성기

전체 개발 계획은 [API_ROADMAP.md](API_ROADMAP.md)에서 확인하세요.

---

**더 많은 API를 원하시나요?** [Issues](../../issues/new)에서 제안해주세요!