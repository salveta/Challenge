package com.salvaperez.challenge.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salvaperez.challenge.domain.model.ShoppingCartModel
import com.salvaperez.challenge.domain.usecase.ClearShoppingCart
import com.salvaperez.challenge.domain.usecase.GetShoppingCart
import com.salvaperez.challenge.presentation.utils.Event
import com.salvaperez.challenge.presentation.utils.Resource
import kotlinx.coroutines.launch

class PaymentViewModel(private val getShoppingCart: GetShoppingCart,
                       private val clearShoppingCart: ClearShoppingCart): ViewModel(){

    private val _shoppingCartProducts = MutableLiveData< Resource<ShoppingCartModel>>()
    val shoppingCartProducts: LiveData<Resource<ShoppingCartModel>> get() = _shoppingCartProducts

    private val _showDeletePopup = MutableLiveData<Event<Unit>>()
    val showDeletePopup: LiveData<Event<Unit>> get() = _showDeletePopup

    fun onInit(){
        viewModelScope.launch {
            val products = getShoppingCart.invoke()
            _shoppingCartProducts.value = Resource.success(products)
        }
    }

    fun clickDeleteProducts(){
        _showDeletePopup.value = Event(Unit)
    }

    fun clearShoppingCart(){
        viewModelScope.launch {
            clearShoppingCart.invoke()
        }
    }

}