package com.example.sahil_delannie_comp304sec001_lab03

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sahil_delannie_comp304sec001_lab03.ViewModel.ProductViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(
    productViewModel: ProductViewModel,
    navController: NavHostController,
    onProductAdded: (Product_entity) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)) } // Set to current date
    var category by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    // New id field
    var id by remember { mutableStateOf("") }

    // Dropdown menu state for category
    val categories = listOf("Cell Phone", "Electronics", "Appliances", "Media")
    var expanded by remember { mutableStateOf(false) }

    // MutableInteractionSource for handling user interactions
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ID input with validation for range 101 to 999
        TextField(
            value = id,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                    id = newValue
                }
            },
            label = { Text("ID (101-999) *") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = price,
            onValueChange = { newPrice ->
                if (newPrice.all { it.isDigit() || it == '.' } && newPrice.toDoubleOrNull() ?: 0.0 > 0.0) {
                    price = newPrice
                }
            },
            label = { Text("Price (Positive) *") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Display the current date in the Delivery Date field
        TextField(
            value = deliveryDate,
            onValueChange = { deliveryDate = it },
            label = { Text("Delivery Date") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            readOnly = true // Make it read-only as it's auto-filled with current date
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown menu for selecting category
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Favorite switch placed here
        Column {
            Text("Favorite")
            Switch(
                checked = isFavorite,
                onCheckedChange = { isFavorite = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Product button placed after all text fields and the favorite switch
        Button(
            onClick = {
                // Validate input
                if (id.isNotBlank() && name.isNotBlank() && price.isNotBlank() && deliveryDate.isNotBlank() && category.isNotBlank()) {
                    val newProduct = Product_entity(
                        id = id.toInt(), // Convert id to Int
                        name = name,
                        price = price.toDouble(),
                        deliveryDate = deliveryDate,
                        category = category,
                        isFavorite = isFavorite
                    )
                    // Insert product into the database using the ViewModel
                    productViewModel.insertProduct(newProduct)
                    onProductAdded(newProduct)  // Notify the caller that the product has been added

                    // Navigate to ProductList after adding the product
                    navController.navigate("productList") {
                        popUpTo("createProduct") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Product")
        }
    }
}
