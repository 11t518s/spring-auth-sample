package com.example.authtest.demo.api.auth.dtos

data class GoogleOAuth(
    val sub: String, // Google의 고유 사용자 ID
    val name: String?, // 전체 이름
    val givenName: String?, // 이름 (예: John)
    val familyName: String?, // 성 (예: Doe)
    val picture: String?, // 프로필 사진 URL
    val email: String?, // 이메일 주소
    val emailVerified: Boolean?, // 이메일 인증 여부
    val locale: String? // 사용자 지역 설정
)