package com.salvaperez.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.salvaperez.challenge.domain.usecase.ClearShoppingCart
import com.salvaperez.challenge.domain.usecase.GetShoppingCart
import com.salvaperez.challenge.presentation.payment.PaymentViewModel
import com.salvaperez.challenge.presentation.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PaymentsActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getShoppingCart: GetShoppingCart

    @Mock
    lateinit var clearShoppingCart: ClearShoppingCart

    private lateinit var vm: PaymentViewModel
    private val showDeletePopup: Observer<Event<Unit>> = mock()
    private val uiScope = CoroutineScope(Dispatchers.Main)

    @Before
    fun setUp() {
        vm = PaymentViewModel(getShoppingCart, clearShoppingCart)
    }

    @Test
    fun `Click deleteProducts method should show a popup`() {
        vm.showDeletePopup.observeForever(showDeletePopup)
        vm.clickDeleteProducts()

        Mockito.verify(showDeletePopup).onChanged(any())
    }

    @Test
    fun `check in clearShoppingCart method should delete all product`() {
        uiScope.launch {
            whenever(clearShoppingCart.invoke())
        }
    }

}