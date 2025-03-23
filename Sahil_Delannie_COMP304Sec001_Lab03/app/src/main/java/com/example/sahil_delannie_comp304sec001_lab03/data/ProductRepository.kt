package com.example.sahil_delannie_comp304sec001_lab03.data

import com.example.sahil_delannie_comp304sec001_lab03.Product_entity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insert(product: Product_entity)
    suspend fun getAllProducts(): Flow<List<Product_entity>>
}