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

    private data class NtsValidationRequest(
        val businesses: List<NtsBusinessInfo>
    )

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
    private data class NtsValidationResponse(
        val status_code: String,
        val request_cnt: Int,
        val valid_cnt: Int,
        val data: List<NtsValidationResult>
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
     * 사업자등록정보 진위확인 API 호출
     * 최대 100개의 사업자 정보 검증 가능
     */
    fun validateBusinessRegistration(request: ValidationRequest): ValidationResponse {
        val url = buildUrl("/validate")

        // Convert user-friendly request to NTS API format
        val ntsRequest = NtsValidationRequest(
            businesses = request.businesses.map { it.toNtsFormat() }
        )

        // Call NTS API and get raw response
        val ntsResponse = callNtsApi(url, ntsRequest, NtsValidationResponse::class.java)

        // Convert NTS response to user-friendly format
        return ntsResponse.toUserFormat(request.businesses)
    }

    /**
     * 사업자등록 상태조회 API 호출
     * 최대 100개의 사업자 상태 조회 가능
     */
    fun getBusinessStatus(request: StatusRequest): StatusResponse {
        val url = buildUrl("/status")

        // Convert user-friendly request to NTS API format
        val ntsRequest = NtsStatusRequest(b_no = request.businessNumbers)

        // Call NTS API and get raw response
        val ntsResponse = callNtsApi(url, ntsRequest, NtsStatusResponse::class.java)

        // Convert NTS response to user-friendly format
        return ntsResponse.toUserFormat()
    }

    // ==================== Mapper Functions ====================

    // Request mappers: User format -> NTS format
    private fun BusinessInfo.toNtsFormat() = NtsBusinessInfo(
        b_no = this.businessNumber,
        start_dt = this.startDate,
        p_nm = this.representativeName,
        p_nm2 = this.representativeName2,
        b_nm = this.businessName,
        corp_no = this.corporationNumber,
        b_sector = this.businessSector,
        b_type = this.businessType,
        b_adr = this.businessAddress
    )

    // Response mappers: NTS format -> User format
    private fun NtsValidationResponse.toUserFormat(originalBusinesses: List<BusinessInfo>): ValidationResponse {
        // Create a map of business number to original request for quick lookup
        val businessMap = originalBusinesses.associateBy { it.businessNumber }

        return ValidationResponse(
            statusCode = this.status_code,
            requestCount = this.request_cnt,
            validCount = this.valid_cnt,
            data = this.data.map { it.toUserFormat(businessMap) }
        )
    }

    private fun NtsValidationResult.toUserFormat(businessMap: Map<String, BusinessInfo>): ValidationResult {
        // Get the original business info from the map, or convert from request_param as fallback
        val originalBusinessInfo = businessMap[this.b_no] ?: this.request_param.toUserFormat()

        return ValidationResult(
            businessNumber = this.b_no,
            valid = this.valid,
            validMessage = this.valid_msg,
            requestParam = originalBusinessInfo,
            status = this.status?.toUserFormat()
        )
    }

    private fun NtsStatusResponse.toUserFormat(): StatusResponse {
        return StatusResponse(
            statusCode = this.status_code,
            matchCount = this.match_cnt,
            requestCount = this.request_cnt,
            data = this.data.map { it.toUserFormat() }
        )
    }

    private fun NtsBusinessInfo.toUserFormat(): BusinessInfo {
        return BusinessInfo(
            businessNumber = this.b_no,
            startDate = this.start_dt,
            representativeName = this.p_nm,
            representativeName2 = this.p_nm2,
            businessName = this.b_nm,
            corporationNumber = this.corp_no,
            businessSector = this.b_sector,
            businessType = this.b_type,
            businessAddress = this.b_adr
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