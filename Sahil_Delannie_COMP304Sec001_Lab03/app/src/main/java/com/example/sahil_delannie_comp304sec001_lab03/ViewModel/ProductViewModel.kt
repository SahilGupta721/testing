package com.example.sahil_delannie_comp304sec001_lab03.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sahil_delannie_comp304sec001_lab03.Product_entity
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDao
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao: ProductDao = ProductDatabase.getDatabase(application).productDao()

    fun insertProduct(product: Product_entity) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insert(product) // Insert the product into the database
        }
    }
}

