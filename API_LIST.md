# 📋 API 목록

## 더미 데이터

### 👤 한국 이름 생성기

랜덤 한국 이름을 생성합니다.

- **엔드포인트**: `/dummy/korean-names`
- **파라미터**: `gender` (random, male, female)
- **대량 생성**: `/dummy/korean-names/bulk?count=10`

---

### 🏢 회사명 생성기

한국 회사명을 생성합니다. 한글명과 매칭되는 영문명도 함께 제공됩니다.

- **엔드포인트**: `/dummy/company-names`
- **파라미터**: `includeType` (true/false - 회사 형태 포함 여부)
- **대량 생성**: `/dummy/company-names/bulk?count=10`

---

### 🎮 닉네임 생성기

형용사 + 명사 형식의 한국어 닉네임을 생성합니다.

- **엔드포인트**: `/dummy/nicknames`
- **대량 생성**: `/dummy/nicknames/bulk?count=10`

---
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