package com.salvaperez.challenge.presentation.payment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.salvaperez.challenge.presentation.utils.EventObserver
import com.salvaperez.challenge.R
import com.salvaperez.challenge.domain.model.ShoppingCartModel
import com.salvaperez.challenge.presentation.extensions.formatCredit
import com.salvaperez.challenge.presentation.extensions.showDialog
import com.salvaperez.challenge.presentation.payment.adapter.PaymentAdapter
import com.salvaperez.challenge.presentation.utils.Resource
import kotlinx.android.synthetic.main.payment_toolbar.*
import kotlinx.android.synthetic.main.payment_activity.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity() {

    private val vModel: PaymentViewModel by currentScope.viewModel(this)
    private lateinit var paymentAdapter: PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_activity)

        vModel.onInit()
        initViews()
        loadObservers()
    }

    private fun initViews(){
        paymentAdapter = PaymentAdapter()

        rvCheckout.layoutManager = LinearLayoutManager(this)
        rvCheckout.adapter = paymentAdapter
    }

    private fun loadObservers(){
        vModel.shoppingCartProducts.observe(this, Observer { items ->
            when(items.status){
                Resource.Status.SUCCESS -> showProducts(items.data as ShoppingCartModel)
            }
        })

        vModel.showDeletePopup.observe(this, EventObserver {
            showPopupDialog()
        })

        btnPaypal.setOnClickListener { showFinishShoppingDialog() }
        btnCreditCard.setOnClickListener { showFinishShoppingDialog() }
        cntDeleteProducts.setOnClickListener {
            vModel.clickDeleteProducts()
        }
    }

    private fun showProducts(productsList: ShoppingCartModel) {
        paymentAdapter.paymentList = productsList.product
        txTotalPayment.text = getString(R.string.euro, productsList.totalPrice.toString().formatCredit())
    }

    private fun showPopupDialog() {
        showDialog(
            title = "",
            message = getString(R.string.dialog_delete_shopping),
            cancelable = false,
            onYesClicked = {
                vModel.clearShoppingCart()
                finish()
            },
            onNoClicked = { })
    }

    private fun showFinishShoppingDialog(){
        Toast.makeText(this, getString(R.string.shopping_success), Toast.LENGTH_SHORT).show()
    }
}