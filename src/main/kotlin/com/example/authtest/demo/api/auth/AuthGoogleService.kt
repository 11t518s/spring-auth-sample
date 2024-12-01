package com.example.authtest.demo.api.auth

import com.example.authtest.demo.api.auth.dtos.GoogleOAuth
import com.example.authtest.demo.entities.GoogleUserInfo
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class AuthGoogleService(
    private val restTemplate: RestTemplate
) {
    @Transactional
    fun getUserInfo(accessToken: String): GoogleOAuth {
        val response = restTemplate.exchange(
            "https://www.googleapis.com/oauth2/v3/userinfo",
            HttpMethod.GET,
            HttpEntity<Void>(createHeaders(accessToken)),
            GoogleOAuth::class.java
        )

        return response.body ?: throw RuntimeException("Failed to get user info from Google")
    }

    private fun createHeaders(token: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $token")
        return headers
    }
}