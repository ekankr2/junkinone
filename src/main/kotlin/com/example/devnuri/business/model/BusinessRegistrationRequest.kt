package com.example.devnuri.business.model

/**
 * 사업자등록 상태조회 요청
 */
data class StatusRequest(
    val businessNumbers: List<String>        // 사업자등록번호 배열
)