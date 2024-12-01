package com.example.authtest.demo.api.auth

import com.example.authtest.demo.api.auth.dtos.GoogleOAuth
import com.example.authtest.demo.api.auth.repository.GoogleUserInfoRepository
import com.example.authtest.demo.api.user.repository.UserRepository
import com.example.authtest.demo.entities.GoogleUserInfo
import com.example.authtest.demo.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val googleUserInfoRepository: GoogleUserInfoRepository
) {
    @Transactional
    fun googleLogin(googleOAuth: GoogleOAuth): User {
        val googleId = googleOAuth.sub
        val googleUserInfo = googleUserInfoRepository.findByGoogleId(googleId)
        return if (googleUserInfo == null) {
            // 유저 정보가 없을 경우 새로 생성
            val newUser = User(email = googleOAuth.email)
            val savedUser = userRepository.save(newUser) // 저장 후 반환된 엔티티에서 ID 확인

            // Google 사용자 정보 저장
            val googleInfo = GoogleUserInfo(
                uid = savedUser.id!!,
                googleId = googleOAuth.sub,
                email = googleOAuth.email,
                name = googleOAuth.name,
                picture = googleOAuth.picture,
                locale = googleOAuth.locale,
            )
            googleUserInfoRepository.save(googleInfo)

            newUser
        } else {
            userRepository.findByIdOrNull(googleUserInfo.uid)
                ?: throw IllegalStateException("User not found for UID: ${googleUserInfo.uid}")
        }
    }
}