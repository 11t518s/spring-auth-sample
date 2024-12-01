package com.example.authtest.demo.filters

import com.example.authtest.demo.utils.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")

        if (!authHeader.isNullOrEmpty() && authHeader.startsWith("Bearer ")) {
            val token = authHeader.replace("Bearer ", "")

            if (jwtUtil.validateToken(token)) {
                val uid = jwtUtil.getAuthUid(token)
                val authentication = CustomAuthentication(uid)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}

class CustomAuthentication(private val uid: UUID) : org.springframework.security.authentication.AbstractAuthenticationToken(null) {
    override fun getPrincipal(): UUID = uid
    override fun getCredentials(): Any? = null
    init {
        isAuthenticated = true
    }
}
