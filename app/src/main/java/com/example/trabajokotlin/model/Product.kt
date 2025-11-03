package com.example.trabajokotlin.model

import androidx.annotation.DrawableRes

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    @DrawableRes val imageRes: Int? = null
)
