package com.example.sahil_delannie_comp304sec001_lab03.data

import com.example.sahil_delannie_comp304sec001_lab03.Product_entity
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override suspend fun insert(product: Product_entity) {
        productDao.insert(product)
    }

    // Marking the method as suspend because it's calling a suspend function
    override suspend fun getAllProducts(): Flow<List<Product_entity>> {
        return productDao.getAllProducts()
    }
}
