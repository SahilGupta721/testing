package com.example.sahil_delannie_comp304sec001_lab03.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sahil_delannie_comp304sec001_lab03.Product_entity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict =OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product_entity)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): Flow<List<Product_entity>>
}


