# 개발자를 위한 가이드

## 🎯 Junkinone API란?

부트캠프 학생들이 만든 **무료 한국형 API** 모음입니다.

### 왜 사용하나요?

✅ **완전 무료** - 과금 없음  
✅ **한국 데이터** - 한국 특화 API  
✅ **학생 지원** - 사용하면 학생 포트폴리오에 도움

## 📋 사용 가능한 API

[전체 API 목록 보기](api-list.md)

## 🔌 사용 방법

### 기본 사용
```bash
curl https://junkinone.com/api/weather?city=서울
```
```javascript
const response = await fetch('https://junkinone.com/api/weather?city=서울')
const data = await response.json()
```

### 인증

현재는 인증 없이 사용 가능합니다.  
(추후 변경될 수 있음)

## ⚠️ 제한사항

- Rate Limit: 1000 req/hour
- 상업적 사용 가능
- 안정성 보장 없음 (학생 프로젝트)

## 🤝 피드백

API에 문제가 있나요?  
[Issues에 제보](../../issues)

---

더 많은 API는 [apinuri.com](https://apinuri.com)
