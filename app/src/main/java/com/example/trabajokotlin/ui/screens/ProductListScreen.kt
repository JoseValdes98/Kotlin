package com.example.trabajokotlin.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.trabajokotlin.viewmodel.MainViewModel
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color


@Composable
fun ProductListScreen(
    vm: MainViewModel,
    onGoToCart: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val results = remember(query, vm.products) { vm.search(query) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos",color = Color.White) },
                actions = {

                    TextButton(onClick = onGoToCart) { Text("Carrito (${vm.cartCount()})", color = Color.White) }

                }

            )
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(12.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar producto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(results) { p ->
                    Card(Modifier.fillMaxWidth().padding(6.dp)) {
                        Row(
                            Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (p.imageRes != null) {
                                Image(
                                    painter = painterResource(id = p.imageRes),
                                    contentDescription = p.name,
                                    modifier = Modifier.size(56.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ShoppingBag,
                                    contentDescription = "Sin imagen",
                                    modifier = Modifier.size(56.dp)
                                )
                            }

                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text(p.name, style = MaterialTheme.typography.titleMedium)
                                Text("Precio: \$${p.price}", style = MaterialTheme.typography.bodySmall)
                            }
                            Button(onClick = { vm.addToCart(p) }) { Text("Agregar") }
                        }

                    }
                }
            }
        }
    }
}
