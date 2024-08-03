package com.example.domain.entity

data class NoteDomain(
    val id: Int,
    val title: String,
    val content: String,
    val hexColor: String,
    val createdDate: String,
    val imageLink: String
)
