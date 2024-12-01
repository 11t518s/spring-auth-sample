package com.example.authtest.demo.api.user.service

import com.example.authtest.demo.api.user.repository.UserRepository
import com.example.authtest.demo.entities.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUser(id: UUID): User {
        return userRepository.findById(id).orElseThrow{
            throw RuntimeException("User not found for uid: $id")
        }
    }
}