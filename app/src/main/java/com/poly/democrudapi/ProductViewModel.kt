package com.poly.democrudapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>> = _products

    private val selectedProduct = MutableLiveData<Product?>()

    val slProduct: LiveData<Product?> = selectedProduct
    init {
        getProducts()
    }
    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.getLit()
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.map { it.toProduct() })
                } else {
                    _products.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getProduct: " + e.message)
                _products.postValue(emptyList())
            }
        }
    }
    fun getDetail(id: String){
        viewModelScope.launch {
           try {
               val response = RetrofitService().productService.getDetal(id)
               if (response.isSuccessful) {
                   selectedProduct.postValue(response.body()?.toProduct())
                   Log.e("del","hehe")
               } else {
                   selectedProduct.postValue(null)
               }
           }catch (e: Exception){
               Log.e("del","loi APi")
           }
        }
    }


    fun clearSelected() {
        selectedProduct.value = null
    }
}