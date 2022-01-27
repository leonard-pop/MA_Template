package com.asd.template_inventar.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.asd.template_inventar.model.viewmodel.ProductsViewModel

@ExperimentalMaterialApi
@Composable
fun DisplayCheapestScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val products by remember { viewModel.listOfTopEntities }
    val loading by remember { viewModel.loading }

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        LazyColumn {
            items(products) { product ->
                SingleEntityItem(
                    entity = product
                )
            }
        }
    }
    CircularIndeterminateProgressBar(isDisplayed = loading)
}

