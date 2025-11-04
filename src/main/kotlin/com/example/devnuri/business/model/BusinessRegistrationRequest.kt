package com.example.devnuri.business.model

/**
 * 사업자등록정보 진위확인 요청
 */
data class ValidationRequest(
    val businesses: List<BusinessInfo>
)

/**
 * 사업자 정보
 */
data class BusinessInfo(
    val businessNumber: String,              // 사업자등록번호 (필수, 10자리)
    val startDate: String,                   // 개업일자 (필수, YYYYMMDD)
    val representativeName: String,          // 대표자성명 (필수)
    val representativeName2: String? = null, // 대표자성명2 (외국인의 경우 한글명)
    val businessName: String? = null,        // 상호
    val corporationNumber: String? = null,   // 법인등록번호 (13자리)
    val businessSector: String? = null,      // 주업태명
    val businessType: String? = null,        // 주종목명
    val businessAddress: String? = null      // 사업장주소
)

/**
 * 사업자등록 상태조회 요청
 */
data class StatusRequest(
    val businessNumbers: List<String>        // 사업자등록번호 배열
)