package com.example.authtest.demo.api.auth

import com.example.authtest.demo.api.auth.dtos.GoogleOAuth
import com.example.authtest.demo.api.auth.repository.GoogleUserInfoRepository
import com.example.authtest.demo.api.user.repository.UserRepository
import com.example.authtest.demo.entities.GoogleUserInfo
import com.example.authtest.demo.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val googleUserInfoRepository: GoogleUserInfoRepository
) {
    fun googleLogin(googleOAuth: GoogleOAuth): User {
        val googleId = googleOAuth.sub
        val googleUserInfo = googleUserInfoRepository.findByGoogleId(googleId)
        return if (googleUserInfo == null) {
            // 유저 정보가 없을경우 새로 만들어준다.
            val newUser = userRepository.save(User(email = googleOAuth.email))
            googleUserInfoRepository.save(GoogleUserInfo(
                uid = newUser.id,
                googleId = googleOAuth.sub,
                email = googleOAuth.email,
                name = googleOAuth.name,
                picture = googleOAuth.picture,
                locale = googleOAuth.locale,
            ))
            newUser
        } else {
            userRepository.findByIdOrNull(googleUserInfo.uid)
                ?: throw IllegalStateException("User not found for UID: ${googleUserInfo.uid}")
        }
    }
}