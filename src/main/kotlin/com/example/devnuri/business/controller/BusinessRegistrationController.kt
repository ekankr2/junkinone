package com.example.devnuri.business.controller

import com.example.devnuri.business.model.*
import com.example.devnuri.business.service.BusinessRegistrationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/business")
@Tag(name = "사업자등록정보", description = "국세청 사업자등록정보 진위확인 및 상태조회 서비스")
class BusinessRegistrationController(
    private val businessRegistrationService: BusinessRegistrationService
) {

    @PostMapping("/validate")
    @Operation(
        summary = "사업자등록정보 진위확인",
        description = """
            입력한 사업자 정보에 대한 일치여부를 확인합니다.

            **주요 기능:**
            - 1회 호출 시 최대 100개의 사업자 정보 검증 가능
            - 일치할 경우: valid=01 및 해당 사업자 정보 반환
            - 불일치할 경우: valid=02, valid_msg="확인할 수 없습니다" 반환

            **필수 항목:**
            - businessNumber: 사업자등록번호 (10자리 숫자, - 제거)
            - startDate: 개업일자 (YYYYMMDD 형식)
            - representativeName: 대표자성명

            **선택 항목:**
            - representativeName2: 대표자성명2 (외국인 사업자의 경우 한글명)
            - businessName: 상호
            - corporationNumber: 법인등록번호 (13자리 숫자)
            - businessSector: 주업태명
            - businessType: 주종목명
            - businessAddress: 사업장주소
        """
    )
    fun validateBusinessRegistration(
        @RequestBody request: ValidationRequest
    ): ValidationResponse {
        return businessRegistrationService.validateBusinessRegistration(request)
    }

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