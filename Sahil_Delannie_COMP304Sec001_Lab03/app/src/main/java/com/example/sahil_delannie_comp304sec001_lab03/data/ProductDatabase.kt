package com.example.sahil_delannie_comp304sec001_lab03.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sahil_delannie_comp304sec001_lab03.Product_entity
@Database(entities = [Product_entity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        // Provides the database instance, ensuring thread-safety
        fun getDatabase(context: Context): ProductDatabase {
            // Check if the instance is null, if so, create a new one
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).fallbackToDestructiveMigration()  // Handle migrations if needed
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
