package com.salvaperez.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.usecase.AddProductInShoppingCart
import com.salvaperez.challenge.domain.usecase.GetProducts
import com.salvaperez.challenge.domain.usecase.GetShoppingCart
import com.salvaperez.challenge.presentation.products.ProductsViewModel
import com.salvaperez.challenge.presentation.utils.Event
import com.salvaperez.challenge.presentation.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductsActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getProducts: GetProducts

    @Mock
    lateinit var addProductInShoppingCart: AddProductInShoppingCart

    @Mock
    lateinit var getShoppingCart: GetShoppingCart

    private lateinit var vm: ProductsViewModel
    private val products: Observer<Resource<List<ProductsModel>>> = mock()
    private val clickToolbarIcon: Observer<Event<Unit>> = mock()
    private val shoppingItemCount: Observer<Event<String>> = mock()
    private val hasProductsInShoppingCart: Observer<Event<Unit>> = mock()

    private val uiScope = CoroutineScope(Dispatchers.Main)

    @Before
    fun setUp() {
        vm = ProductsViewModel(getProducts, addProductInShoppingCart, getShoppingCart)
    }

    @Test
    fun `observing LiveData start video intent`() {
        vm.clickToolbarIcon.observeForever(clickToolbarIcon)
        vm.clickOnToolbarIcon()

        verify(clickToolbarIcon).onChanged(any())
    }

    @Test
    fun `check if addProduct method should add product`() {
        val product = ProductsModel("Mug", "Cabify Coffe Mug", 7.5)

        vm.shoppingItemCount.observeForever(shoppingItemCount)
        vm.addProductToShoppingCart(product)

        verify(shoppingItemCount).onChanged(any())

        uiScope.launch {
            whenever(addProductInShoppingCart.invoke(product))
        }
    }

    @Test
    fun `Shopping icon have to be hide`() {
        vm.hasProductsInShoppingCart.observeForever(hasProductsInShoppingCart)
        vm.checkIfNeedShowShoppingIcon()
        verifyZeroInteractions(hasProductsInShoppingCart)
    }

    @Test
    fun `Shopping icon have to be show when product is saved`() {
        val product = ProductsModel("Mug", "Cabify Coffe Mug", 7.5)

        vm.hasProductsInShoppingCart.observeForever(hasProductsInShoppingCart)

        uiScope.launch {
            whenever(addProductInShoppingCart.invoke(product))
            vm.checkIfNeedShowShoppingIcon()
            verify(hasProductsInShoppingCart).onChanged(any())
        }
    }

    @Test
    fun `OnInit show the products`() {
        vm.products.observeForever(products)

        uiScope.launch {
            whenever(getProducts.invoke(onGetProductSuccess = {
                verify(products).onChanged(any())
            }, onGetErrorProduct = {}))
        }
    }
}