# 🗺️ API 개발 로드맵

Junkinone에 추가할 API 목록입니다. 위에서부터 차례대로 구현하세요!

---

## 📋 진행 상황

- ✅ 완료: 4개
- 🚧 진행중: 0개
- ⏳ 대기중: 20개

---

## 🎯 Phase 1: 더미 데이터 생성 API

### ✅ 1. 한국 이름 생성기
**엔드포인트**: `/api/korean-names`
**구현 방식**: 성씨 리스트 + 이름 글자 리스트 랜덤 조합
```kotlin
surnames = listOf("김", "이", "박", "최", "정", "강", "조", "윤", ...)
firstNames = listOf("민준", "서준", "지우", "서연", ...)
```
**응답 예시**:
```json
{
  "name": "김민준",
  "gender": "male"
}
```

### ⏳ 2. 한국 주소 생성기
**엔드포인트**: `/api/korean-address`
**구현 방식**: 시/도, 구/군, 동/읍/면, 번지 리스트 조합
```kotlin
cities = listOf("서울특별시", "부산광역시", ...)
districts = listOf("강남구", "서초구", ...)
dongs = listOf("역삼동", "논현동", ...)
```
**응답 예시**:
```json
{
  "address": "서울특별시 강남구 역삼동 123-45",
  "roadAddress": "서울특별시 강남구 테헤란로 152",
  "postalCode": "06234"
}
```

### ⏳ 3. 한국 전화번호 생성기
**엔드포인트**: `/api/korean-phone`
**구현 방식**: 010 + 랜덤 4자리 + 랜덤 4자리
```kotlin
fun generatePhone() = "010-${Random.nextInt(1000, 9999)}-${Random.nextInt(1000, 9999)}"
```
**응답 예시**:
```json
{
  "phone": "010-1234-5678",
  "formatted": "01012345678"
}
```

### ⏳ 4. 이메일 생성기
**엔드포인트**: `/api/email-generator`
**구현 방식**: 랜덤 이름 + 도메인 리스트
```kotlin
domains = listOf("gmail.com", "naver.com", "kakao.com", ...)
```
**응답 예시**:
```json
{
  "email": "minsu.kim@gmail.com",
  "username": "minsu.kim"
}
```

### ⏳ 5. 회사명 생성기
**엔드포인트**: `/api/company-name`
**구현 방식**: 형용사 + 명사 조합
```kotlin
adjectives = listOf("혁신적인", "창의적인", "글로벌", ...)
nouns = listOf("테크", "솔루션", "시스템", ...)
```
**응답 예시**:
```json
{
  "name": "혁신적인테크",
  "nameEn": "Innovative Tech"
}
```

---

## 🔍 Phase 2: 검증/유틸리티 API

### ⏳ 6. 주민등록번호 검증기
**엔드포인트**: `/api/validate/resident-number`
**구현 방식**: 체크섬 알고리즘
```kotlin
fun isValidResidentNumber(rrn: String): Boolean {
    // 체크섬 계산 로직
}
```
**요청**:
```json
POST /api/validate/resident-number
{ "number": "900101-1234567" }
```
**응답**:
```json
{
  "valid": true,
  "gender": "male",
  "birthYear": 1990
}
```

### ⏳ 7. 사업자등록번호 검증기
**엔드포인트**: `/api/validate/business-number`
**구현 방식**: 체크섬 알고리즘
**요청**:
```json
POST /api/validate/business-number
{ "number": "123-45-67890" }
```
**응답**:
```json
{
  "valid": true
}
```

### ⏳ 8. 비밀번호 생성기
**엔드포인트**: `/api/password-generator`
**구현 방식**: 대소문자 + 숫자 + 특수문자 랜덤 조합
```kotlin
fun generatePassword(length: Int, includeSpecial: Boolean): String
```
**응답**:
```json
{
  "password": "aB3$xY9!mN2@",
  "strength": "strong"
}
```

### ⏳ 9. UUID/ULID 생성기
**엔드포인트**: `/api/uuid`, `/api/ulid`
**구현 방식**: Java UUID 라이브러리 사용
**응답**:
```json
{
  "uuid": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 🇰🇷 Phase 3: 한국 정보 API

### ⏳ 10. 한국 공휴일 API
**엔드포인트**: `/api/korean-holidays`
**구현 방식**: 2025년 공휴일 하드코딩 리스트
```kotlin
data class Holiday(val date: String, val name: String, val isLunar: Boolean)
holidays = listOf(
    Holiday("2025-01-01", "신정", false),
    Holiday("2025-01-29", "설날", true),
    ...
)
```
**응답**:
```json
{
  "holidays": [
    { "date": "2025-01-01", "name": "신정", "isLunar": false },
    { "date": "2025-01-29", "name": "설날", "isLunar": true }
  ]
}
```

### ⏳ 11. 한국 지역 코드 API
**엔드포인트**: `/api/region-codes`
**구현 방식**: 행정구역 코드 리스트
```kotlin
data class Region(val code: String, val name: String, val level: String)
```
**응답**:
```json
{
  "regions": [
    { "code": "11", "name": "서울특별시", "level": "시도" },
    { "code": "1111", "name": "종로구", "level": "시군구" }
  ]
}
```

### ⏳ 12. 한국 은행 코드 API
**엔드포인트**: `/api/bank-codes`
**구현 방식**: 은행 코드 리스트
```kotlin
banks = listOf(
    Bank("004", "KB국민은행"),
    Bank("088", "신한은행"),
    ...
)
```
**응답**:
```json
{
  "banks": [
    { "code": "004", "name": "KB국민은행" },
    { "code": "088", "name": "신한은행" }
  ]
}
```

### ⏳ 13. 한국 대학교 목록 API
**엔드포인트**: `/api/universities`
**구현 방식**: 주요 대학교 리스트
**응답**:
```json
{
  "universities": [
    { "name": "서울대학교", "location": "서울", "type": "국립" },
    { "name": "연세대학교", "location": "서울", "type": "사립" }
  ]
}
```

---

## 🎲 Phase 4: 재미 요소 API

### ⏳ 14. 로또 번호 생성기
**엔드포인트**: `/api/lotto`
**구현 방식**: 1-45 중 중복 없이 6개 랜덤 선택
```kotlin
fun generateLotto(): List<Int> {
    return (1..45).shuffled().take(6).sorted()
}
```
**응답**:
```json
{
  "numbers": [3, 12, 23, 31, 38, 42],
  "bonus": 15
}
```

### ⏳ 15. 주사위/동전 던지기
**엔드포인트**: `/api/dice`, `/api/coin`
**구현 방식**: 랜덤 숫자
**응답**:
```json
// /api/dice
{ "result": 4 }

// /api/coin
{ "result": "앞면" }
```

### ⏳ 16. 랜덤 색상 생성기
**엔드포인트**: `/api/random-color`
**구현 방식**: RGB 랜덤 생성
**응답**:
```json
{
  "hex": "#3A7FD5",
  "rgb": "rgb(58, 127, 213)",
  "hsl": "hsl(210, 65%, 53%)"
}
```

### ⏳ 17. 점심 메뉴 추천
**엔드포인트**: `/api/lunch-menu`
**구현 방식**: 음식 리스트 랜덤
```kotlin
menus = listOf("김치찌개", "된장찌개", "불고기", "냉면", "비빔밥", ...)
```
**응답**:
```json
{
  "menu": "김치찌개",
  "category": "한식"
}
```

---

## 🔧 Phase 5: 변환 유틸리티 API

### ⏳ 18. 한글 초성 추출기
**엔드포인트**: `/api/korean/chosung`
**구현 방식**: 유니코드 계산
```kotlin
fun extractChosung(text: String): String {
    val chosungs = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
    // 유니코드 계산 로직
}
```
**요청**:
```json
POST /api/korean/chosung
{ "text": "대한민국" }
```
**응답**:
```json
{
  "original": "대한민국",
  "chosung": "ㄷㅎㅁㄱ"
}
```

### ⏳ 19. Base64 인코더/디코더
**엔드포인트**: `/api/base64/encode`, `/api/base64/decode`
**구현 방식**: Java Base64 라이브러리
**요청**:
```json
POST /api/base64/encode
{ "text": "Hello World" }
```
**응답**:
```json
{
  "encoded": "SGVsbG8gV29ybGQ="
}
```

### ⏳ 20. 색상 변환기
**엔드포인트**: `/api/color/convert`
**구현 방식**: HEX ↔ RGB ↔ HSL 변환
**요청**:
```json
POST /api/color/convert
{ "hex": "#FF5733" }
```
**응답**:
```json
{
  "hex": "#FF5733",
  "rgb": "rgb(255, 87, 51)",
  "hsl": "hsl(11, 100%, 60%)"
}
```

---

## 🎯 Phase 6: 공공데이터 가공 (나중에)

### ⏳ 21. 날씨 API (기상청)
**엔드포인트**: `/api/weather`
**구현 방식**: 공공데이터포털 기상청 API 연동 + 캐싱

### ⏳ 22. 미세먼지 API
**엔드포인트**: `/api/air-quality`
**구현 방식**: 에어코리아 API 연동 + 캐싱

### ⏳ 23. 약국 찾기 API
**엔드포인트**: `/api/pharmacy`
**구현 방식**: 공공데이터포털 약국 정보 연동

---

## 📝 구현 가이드

### 컨트롤러 파일 위치
```
src/main/kotlin/com/example/junkinone/
  ├── names/controller/KoreanNameController.kt
  ├── address/controller/KoreanAddressController.kt
  ├── phone/controller/PhoneNumberController.kt
  ├── validation/controller/ValidationController.kt
  ├── holidays/controller/KoreanHolidayController.kt
  ├── lotto/controller/LottoController.kt
  └── ...
```

### 구현 순서
1. 기존 컨트롤러(`cat`, `dog`, `quotes`) 중 하나를 복사
2. 패키지명/클래스명 변경
3. 데이터 리스트 작성
4. 로직 구현
5. Swagger 어노테이션 추가
6. 로컬 테스트
7. README.md 상태 업데이트 (⏳ → ✅)

### 예상 소요 시간
- 간단한 API (1-5번): 10-20분/개
- 중간 난이도 (6-17번): 30분/개
- 복잡한 API (18-23번): 1시간/개

**총 예상 시간: 1-2일**

---

## 🚀 시작하기

지금 바로 1번부터 시작하세요!

```bash
# 프로젝트 실행
./gradlew bootRun

# Swagger UI 확인
http://localhost:8080/swagger-ui.html
```