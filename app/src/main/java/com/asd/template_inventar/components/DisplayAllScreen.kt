package com.asd.template_inventar.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.viewmodel.ProductsViewModel
import com.asd.template_inventar.utils.InternetStatus
import com.asd.template_inventar.utils.InternetStatusLive

@ExperimentalMaterialApi
@Composable
fun DisplayAllScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    onClickAdd: () -> Unit,
    onClickTop: () -> Unit
) {
    val products by remember { viewModel.listOfEntities }
    val loading by remember { viewModel.loading }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        LazyColumn {
            items(products) { item ->
                SingleEntityItem(product = item)
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 10.dp),
            onClick = { onClickAdd() }
        ) {
            Text(text = "Add")
        }
        val context = LocalContext.current
        FloatingActionButton(
            onClick = {
                if (InternetStatusLive.status.value == InternetStatus.ONLINE)
                    onClickTop()
                else
                    Toast.makeText(context, "Only available online!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 80.dp)
        ) {
            Text(text = "Top cheapest")
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SingleEntityItem(
    product: Product
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "name: ${product.nume}")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "quantity: ${product.cantitate}")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "price: ${product.pret}")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "type: ${product.tip}")
        }
    }
}