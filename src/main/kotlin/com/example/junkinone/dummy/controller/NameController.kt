package com.example.junkinone.dummy.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * Korean Name Generator API
 * Generates random Korean names for dummy data
 */
@RestController
@RequestMapping("/dummy/korean-names")
@Tag(name = "Dummy", description = "Generate random dummy data for testing and development")
class NameController {

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
    @Operation(summary = "Get a Korean name", description = "Returns a Korean name with gender")
    fun getName(): KoreanNameResponse {
        return KoreanNameResponse(
            name = "${surnames.random()}${maleFirstNames.random()}",
            gender = "male"
        )
    }

    @GetMapping("/male")
    @Operation(summary = "Get a male Korean name", description = "Returns a male Korean name")
    fun getMaleName(): KoreanNameResponse {
        return KoreanNameResponse(
            name = "${surnames.random()}${maleFirstNames.random()}",
            gender = "male"
        )
    }

    @GetMapping("/female")
    @Operation(summary = "Get a female Korean name", description = "Returns a female Korean name")
    fun getFemaleName(): KoreanNameResponse {
        return KoreanNameResponse(
            name = "${surnames.random()}${femaleFirstNames.random()}",
            gender = "female"
        )
    }

    @GetMapping("/bulk")
    @Operation(summary = "Get multiple random Korean names", description = "Returns multiple random Korean names")
    fun getBulkNames(@RequestParam(defaultValue = "10") count: Int): BulkNamesResponse {
        val actualCount = minOf(count, 100)
        val names = (1..actualCount).map {
            val isMale = (0..1).random() == 0
            KoreanNameResponse(
                name = "${surnames.random()}${if (isMale) maleFirstNames.random() else femaleFirstNames.random()}",
                gender = if (isMale) "male" else "female"
            )
        }
        return BulkNamesResponse(
            names = names,
            count = actualCount
        )
    }
}