package com.example.authtest.demo.api.auth

import com.example.authtest.demo.api.auth.dtos.TokenRequest
import com.example.authtest.demo.api.user.service.UserService
import com.example.authtest.demo.utils.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authGoogleService: AuthGoogleService,
    private val jwtUtil: JwtUtil,
    private val authService: AuthService
) {

    @PostMapping("/google")
    fun googleLogin(@RequestBody tokenRequest: TokenRequest): ResponseEntity<Any> {
        val googleToken = tokenRequest.token

        val googleOAuth = authGoogleService.getUserInfo(googleToken)
        val user = authService.googleLogin(googleOAuth)

        val jwt = jwtUtil.generateToken(user)

        return ResponseEntity.ok(mapOf("token" to jwt))
    }
}

