# API 목록

> 마지막 업데이트: 2025-10-28

## 🐱 동물/펫 (2개)

### Cat Facts API
**엔드포인트**:
- `GET /api/catfacts/random` - 랜덤 고양이 사실
- `GET /api/catfacts/all` - 모든 고양이 사실

**개발자**: @student_john
**원본**: [github.com/student_john/cat-facts](https://github.com/student_john/cat-facts)

**사용 예시**:
```bash
curl http://localhost:8080/api/catfacts/random
```

---

### Dog Breeds API
**엔드포인트**:
- `GET /api/dogbreeds` - 모든 견종 목록
- `GET /api/dogbreeds/{breedId}` - 특정 견종 정보
- `GET /api/dogbreeds/random` - 랜덤 견종

**개발자**: @student_sarah
**원본**: [github.com/student_sarah/dog-breeds-api](https://github.com/student_sarah/dog-breeds-api)

**사용 예시**:
```bash
curl http://localhost:8080/api/dogbreeds/golden-retriever
```

---

## 💬 명언/인용구 (1개)

### Random Quotes API
**엔드포인트**:
- `GET /api/quotes/random` - 랜덤 명언
- `GET /api/quotes/all` - 모든 명언
- `GET /api/quotes/daily` - 오늘의 명언

**개발자**: @student_mike
**원본**: [github.com/student_mike/motivational-quotes](https://github.com/student_mike/motivational-quotes)

**사용 예시**:
```bash
curl http://localhost:8080/api/quotes/random
```

---

## 🗺 위치/지도 (1개)

### Korean Cities API
**엔드포인트**:
- `GET /api/korean-cities` - 모든 도시 목록
- `GET /api/korean-cities/random` - 랜덤 도시
- `GET /api/korean-cities/region/{region}` - 지역별 도시

**개발자**: @student_jimin
**원본**: [github.com/student_jimin/korean-cities](https://github.com/student_jimin/korean-cities)

**사용 예시**:
```bash
curl http://localhost:8080/api/korean-cities/region/Capital%20Area
```

---

## 🌦 날씨 (0개)

Coming soon...

## 💱 금융 (0개)

Coming soon...

## 📊 데이터 (0개)

Coming soon...

---

## 템플릿 (첫 API 등록되면 이렇게)

### 날씨 API

**설명**: 전국 날씨 정보 조회

**엔드포인트**: `GET /api/weather`

**개발자**: 김철수 ([@kimcs](https://github.com/kimcs))  
**출처**: 웹 3기  
**원본**: [github.com/kimcs/weather-api](https://github.com/kimcs/weather-api)

**사용 예시**:
```bash
curl https://junkinone.com/api/weather?city=서울
```

**응답**:
```json
{
  "city": "서울",
  "temperature": 5.2,
  "status": "맑음"
}
```

**통계**: 
- 일 평균 호출: 487회
- 에러율: 0.1%

---

**더 추가...**
