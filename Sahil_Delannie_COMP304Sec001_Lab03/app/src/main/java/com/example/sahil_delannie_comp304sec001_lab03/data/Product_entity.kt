package com.example.sahil_delannie_comp304sec001_lab03

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")//This si table
data class Product_entity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val price: Double,
    val deliveryDate: String,
    val category: String,
    val isFavorite: Boolean
)
