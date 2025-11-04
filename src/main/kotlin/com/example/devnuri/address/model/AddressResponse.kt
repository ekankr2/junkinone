package com.example.devnuri.address.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 주소 검색 응답
 */
@Schema(description = "주소 검색 응답")
data class AddressSearchResponse(
    @Schema(description = "결과 정보")
    val results: ResultsInfo,

    @Schema(description = "주소 목록")
    val juso: List<AddressInfo>
)

/**
 * 검색 결과 정보
 */
@Schema(description = "검색 결과 정보")
data class ResultsInfo(
    @Schema(description = "공통 에러코드 (정상: 0, 실패: 기타)")
    val errorCode: String,

    @Schema(description = "공통 에러메시지")
    val errorMessage: String,

    @Schema(description = "총 검색 데이터수")
    val totalCount: String,

    @Schema(description = "페이지 번호")
    val currentPage: String,

    @Schema(description = "페이지당 출력할 결과 Row 수")
    val countPerPage: String
)

/**
 * 주소 상세 정보
 */
@Schema(description = "주소 상세 정보")
data class AddressInfo(
    @Schema(description = "도로명 주소 (전체)")
    val roadAddr: String,

    @Schema(description = "도로명 주소 (영문)")
    val roadAddrEnglish: String? = null,

    @Schema(description = "지번 주소 (전체)")
    val jibunAddr: String,

    @Schema(description = "우편번호")
    val zipNo: String,

    @Schema(description = "행정구역코드 (시군구코드)")
    val admCd: String,

    @Schema(description = "도로명코드")
    val rnMgtSn: String,

    @Schema(description = "건물관리번호")
    val bdMgtSn: String,

    @Schema(description = "상세건물명")
    val detBdNmList: String? = null,

    @Schema(description = "건물명")
    val bdNm: String? = null,

    @Schema(description = "공동주택여부 (1: 공동주택, 0: 비공동주택)")
    val bdKdcd: String,

    @Schema(description = "시도명")
    val siNm: String,

    @Schema(description = "시군구명")
    val sggNm: String,

    @Schema(description = "읍면동명")
    val emdNm: String,

    @Schema(description = "법정동명")
    val liNm: String? = null,

    @Schema(description = "도로명")
    val rn: String,

    @Schema(description = "지하여부 (0: 지상, 1: 지하)")
    val udrtYn: String,

    @Schema(description = "건물본번")
    val buldMnnm: String,

    @Schema(description = "건물부번")
    val buldSlno: String,

    @Schema(description = "산여부 (0: 일반, 1: 산)")
    val mtYn: String,

    @Schema(description = "지번본번(번지)")
    val lnbrMnnm: String,

    @Schema(description = "지번부번(호)")
    val lnbrSlno: String,

    @Schema(description = "읍면동 일련번호")
    val emdNo: String,

    @Schema(description = "변동 이력 여부 (0: 현행, 1: 변동)")
    val hstryYn: String? = null,

    @Schema(description = "관련 지번")
    val relJibun: String? = null,

    @Schema(description = "행정동 명칭")
    val hemdNm: String? = null
)