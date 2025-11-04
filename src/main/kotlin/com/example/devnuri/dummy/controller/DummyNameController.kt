package com.example.devnuri.dummy.controller

import com.example.devnuri.dummy.generator.CompanyNameGenerator
import com.example.devnuri.dummy.generator.KoreanNameGenerator
import com.example.devnuri.dummy.generator.NicknameGenerator
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
class DummyNameController : DummyController() {

    // ============ Company Name APIs ============

    data class BulkCompanyNamesResponse(
        val companies: List<CompanyNameGenerator.CompanyNameResponse>,
        val count: Int
    )

    @GetMapping("/company-names")
    @Operation(
        summary = "회사명 생성",
        description = """
            한국 회사명을 생성합니다.

            **includeType 파라미터:**
            - `false` (기본값): 일반 회사명 (예: 한국소프트)
            - `true`: 회사 형태 포함 (예: (주)한국소프트, (유)글로벌테크)
        """
    )
    fun getCompanyName(
        @RequestParam(defaultValue = "false") includeType: Boolean
    ): CompanyNameGenerator.CompanyNameResponse {
        return CompanyNameGenerator.generate(includeType)
    }

    @GetMapping("/company-names/bulk")
    @Operation(
        summary = "회사명 대량 생성",
        description = """
            여러 개의 회사명을 생성합니다 (최대 100개).

            **파라미터:**
            - `count`: 생성할 회사명 개수 (기본값: 10, 최대: 100)
            - `includeType`: 회사 형태 포함 여부 (기본값: false)
        """
    )
    fun getBulkCompanyNames(
        @RequestParam(defaultValue = "10") count: Int,
        @RequestParam(defaultValue = "false") includeType: Boolean
    ): BulkCompanyNamesResponse {
        val companies = CompanyNameGenerator.generateBulk(count, includeType)
        return BulkCompanyNamesResponse(
            companies = companies,
            count = companies.size
        )
    }

    // ============ Korean Name APIs ============

    data class BulkNamesResponse(
        val names: List<KoreanNameGenerator.KoreanNameResponse>,
        val count: Int
    )

    @GetMapping("/korean-names")
    @Operation(
        summary = "한국 이름 생성",
        description = """
            한국 이름을 생성합니다.

            **gender 파라미터:**
            - `random` (기본값): 랜덤 성별
            - `male`: 남성 이름
            - `female`: 여성 이름
        """
    )
    fun getName(
        @RequestParam(defaultValue = "random") gender: String
    ): KoreanNameGenerator.KoreanNameResponse {
        return KoreanNameGenerator.generate(gender)
    }

    @GetMapping("/korean-names/bulk")
    @Operation(
        summary = "이름 대량 생성",
        description = """
            여러 개의 한국 이름을 생성합니다 (최대 100개).

            **파라미터:**
            - `count`: 생성할 이름 개수 (기본값: 10, 최대: 100)
            - `gender`: 성별 (기본값: random, 옵션: male, female, random)
        """
    )
    fun getBulkNames(
        @RequestParam(defaultValue = "10") count: Int,
        @RequestParam(defaultValue = "random") gender: String
    ): BulkNamesResponse {
        val names = KoreanNameGenerator.generateBulk(count, gender)
        return BulkNamesResponse(
            names = names,
            count = names.size
        )
    }

    // ============ Nickname APIs ============

    data class BulkNicknamesResponse(
        val nicknames: List<NicknameGenerator.NicknameResponse>,
        val count: Int
    )

    @GetMapping("/nicknames")
    @Operation(
        summary = "닉네임 생성",
        description = "형용사 + 명사 형식의 한국어 닉네임을 생성합니다."
    )
    fun getNickname(): NicknameGenerator.NicknameResponse {
        return NicknameGenerator.generate()
    }

    @GetMapping("/nicknames/bulk")
    @Operation(
        summary = "닉네임 대량 생성",
        description = """
            여러 개의 닉네임을 생성합니다 (최대 100개).

            **파라미터:**
            - `count`: 생성할 닉네임 개수 (기본값: 10, 최대: 100)
        """
    )
    fun getBulkNicknames(
        @RequestParam(defaultValue = "10") count: Int
    ): BulkNicknamesResponse {
        val nicknames = NicknameGenerator.generateBulk(count)
        return BulkNicknamesResponse(
            nicknames = nicknames,
            count = nicknames.size
        )
    }
}