package com.example.authtest.demo.utils

import com.example.authtest.demo.entities.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor("test-auth-key-test-auth-key-test-auth-key".toByteArray()) // 256비트 키
    private val expirationHours: Long = 12

    /**
     * JWT 토큰 생성
     */
    fun generateToken(user: User): String {
        val claims: Claims = Jwts.claims()
            .add("uid", user.id.toString())
            .build()

        val now = Instant.now()

        return Jwts.builder()
            .subject("user-token")
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationHours, ChronoUnit.HOURS)))
            .claims(claims)
            .signWith(secretKey)
            .compact()
    }


    fun getAuthUid(token: String): UUID {
        val claims: Claims = getClaims(token)
        val uidString = claims.get("uid", String::class.java)?: throw RuntimeException("no uid field")
        return UUID.fromString(uidString)
    }

    /**
     * JWT 토큰 검증
     */
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is io.jsonwebtoken.ExpiredJwtException -> {
                    println("Token expired: ${e.message}")
                    throw e // 토큰 만료 예외 다시 던짐
                }
                is io.jsonwebtoken.JwtException -> {
                    println("Invalid token: ${e.message}")
                    throw e // 일반적인 JWT 예외 다시 던짐
                }
                else -> {
                    println("Unexpected error: ${e.message}")
                    throw e // 기타 예외 다시 던짐
                }
            }
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
