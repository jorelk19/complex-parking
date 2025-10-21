package com.complexparking.entities

data class Car(
    val id: Int,
    val plate: String,
    val unit: Int,
    val brand: Brand,
    val color: String
)
