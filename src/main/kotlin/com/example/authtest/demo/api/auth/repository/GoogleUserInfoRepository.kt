package com.example.authtest.demo.api.auth.repository

import com.example.authtest.demo.entities.GoogleUserInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GoogleUserInfoRepository: JpaRepository<GoogleUserInfo, UUID> {
    fun findByGoogleId(googleId: String): GoogleUserInfo?
}