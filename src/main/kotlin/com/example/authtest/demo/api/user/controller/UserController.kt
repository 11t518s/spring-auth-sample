package com.example.authtest.demo.api.user.controller

import com.example.authtest.demo.api.user.service.UserService
import com.example.authtest.demo.entities.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getUserInfo(@AuthenticationPrincipal uid: UUID): ResponseEntity<User> {
        val user = userService.getUser(uid)
        return ResponseEntity.ok(user)
    }

}