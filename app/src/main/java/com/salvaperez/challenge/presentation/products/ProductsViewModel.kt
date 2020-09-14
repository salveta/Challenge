package com.salvaperez.challenge.presentation.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salvaperez.challenge.data.api.ChallengeError
import com.salvaperez.challenge.presentation.utils.Resource
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.usecase.AddProductInShoppingCart
import com.salvaperez.challenge.domain.usecase.GetProducts
import com.salvaperez.challenge.domain.usecase.GetShoppingCart
import com.salvaperez.challenge.presentation.utils.Event
import kotlinx.coroutines.launch

class ProductsViewModel(private val getProducts: GetProducts,
                        private val addProductInShoppingCart: AddProductInShoppingCart,
                        private val getShoppingCart: GetShoppingCart): ViewModel(){

    private val _products = MutableLiveData<Resource<List<ProductsModel>>>()
    val products: LiveData<Resource<List<ProductsModel>>> get() = _products

    private val _shoppingItemCount = MutableLiveData<Event<String>>()
    val shoppingItemCount: LiveData<Event<String>> get() = _shoppingItemCount

    private val _clickToolbarIcon = MutableLiveData<Event<Unit>>()
    val clickToolbarIcon: LiveData<Event<Unit>> get() = _clickToolbarIcon

    private val _hasProductsInShoppingCart = MutableLiveData<Event<Unit>>()
    val hasProductsInShoppingCart: LiveData<Event<Unit>> get() = _hasProductsInShoppingCart

    private var count: Int = 0
    private val sum: Int = 1

    fun onInit(){
        viewModelScope.launch {
            getProducts.invoke(
                onGetErrorProduct = {getErrorProducts(it)},
                onGetProductSuccess = {getProducts(it)}
            )
        }
    }

    private fun getProducts(products: List<ProductsModel>){
        hideLoading()

        if(products.isEmpty()){
            _products.value = Resource.error()
        }else{
            _products.value = Resource.success(products)
        }
    }

    private fun getErrorProducts(error: ChallengeError){
        hideLoading()
        _products.value = Resource.error(error)
    }

    private fun hideLoading(){
        _products.value = Resource.loading()
    }

    fun addProductToShoppingCart(product: ProductsModel){
        count = count.plus(sum)
        _shoppingItemCount.value = Event(count.toString())

        viewModelScope.launch {
            addProductInShoppingCart.invoke(product)
        }
    }

    fun clickOnToolbarIcon(){
        _clickToolbarIcon.value = Event(Unit)
    }

    fun checkIfNeedShowShoppingIcon() {
        viewModelScope.launch {
            if(getShoppingCart.invoke().product.isEmpty()){
                resetCountShopping()
                _hasProductsInShoppingCart.value = Event(Unit)
            }
        }
    }

    private fun resetCountShopping() {
        count = 0
    }
}