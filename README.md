# 🎓 Junkinone

**한국 학생 개발자를 위한 무료 API 모음**

> 토이 프로젝트 만들 때 필요한 API, 여기 다 있습니다.

## 💡 왜 만들었나요?

학생 개발자들이 토이 프로젝트 만들 때:
- 백엔드 API 만들기 귀찮음
- 공공데이터 사용법 복잡함
- 연습용 더미 데이터 필요함
- 한국어 데이터 찾기 어려움

**Junkinone은 이런 API들을 무료로 제공합니다.**

## 📊 현재 제공 중인 API

- 🐱 Cat Facts API - 고양이 관련 재미있는 사실
- 🐶 Dog Breeds API - 개 품종 정보
- 💬 Random Quotes API - 랜덤 명언
- 🏙️ Korean Cities API - 한국 도시 정보

**더 많은 API 준비 중...**

## 🚀 사용 방법

모든 API는 [apinuri.com](https://apinuri.com)에서 확인하고 사용하세요.

- API 목록 및 문서
- 사용 예시 코드
- Swagger UI로 바로 테스트

## 🤝 기여하기 (Contribute)

새로운 API 아이디어가 있거나 직접 추가하고 싶으신가요?

### API 제안하기
[Issues](../../issues/new)에서 이런 API 있으면 좋겠다고 제안해주세요!

### 직접 API 추가하기

1. **Fork & Clone**
```bash
git fork https://github.com/ekaylab/junkinone
git clone https://github.com/YOUR_USERNAME/junkinone
```

2. **새 API 만들기**
```
src/main/kotlin/com/example/junkinone/
  └── {domain}/
      └── controller/
          └── {YourApi}Controller.kt
```

3. **패턴 따라하기**
기존 컨트롤러 (`cat`, `dog`, `quotes` 등) 보고 똑같이 만들면 됩니다.

4. **Pull Request**
PR 보내주시면 리뷰 후 머지하겠습니다!

### 어떤 API가 좋을까요?

✅ **환영하는 API**
- 학생들 토이 프로젝트에 유용한 것
- 한국어/한국 특화 데이터
- 공공데이터포털 가공
- 재미있고 실용적인 유틸리티

❌ **적합하지 않은 API**
- 단순 CRUD만 있는 것
- 너무 복잡한 비즈니스 로직
- 개인정보 포함

## 🤝 참여하기

- **API 제안**: [Issues](../../issues/new)
- **코드 기여**: Pull Request 환영
- **피드백**: [Discussions](../../discussions)

## 📧 문의

- 이메일: ekankr2@gmail.com
- 웹사이트: [apinuri.com](https://apinuri.com)

---

Made with ❤️ by [apinuri](https://github.com/ekaylab/apinuri)
