package com.example.devnuri.business.controller

import com.example.devnuri.business.model.*
import com.example.devnuri.business.service.BusinessRegistrationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/business")
@Tag(name = "사업자등록정보", description = "국세청 사업자등록정보 상태조회 서비스")
class BusinessRegistrationController(
    private val businessRegistrationService: BusinessRegistrationService
) {

    @PostMapping("/status")
    @Operation(
        summary = "사업자등록 상태조회",
        description = """
            사업자 상태조회 정보를 제공합니다.

            **주요 기능:**
            - 1회 호출 시 최대 100개의 사업자 상태 조회 가능
            - 납세자 상태, 과세유형, 폐업일 등의 정보 제공

            **필수 항목:**
            - businessNumbers: 사업자등록번호 배열 (각 10자리 숫자, - 제거)

            **반환 정보:**
            - 납세자 상태 (계속사업자, 휴업자, 폐업자)
            - 과세유형 (일반과세자, 간이과세자, 면세사업자 등)
            - 폐업일, 과세유형전환일자 등
        """
    )
    fun getBusinessStatus(
        @RequestBody request: StatusRequest
    ): StatusResponse {
        return businessRegistrationService.getBusinessStatus(request)
    }
}