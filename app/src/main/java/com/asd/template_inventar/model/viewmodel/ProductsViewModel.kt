package com.asd.template_inventar.model.viewmodel

import java.lang.Exception
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.usecase.AddProductsUseCase
import com.asd.template_inventar.model.usecase.GetProductsUseCase
import com.asd.template_inventar.model.usecase.SyncUseCase

@HiltViewModel
class ProductsViewModel @Inject constructor(
    val getAllUseCase: GetProductsUseCase,
    val addUseCase: AddProductsUseCase,
    val syncUseCase: SyncUseCase
) : ViewModel() {
    val loading = mutableStateOf(false)

    private val _listOfEntities: MutableState<List<Product>> = mutableStateOf(emptyList())
    val listOfEntities: State<List<Product>> = _listOfEntities

    private val _listOfTopEntities: MutableState<List<Product>> = mutableStateOf(emptyList())
    val listOfTopEntities: State<List<Product>> get() {
        getTop()
        return _listOfTopEntities
    }

    init {
        viewModelScope.launch {
            loading.value = true
            val entityList = getAllUseCase()
            _listOfEntities.value = entityList
            delay(1000)
            loading.value = false
        }
    }

    fun add(nume: String, tip: String, cantitate: Int, pret: Int, discount: Int, status: Boolean = false) {
        viewModelScope.launch {
            loading.value = true
            
            try {
                val product = Product(
                    nume = nume,
                    tip = tip,
                    cantitate = cantitate,
                    pret = pret,
                    discount = discount,
                    status = status
                )
                
                Log.d("Debug","Add: $product")
                
                addUseCase(
                    entity = product
                )
            }catch (exception: Exception) {
                Log.d("Debug", "Exception: " + exception.message?:"")
            }

            loading.value = false
        }
    }

    private fun getTop() {
        viewModelScope.launch {
            loading.value = true
            
            Log.d("Debug","Generating top cheapest")
            
            val entityList = getAllUseCase()
            _listOfTopEntities.value = entityList.sortedByDescending { it.pret }.take(15)

            delay(1000)
            loading.value = false
        }
    }

    fun syncLocalData() {
        viewModelScope.launch {
            Log.d("Debug","Reconnection sync")
            loading.value = true
            
            try {
                syncUseCase()
                delay(1000)
            } catch (exception: Exception) {
                Log.d("Debug", "Sync exception: $exception")
            }
            
            loading.value = false
        }
    }
}