package com.example.sahil_delannie_comp304sec001_lab03.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sahil_delannie_comp304sec001_lab03.CreateProduct
import com.example.sahil_delannie_comp304sec001_lab03.Home
import com.example.sahil_delannie_comp304sec001_lab03.ProductList
import com.example.sahil_delannie_comp304sec001_lab03.ViewModel.ProductViewModel
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepositoryImpl
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductDatabase

@Composable
fun NavigationGraph(navController: NavHostController, productViewModel: ProductViewModel) {
    // Initialize the database and repository for managing product data
    val productDao = ProductDatabase.getDatabase(navController.context).productDao()
    val productRepository = ProductRepositoryImpl(productDao)

    // Set up the navigation graph
    NavHost(navController = navController, startDestination = "home") {  // Changed to "home" for the initial screen

        // Composable for the Home screen
        composable("home") {
            Home(navController = navController)
        }

        // Composable for creating a product
        composable("addProduct") {
            CreateProduct(
                navController = navController,
                productViewModel = productViewModel,
                onProductAdded = {
                    // Optional callback function when a product is added
                    navController.navigate("products_list") // Navigate to the product list after adding a product
                }
            )
        }

        // Composable for displaying the product list
        composable("products_list") {
            ProductList(
                navController = navController,
                repository = productRepository // Pass repository to the ProductList screen
            )
        }
    }
}
