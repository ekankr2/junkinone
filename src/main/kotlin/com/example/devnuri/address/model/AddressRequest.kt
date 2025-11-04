package com.example.devnuri.address.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 주소 검색 요청
 */
@Schema(description = "주소 검색 요청")
data class AddressSearchRequest(
    @Schema(description = "주소 검색어", example = "세종로", required = true)
    val keyword: String,

    @Schema(description = "현재 페이지 번호 (n>0)", example = "1", defaultValue = "1")
    val currentPage: Int = 1,

    @Schema(description = "페이지당 출력할 결과 Row 수 (0<n<=100)", example = "10", defaultValue = "10")
    val countPerPage: Int = 10,

    @Schema(description = "변동된 주소정보 포함 여부", example = "N", defaultValue = "N", allowableValues = ["Y", "N"])
    val hstryYn: String = "N",

    @Schema(
        description = "정렬 방식 (none: 정확도순, road: 도로명 포함 우선, location: 지번 포함 우선)",
        example = "none",
        defaultValue = "none",
        allowableValues = ["none", "road", "location"]
    )
    val firstSort: String = "none",

    @Schema(description = "추가 정보 항목 제공 여부 (hstryYn, relJibun, hemdNm)", example = "N", defaultValue = "N", allowableValues = ["Y", "N"])
    val addInfoYn: String = "N"
)