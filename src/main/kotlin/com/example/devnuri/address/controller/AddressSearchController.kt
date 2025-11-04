package com.example.devnuri.address.controller

import com.example.devnuri.address.model.*
import com.example.devnuri.address.service.AddressSearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/address")
@Tag(name = "주소검색", description = "도로명주소 안내시스템 주소검색 API")
class AddressSearchController(
    private val addressSearchService: AddressSearchService
) {

    @GetMapping("/search")
    @Operation(
        summary = "주소 검색",
        description = """
            도로명주소 및 지번주소를 검색합니다.

            **주요 기능:**
            - 도로명, 지번, 건물명 등으로 주소 검색 가능
            - 변동된 주소정보 포함 여부 선택 가능
            - 다양한 정렬 방식 지원 (정확도순, 도로명 우선, 지번 우선)
            - 행정동 명칭으로 검색 가능

            **필수 항목:**
            - keyword: 주소 검색어

            **선택 항목:**
            - currentPage: 현재 페이지 번호 (기본값: 1, n > 0)
            - countPerPage: 페이지당 결과 수 (기본값: 10, 범위: 0 < n ≤ 100)
            - hstryYn: 변동된 주소정보 포함 여부 (Y: 포함, N: 미포함, 기본값: N)
            - firstSort: 정렬 방식
              * none: 정확도순 (기본값)
              * road: 도로명 포함 우선
              * location: 지번 포함 우선
            - addInfoYn: 추가 정보 항목 제공 여부 (Y: 제공, N: 미제공, 기본값: N)
              * 제공 항목: hstryYn(변동이력), relJibun(관련지번), hemdNm(행정동명칭)

            **반환 정보:**
            - 도로명주소, 지번주소, 우편번호
            - 시도명, 시군구명, 읍면동명
            - 건물명, 상세건물명
            - 건물관리번호, 도로명코드 등
        """
    )
    fun searchAddress(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "1") currentPage: Int,
        @RequestParam(defaultValue = "10") countPerPage: Int,
        @RequestParam(defaultValue = "N") hstryYn: String,
        @RequestParam(defaultValue = "none") firstSort: String,
        @RequestParam(defaultValue = "N") addInfoYn: String
    ): AddressSearchResponse {
        val request = AddressSearchRequest(
            keyword = keyword,
            currentPage = currentPage,
            countPerPage = countPerPage,
            hstryYn = hstryYn,
            firstSort = firstSort,
            addInfoYn = addInfoYn
        )
        return addressSearchService.searchAddress(request)
    }
}