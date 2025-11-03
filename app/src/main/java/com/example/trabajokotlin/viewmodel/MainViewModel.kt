package com.example.trabajokotlin.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.trabajokotlin.R
import com.example.trabajokotlin.model.CartItem
import com.example.trabajokotlin.model.Product

class MainViewModel : ViewModel() {

    // Catálogo estático (simula los productos)
    val products: SnapshotStateList<Product> = mutableStateListOf(
        Product(1, "Carta Pokemon", 59990, null),
        Product(2, "Carte magic", 24990, null),
        Product(3, "Nose que", 19990, null),
        Product(4, "tampoco", 39990, null)
    )

    // Carrito: persistencia simulada en memoria (cumple “ingreso/modificación”)
    val cart: SnapshotStateList<CartItem> = mutableStateListOf()

    fun addToCart(p: Product) {
        val idx = cart.indexOfFirst { it.product.id == p.id }
        if (idx >= 0) {
            cart[idx] = cart[idx].copy(qty = cart[idx].qty + 1)
        } else {
            cart.add(CartItem(product = p, qty = 1))
        }
    }

    fun incQty(productId: Int) {
        val i = cart.indexOfFirst { it.product.id == productId }
        if (i >= 0) cart[i] = cart[i].copy(qty = cart[i].qty + 1)
    }

    fun decQty(productId: Int) {
        val i = cart.indexOfFirst { it.product.id == productId }
        if (i >= 0) {
            val current = cart[i]
            if (current.qty > 1) {
                cart[i] = current.copy(qty = current.qty - 1)
            } else {
                cart.removeAt(i)
            }
        }
    }

    fun removeFromCart(productId: Int) {
        cart.removeAll { it.product.id == productId }
    }

    fun cartCount(): Int = cart.sumOf { it.qty }
    fun cartTotal(): Int = cart.sumOf { it.product.price * it.qty }

    // Búsqueda local (UI la usa para filtrar)
    fun search(query: String): List<Product> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return products
        return products.filter { it.name.lowercase().contains(q) }
    }

    // Simula “enviar venta al backend”
    fun sendSale() { /* No-op (estático). Puedes mostrar Snackbar en la UI al llamarlo) */ }
}
