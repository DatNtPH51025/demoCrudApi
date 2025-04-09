package com.poly.democrudapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.poly.democrudapi.ui.theme.DemoCrudApiTheme

class MainActivity : ComponentActivity() {

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoCrudApiTheme {
                ProductScreen(productViewModel)
            }
        }
    }
}

@Composable
fun ProductScreen(viewModel: ProductViewModel) {
    val lstProduct by viewModel.products.observeAsState()
    val selected by viewModel.slProduct.observeAsState()

    // Hiển thị Dialog khi có sản phẩm được chọn
    selected?.let {
        DetailDialog(product = it, onDismiss = { viewModel.clearSelected() })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(lstProduct ?: emptyList()) { product ->
            ProductItem(
                product = product,
                onClickProduct = { id -> viewModel.getDetail(id) }
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClickProduct: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClickProduct(product.id) }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 10.dp)
            )
            Column {
                Text(text = "Name: ${product.name}")
                Text(text = "Price: ${product.price}")
            }
        }
    }
}

@Composable
fun DetailDialog(
    product: Product,
    onDismiss: () -> Unit,
) {
    androidx.compose.material.AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), // vừa đủ, không full màn hình như .fillMaxSize()
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Product Detail")
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(text = "Name: ${product.name}")
                    Text(text = "Price: ${product.price}")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoCrudApiTheme {
        Greeting("Android")
    }
}
