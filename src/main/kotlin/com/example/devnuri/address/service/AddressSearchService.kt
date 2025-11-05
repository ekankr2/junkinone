package com.example.devnuri.address.service

import com.example.devnuri.address.model.*
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AddressSearchService {

    @Value("\${juso.api.confm-key:}")
    private lateinit var confmKey: String

    private val restTemplate = RestTemplate()
    private val baseUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do"

    // ==================== Juso API DTOs (Internal) ====================

    private data class JusoApiResponse(
        val results: JusoResultsInfo
    )

    private data class JusoResultsInfo(
        val common: JusoCommonInfo,
        val juso: List<JusoAddressInfo>?
    )

    private data class JusoCommonInfo(
        val errorCode: String,
        val errorMessage: String,
        val totalCount: String,
        val currentPage: String,
        val countPerPage: String
    )

    private data class JusoAddressInfo(
        val roadAddr: String,
        val engAddr: String? = null,
        val jibunAddr: String,
        val zipNo: String,
        val admCd: String,
        val rnMgtSn: String,
        val bdMgtSn: String,
        val detBdNmList: String? = null,
        val bdNm: String? = null,
        val bdKdcd: String,
        val siNm: String,
        val sggNm: String,
        val emdNm: String,
        val liNm: String? = null,
        val rn: String,
        val udrtYn: String,
        val buldMnnm: String,
        val buldSlno: String,
        val mtYn: String,
        val lnbrMnnm: String,
        val lnbrSlno: String,
        val emdNo: String,
        val hstryYn: String? = null,
        val relJibun: String? = null,
        val hemdNm: String? = null
    )

    // ==================== API Methods ====================

    /**
     * 주소 검색 API 호출
     */
    fun searchAddress(request: AddressSearchRequest): AddressSearchResponse {
        val url = buildUrl(request)
        val jusoResponse = callJusoApi(url)
        return jusoResponse.toUserFormat()
    }

    // ==================== Mapper Functions ====================

    private fun JusoApiResponse.toUserFormat(): AddressSearchResponse {
        return AddressSearchResponse(
            results = ResultsInfo(
                errorCode = this.results.common.errorCode,
                errorMessage = this.results.common.errorMessage,
                totalCount = this.results.common.totalCount,
                currentPage = this.results.common.currentPage,
                countPerPage = this.results.common.countPerPage
            ),
            juso = this.results.juso?.map { it.toUserFormat() } ?: emptyList()
        )
    }

    private fun JusoAddressInfo.toUserFormat(): AddressInfo {
        return AddressInfo(
            roadAddr = this.roadAddr,
            roadAddrEnglish = this.engAddr,
            jibunAddr = this.jibunAddr,
            zipNo = this.zipNo,
            admCd = this.admCd,
            rnMgtSn = this.rnMgtSn,
            bdMgtSn = this.bdMgtSn,
            detBdNmList = this.detBdNmList,
            bdNm = this.bdNm,
            bdKdcd = this.bdKdcd,
            siNm = this.siNm,
            sggNm = this.sggNm,
            emdNm = this.emdNm,
            liNm = this.liNm,
            rn = this.rn,
            udrtYn = this.udrtYn,
            buldMnnm = this.buldMnnm,
            buldSlno = this.buldSlno,
            mtYn = this.mtYn,
            lnbrMnnm = this.lnbrMnnm,
            lnbrSlno = this.lnbrSlno,
            emdNo = this.emdNo,
            hstryYn = this.hstryYn,
            relJibun = this.relJibun,
            hemdNm = this.hemdNm
        )
    }

    // ==================== Private Helper Methods ====================

    private fun buildUrl(request: AddressSearchRequest): String {
        return UriComponentsBuilder
            .fromUriString(baseUrl)
            .queryParam("confmKey", confmKey)
            .queryParam("currentPage", request.currentPage)
            .queryParam("countPerPage", request.countPerPage)
            .queryParam("keyword", request.keyword)
            .queryParam("resultType", "json")
            .queryParam("hstryYn", request.hstryYn)
            .queryParam("firstSort", request.firstSort)
            .queryParam("addInfoYn", request.addInfoYn)
            .build()
            .toUriString()
    }

    private fun callJusoApi(url: String): JusoApiResponse {
        val headers = HttpHeaders().apply {
            accept = listOf(MediaType.APPLICATION_JSON)
        }

        val entity = HttpEntity<Any>(headers)

        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            JusoApiResponse::class.java
        )

        return response.body ?: throw RuntimeException("Empty response from Juso API")
    }
}