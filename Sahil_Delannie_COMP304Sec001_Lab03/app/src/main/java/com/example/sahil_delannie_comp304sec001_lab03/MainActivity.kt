package com.example.sahil_delannie_comp304sec001_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.navigation.compose.*
import com.example.sahil_delannie_comp304sec001_lab03.ui.theme.Sahil_Delannie_COMP304Sec001_Lab03Theme
import com.example.sahil_delannie_comp304sec001_lab03.ViewModel.ProductViewModel
import androidx.activity.viewModels
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepositoryImpl
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDao
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDatabase

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels() // ViewModel for product data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the DAO and Repository inside onCreate where context is available
        val productDao: ProductDao = ProductDatabase.getDatabase(applicationContext).productDao()
        val productRepository = ProductRepositoryImpl(productDao)

        setContent {
            Sahil_Delannie_COMP304Sec001_Lab03Theme {
                // Initialize the NavController for navigation
                val navController = rememberNavController()

                // Set up the NavHost to navigate between screens
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        // Home screen with "Get Started" button
                        Home(navController = navController)
                    }
                    composable("addProduct") {
                        // Pass the onProductAdded parameter with a lambda function
                        CreateProduct(
                            navController = navController,
                            productViewModel = productViewModel,
                            onProductAdded = { product ->
                                // Handle the product added, e.g., navigate to the product list screen
                                navController.navigate("productList")
                            }
                        )
                    }
                    composable("productList") {
                        // Navigate to the ProductList screen and pass repository
                        ProductList(navController = navController, repository = productRepository)
                    }
                }
            }
        }
    }
}
