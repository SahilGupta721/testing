package com.example.sahil_delannie_comp304sec001_lab03

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sahil_delannie_comp304sec001_lab03.data.ProductRepository
import kotlinx.coroutines.launch

@Composable
fun ProductList(
    navController: NavHostController,
    repository: ProductRepository // Pass repository to fetch data
) {
    var productList by remember { mutableStateOf<List<Product_entity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Fetch Products Button
        Button(
            onClick = {
                isLoading = true
                // Use coroutineScope to launch the flow collection
                coroutineScope.launch {
                    repository.getAllProducts().collect { products ->
                        productList = products
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Load Products")
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Show Products List
        if (productList.isEmpty() && !isLoading) {
            Text(
                text = "No products available.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(productList) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

// Extracted Product Card Component
@Composable
fun ProductCard(product: Product_entity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Price: \$${"%.2f".format(product.price)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Category: ${product.category}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
