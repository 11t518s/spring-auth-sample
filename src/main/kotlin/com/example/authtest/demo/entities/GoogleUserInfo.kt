package com.example.authtest.demo.entities

import BaseTimestampEntity
import jakarta.persistence.*
import java.util.*


@Entity
@Table(
    name = "google_user_info",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["google_id"]),
    ]
)
class GoogleUserInfo(
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val uid: UUID,

    @Column(nullable = false, unique = true)
    val googleId: String, // Google에서 제공하는 고유 ID

    @Column(unique = true)
    val email: String?, // 사용자 이메일
    val name: String?, // 사용자 이름 (옵션)
    val picture: String?, // 프로필 사진 URL
    val locale: String?, // 언어 및 지역 정보
) : BaseTimestampEntity()