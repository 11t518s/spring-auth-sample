package com.example.authtest.demo.entities

import BaseTimestampEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(
    name = "user",
)
class User(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // MySQL에서 BINARY(16)로 저장
    val id: UUID = UUID.randomUUID(),

    val email: String? = null,
) : BaseTimestampEntity()
