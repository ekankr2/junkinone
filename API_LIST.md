# 📋 API 목록

## 더미 데이터 생성

### 👤 한국 이름 생성기

랜덤 한국 이름을 생성합니다.

**엔드포인트**: `/dummy/korean-names`

#### 기능
- 랜덤 이름 생성 (남성/여성)
- 성별별 이름 생성
- 대량 생성 (최대 100개)

#### 사용 예시

```bash
# 이름 1개
GET https://api.junkinone.com/dummy/korean-names

# 남성 이름
GET https://api.junkinone.com/dummy/korean-names/male

# 여성 이름
GET https://api.junkinone.com/dummy/korean-names/female

# 대량 생성 (10개)
GET https://api.junkinone.com/dummy/korean-names/bulk?count=10
```

---

### 🏢 회사명 생성기

한국 스타일의 그럴듯한 회사명을 생성합니다.

**엔드포인트**: `/dummy/company-names`

#### 기능
- 랜덤 회사명 생성
- 정식 회사명 생성 (주식회사, 유한회사 등 포함)
- 축약형 생성 ((주), (유) 등 포함)
- 대량 생성 (최대 100개)

#### 사용 예시

```bash
# 회사명 1개
GET https://api.junkinone.com/dummy/company-names

# 정식 회사명 (주식회사 OO)
GET https://api.junkinone.com/dummy/company-names/with-type

# 축약형 ((주)OO)
GET https://api.junkinone.com/dummy/company-names/short

# 대량 생성 (10개)
GET https://api.junkinone.com/dummy/company-names/bulk?count=10
```

#### 응답 예시

```json
{
  "name": "한국소프트",
  "type": "일반",
  "english": "Korea Soft Inc."
}
```
## 🚧 준비 중인 API

다음 API들이 곧 추가될 예정입니다:

- 한국 주소 생성기
- 주민등록번호 검증기
- 사업자등록번호 검증기
- 한국 공휴일 조회
- 한국 은행 코드
- 로또 번호 생성기

전체 개발 계획은 [API_ROADMAP.md](API_ROADMAP.md)에서 확인하세요.

---

**더 많은 API를 원하시나요?** [Issues](../../issues/new)에서 제안해주세요!