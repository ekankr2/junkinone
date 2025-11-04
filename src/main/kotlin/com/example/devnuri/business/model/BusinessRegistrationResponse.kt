package com.example.devnuri.business.model


/**
 * 사업자등록 상태조회 응답
 */
data class StatusResponse(
    val statusCode: String,
    val matchCount: Int,
    val requestCount: Int,
    val data: List<BusinessStatusInfo>
)

/**
 * 사업자 상태 정보
 */
data class BusinessStatusInfo(
    val businessNumber: String,
    val businessStatus: String,                    // 계속사업자, 휴업자, 폐업자
    val businessStatusCode: String,                // 01, 02, 03
    val taxType: String,                           // 과세유형 명칭
    val taxTypeCode: String,                       // 과세유형 코드
    val endDate: String? = null,                   // 폐업일 (YYYYMMDD)
    val unitTaxConversionClosureYn: String? = null, // 단위과세전환폐업여부 (Y/N)
    val taxTypeChangeDate: String? = null,         // 과세유형전환일자 (YYYYMMDD)
    val invoiceApplyDate: String? = null,          // 세금계산서적용일자 (YYYYMMDD)
    val previousTaxType: String? = null,           // 직전과세유형 명칭
    val previousTaxTypeCode: String? = null        // 직전과세유형 코드
)