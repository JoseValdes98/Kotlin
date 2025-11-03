package com.example.trabajokotlin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.TopAppBar

import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.trabajokotlin.viewmodel.MainViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(vm: MainViewModel, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                actions = {
                    TextButton(onClick = { vm.sendSale() }) { Text("Enviar venta") }
                }
            )
        }
    ) { inner ->
        Column(Modifier.padding(inner).fillMaxSize().padding(12.dp)) {
            if (vm.cart.isEmpty()) {
                Text("El carrito está vacío")
            } else {
                LazyColumn (modifier = Modifier.weight(1f), 
                    contentPadding = PaddingValues(bottom = 80.dp)) {
                    items(vm.cart, key = { it.product.id }) { item ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = { value ->
                                if (value == DismissValue.DismissedToStart || value == DismissValue.DismissedToEnd) {
                                    vm.removeFromCart(item.product.id)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            background = { /* fondo opcional de swipe */ },
                            /*dismissThresholds = { FractionalThreshold(0.35f) }*/
                        ) {
                            Card(Modifier.fillMaxWidth().padding(6.dp)) {
                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val img = item.product.imageRes
                                    if (img != null) {
                                        Image(
                                            painter = painterResource(id = img),
                                            contentDescription = item.product.name,
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
                                        Text(item.product.name)
                                        Text("Precio: \$${item.product.price}")
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        OutlinedButton(onClick = { vm.decQty(item.product.id) }) { Text("-") }
                                        Spacer(Modifier.width(8.dp))
                                        Text("${item.qty}")
                                        Spacer(Modifier.width(8.dp))
                                        OutlinedButton(onClick = { vm.incQty(item.product.id) }) { Text("+") }
                                    }
                                }

                            }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Column(Modifier.weight(1f)){
                Text("Total: \$${vm.cartTotal()}", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver")}
                }
            }
        }
    }

