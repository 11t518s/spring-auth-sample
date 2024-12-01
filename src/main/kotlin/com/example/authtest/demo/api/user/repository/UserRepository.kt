package com.example.authtest.demo.api.user.repository

import com.example.authtest.demo.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface UserRepository : JpaRepository<User, UUID> {
}