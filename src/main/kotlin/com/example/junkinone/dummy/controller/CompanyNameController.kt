package com.example.junkinone.dummy.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

/**
 * Korean Company Name Generator API
 * Generates realistic Korean company names for dummy data
 */
@RestController
@RequestMapping("/company-names")
class CompanyNameController : DummyController() {

    data class CompanyNameResponse(
        val name: String,
        val english: String
    )

    data class BulkCompanyNamesResponse(
        val companies: List<CompanyNameResponse>,
        val count: Int
    )

    // 회사명 앞부분 (한글 - 영문 매칭)
    private val prefixMap = mapOf(
        "한국" to "Korea",
        "대한" to "Daehan",
        "글로벌" to "Global",
        "유니" to "Uni",
        "코리아" to "Korea",
        "서울" to "Seoul",
        "부산" to "Busan",
        "인천" to "Incheon",
        "스마트" to "Smart",
        "디지털" to "Digital",
        "테크" to "Tech",
        "넥스트" to "Next",
        "퓨처" to "Future",
        "모던" to "Modern",
        "프라임" to "Prime",
        "베스트" to "Best",
        "탑" to "Top",
        "굿" to "Good",
        "해피" to "Happy",
        "선" to "Sun",
        "빛" to "Light",
        "별" to "Star",
        "하늘" to "Sky",
        "바다" to "Ocean",
        "산" to "Mountain",
        "강" to "River",
        "나래" to "Wing",
        "새" to "Bird",
        "참" to "True",
        "온" to "On",
        "푸른" to "Blue",
        "큰" to "Big",
        "한빛" to "Hanbit",
        "신세계" to "Shinsegae",
        "하나" to "Hana",
        "우리" to "Woori",
        "국민" to "Kookmin",
        "삼성" to "Samsung",
        "엘지" to "LG",
        "현대" to "Hyundai",
        "롯데" to "Lotte",
        "에스케이" to "SK",
        "케이티" to "KT",
        "넥슨" to "Nexon",
        "카카오" to "Kakao",
        "네이버" to "Naver",
        "쿠팡" to "Coupang",
        "뱅크" to "Bank",
        "파이낸스" to "Finance",
        "알파" to "Alpha",
        "오메가" to "Omega",
        "제니스" to "Zenith",
        "빅토리" to "Victory",
        "에이스" to "Ace",
        "골드" to "Gold",
        "실버" to "Silver",
        "다이아" to "Diamond",
        "플래티넘" to "Platinum",
        "로얄" to "Royal",
        "임페리얼" to "Imperial",
        "유나이티드" to "United",
        "그랜드" to "Grand",
        "슈퍼" to "Super",
        "메가" to "Mega",
        "울트라" to "Ultra",
        "하이퍼" to "Hyper",
        "마스터" to "Master"
    )

    // 회사명 중간부분 (한글 - 영문 매칭)
    private val middleMap = mapOf(
        "소프트" to "Soft",
        "시스템" to "System",
        "솔루션" to "Solution",
        "네트워크" to "Network",
        "데이터" to "Data",
        "클라우드" to "Cloud",
        "인포" to "Info",
        "테크" to "Tech",
        "미디어" to "Media",
        "커뮤니케이션" to "Communication",
        "엔터테인먼트" to "Entertainment",
        "푸드" to "Food",
        "바이오" to "Bio",
        "헬스" to "Health",
        "케어" to "Care",
        "에듀" to "Edu",
        "파이낸스" to "Finance",
        "리테일" to "Retail",
        "로지스" to "Logis",
        "모빌리티" to "Mobility",
        "에너지" to "Energy",
        "마케팅" to "Marketing",
        "컨설팅" to "Consulting",
    )

    // 회사 형태 (짧은 버전)
    private val companyTypesShort = listOf(
        "(주)",
        "(유)",
        ""
    )

    // 영문 suffix
    private val englishSuffixes = listOf(
        "Inc.", "Co., Ltd.", "Corp.", "Systems", "Solutions",
        "Tech", "Soft", "Media", "Labs", "Group"
    )

    @GetMapping
    @Operation(
        summary = "회사명 생성",
        description = """
            한국 회사명을 생성합니다.

            **includeType 파라미터:**
            - `false` (기본값): 일반 회사명 (예: 한국소프트)
            - `true`: 회사 형태 포함 (예: (주)한국소프트, (유)글로벌테크)
        """
    )
    fun getCompanyName(@RequestParam(defaultValue = "false") includeType: Boolean): CompanyNameResponse {
        val company = generateCompanyName()

        return if (includeType) {
            val shortType = companyTypesShort.random()
            val name = if (shortType.isEmpty()) company.name else "$shortType${company.name}"
            CompanyNameResponse(
                name = name,
                english = company.english
            )
        } else {
            company
        }
    }

    @GetMapping("/bulk")
    @Operation(
        summary = "회사명 대량 생성",
        description = """
            여러 개의 회사명을 생성합니다 (최대 100개).

            **파라미터:**
            - `count`: 생성할 회사명 개수 (기본값: 10, 최대: 100)
            - `includeType`: 회사 형태 포함 여부 (기본값: false)
        """
    )
    fun getBulkCompanyNames(
        @RequestParam(defaultValue = "10") count: Int,
        @RequestParam(defaultValue = "false") includeType: Boolean
    ): BulkCompanyNamesResponse {
        val actualCount = minOf(count, 100)
        val companies = (1..actualCount).map {
            val company = generateCompanyName()

            if (includeType) {
                val shortType = companyTypesShort.random()
                val name = if (shortType.isEmpty()) company.name else "$shortType${company.name}"
                CompanyNameResponse(
                    name = name,
                    english = company.english
                )
            } else {
                company
            }
        }
        return BulkCompanyNamesResponse(
            companies = companies,
            count = actualCount
        )
    }

    private fun generateCompanyName(): CompanyNameResponse {
        // 한글 prefix와 middle 선택
        val koreanPrefix = prefixMap.keys.random()
        val koreanMiddle = middleMap.keys.random()
        val koreanName = "$koreanPrefix$koreanMiddle"

        // 영문명 생성 - 한글과 매칭되는 영어로
        val englishPrefix = prefixMap[koreanPrefix]!!
        val englishMiddle = middleMap[koreanMiddle]!!
        val english = "$englishPrefix $englishMiddle ${englishSuffixes.random()}"

        return CompanyNameResponse(
            name = koreanName,
            english = english
        )
    }
}