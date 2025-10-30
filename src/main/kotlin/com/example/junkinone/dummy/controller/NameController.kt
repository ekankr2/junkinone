package com.example.junkinone.dummy.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

/**
 * Korean Name Generator API
 * Generates random Korean names for dummy data
 */
@RestController
@RequestMapping("/korean-names")
class NameController : DummyController() {

    data class KoreanNameResponse(
        val name: String,
        val gender: String
    )

    data class BulkNamesResponse(
        val names: List<KoreanNameResponse>,
        val count: Int
    )

    private val surnames = listOf(
        "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
        "한", "오", "서", "신", "권", "황", "안", "송", "류", "전"
    )

    private val maleFirstNames = listOf(
        "민준", "서준", "예준", "도윤", "시우", "주원", "하준", "지호", "지후", "준서",
        "건우", "우진", "선우", "서진", "민재", "현우", "지훈", "승우", "승현", "유준",
        "준혁", "은우", "시후", "준영", "정우", "승민", "성민", "재윤", "태윤", "민성",
        "재현", "수호", "동현", "수현", "원준", "연우", "민찬", "시현", "태현", "민혁",
        "태양", "민수", "준호", "성현", "지환", "재민", "승준", "동훈", "상현", "영준",
        "재훈", "성준", "진우", "상우", "준우", "형준", "성진", "재영", "동욱", "현준",
        "재호", "정민", "성수", "민호", "기현", "우빈", "도현", "시온", "규민", "은찬",
        "준수", "재우", "승호", "준기", "우성", "지훈", "현수", "도훈", "태민", "진혁"
    )

    private val femaleFirstNames = listOf(
        "서연", "지우", "서현", "민서", "하은", "지유", "수아", "지안", "윤서", "채원",
        "지민", "다은", "예은", "소율", "예린", "시은", "소윤", "하린", "지원", "가은",
        "하윤", "유진", "서아", "서윤", "예진", "수빈", "나은", "채은", "민지", "유나",
        "은서", "예나", "주아", "유하", "서은", "아인", "지아", "서영", "하율", "서진",
        "다인", "수연", "가윤", "은채", "하연", "채아", "민주", "지혜", "수진", "서정",
        "예원", "유주", "소민", "연서", "다연", "서희", "은지", "혜원", "은주", "예지",
        "지수", "주은", "세은", "민경", "하은", "유림", "나연", "세아", "하영", "예림",
        "수민", "주현", "지현", "수정", "예서", "보민", "가영", "은진", "다희", "채린"
    )

    @GetMapping
    @Operation(
        summary = "한국 이름 생성",
        description = """
            한국 이름을 생성합니다.

            **gender 파라미터:**
            - `random` (기본값): 랜덤 성별
            - `male`: 남성 이름
            - `female`: 여성 이름
        """
    )
    fun getName(@RequestParam(defaultValue = "random") gender: String): KoreanNameResponse {
        return generateName(gender)
    }

    @GetMapping("/bulk")
    @Operation(
        summary = "이름 대량 생성",
        description = """
            여러 개의 한국 이름을 생성합니다 (최대 100개).

            **파라미터:**
            - `count`: 생성할 이름 개수 (기본값: 10, 최대: 100)
            - `gender`: 성별 (기본값: random, 옵션: male, female, random)
        """
    )
    fun getBulkNames(
        @RequestParam(defaultValue = "10") count: Int,
        @RequestParam(defaultValue = "random") gender: String
    ): BulkNamesResponse {
        val actualCount = minOf(count, 100)
        val names = (1..actualCount).map {
            generateName(gender)
        }
        return BulkNamesResponse(
            names = names,
            count = actualCount
        )
    }

    private fun generateName(gender: String): KoreanNameResponse {
        val actualGender = when (gender.lowercase()) {
            "male" -> "male"
            "female" -> "female"
            else -> if ((0..1).random() == 0) "male" else "female"
        }

        val firstName = if (actualGender == "male") {
            maleFirstNames.random()
        } else {
            femaleFirstNames.random()
        }

        return KoreanNameResponse(
            name = "${surnames.random()}$firstName",
            gender = actualGender
        )
    }
}