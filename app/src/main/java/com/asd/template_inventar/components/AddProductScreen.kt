package com.asd.template_inventar.components

import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asd.template_inventar.model.viewmodel.ProductsViewModel

@Composable
fun AddProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    addCallback: (String, String, Int, Int, Int, Boolean) -> Unit
) {
    var nameText by rememberSaveable { mutableStateOf("") }
    var typeText by rememberSaveable { mutableStateOf("") }
    var quantityText by rememberSaveable { mutableStateOf("0") }
    var priceText by rememberSaveable { mutableStateOf("0") }
    var discountText by rememberSaveable { mutableStateOf("0") }
    var statusText by rememberSaveable { mutableStateOf("false") }

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f)) {
        Column() {
            TextField(
                value = nameText,
                onValueChange = {
                    nameText = it
                },
                label = { Text("Name") }
            )
            TextField(
                value = typeText,
                onValueChange = {
                    typeText = it
                },
                label = { Text("Type") }
            )
            TextField(
                value = quantityText,
                onValueChange = {
                    quantityText = it
                },
                label = { Text("Quantity") }
            )
            TextField(
                value = priceText,
                onValueChange = {
                    priceText = it
                },
                label = { Text("Price") }
            )
            TextField(
                value = discountText,
                onValueChange = {
                    discountText = it
                },
                label = { Text("Discount") }
            )
            TextField(
                value = statusText,
                onValueChange = {
                    statusText = it
                },
                label = { Text("Status") }
            )
        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(30.dp),
            onClick = {
                try {
                    addCallback(
                        nameText,
                        typeText,
                        quantityText.toInt(),
                        priceText.toInt(),
                        discountText.toInt(),
                        statusText.toBoolean()
                    )
                } catch (exception: Exception) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Add")
        }
    }
}