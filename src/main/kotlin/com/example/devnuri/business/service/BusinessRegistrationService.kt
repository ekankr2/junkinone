package com.example.devnuri.business.service

import com.example.devnuri.business.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class BusinessRegistrationService {

    @Value("\${nts.api.service-key:}")
    private lateinit var serviceKey: String

    private val restTemplate = RestTemplate()
    private val baseUrl = "https://api.odcloud.kr/api/nts-businessman/v1"

    // ==================== NTS API DTOs (Internal) ====================

    private data class NtsBusinessInfo(
        val b_no: String,
        val start_dt: String,
        val p_nm: String,
        val p_nm2: String? = null,
        val b_nm: String? = null,
        val corp_no: String? = null,
        val b_sector: String? = null,
        val b_type: String? = null,
        val b_adr: String? = null
    )

    private data class NtsStatusRequest(
        val b_no: List<String>
    )

    @Suppress("PropertyName")
    private data class NtsValidationResult(
        val b_no: String,
        val valid: String,
        val valid_msg: String? = null,
        val request_param: NtsBusinessInfo,
        val status: NtsBusinessStatusInfo? = null
    )

    @Suppress("PropertyName")
    private data class NtsStatusResponse(
        val status_code: String,
        val match_cnt: Int,
        val request_cnt: Int,
        val data: List<NtsBusinessStatusInfo>
    )

    @Suppress("PropertyName")
    private data class NtsBusinessStatusInfo(
        val b_no: String,
        val b_stt: String,
        val b_stt_cd: String,
        val tax_type: String,
        val tax_type_cd: String,
        val end_dt: String? = null,
        val utcc_yn: String? = null,
        val tax_type_change_dt: String? = null,
        val invoice_apply_dt: String? = null,
        val rbf_tax_type: String? = null,
        val rbf_tax_type_cd: String? = null
    )

    // ==================== API Methods ====================

    /**
     * 사업자등록 상태조회 API 호출
     * 최대 100개의 사업자 상태 조회 가능
     */
    fun getBusinessStatus(request: StatusRequest): StatusResponse {
        val url = buildUrl("/status")

        val ntsRequest = NtsStatusRequest(b_no = request.businessNumbers)
        val ntsResponse = callNtsApi(url, ntsRequest, NtsStatusResponse::class.java)
        return ntsResponse.toUserFormat()
    }

    // ==================== Mapper Functions ====================

    private fun NtsStatusResponse.toUserFormat(): StatusResponse {
        return StatusResponse(
            statusCode = this.status_code,
            matchCount = this.match_cnt,
            requestCount = this.request_cnt,
            data = this.data.map { it.toUserFormat() }
        )
    }

    private fun NtsBusinessStatusInfo.toUserFormat(): BusinessStatusInfo {
        return BusinessStatusInfo(
            businessNumber = this.b_no,
            businessStatus = this.b_stt,
            businessStatusCode = this.b_stt_cd,
            taxType = this.tax_type,
            taxTypeCode = this.tax_type_cd,
            endDate = this.end_dt,
            unitTaxConversionClosureYn = this.utcc_yn,
            taxTypeChangeDate = this.tax_type_change_dt,
            invoiceApplyDate = this.invoice_apply_dt,
            previousTaxType = this.rbf_tax_type,
            previousTaxTypeCode = this.rbf_tax_type_cd
        )
    }

    // ==================== Private Helper Methods ====================

    private fun buildUrl(path: String): String {
        return UriComponentsBuilder
            .fromHttpUrl(baseUrl + path)
            .queryParam("serviceKey", serviceKey)
            .queryParam("returnType", "JSON")
            .toUriString()
    }

    private fun <T, R> callNtsApi(url: String, requestBody: T, responseType: Class<R>): R {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            accept = listOf(MediaType.APPLICATION_JSON)
        }

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            responseType
        )

        return response.body ?: throw RuntimeException("Empty response from NTS API")
    }
}