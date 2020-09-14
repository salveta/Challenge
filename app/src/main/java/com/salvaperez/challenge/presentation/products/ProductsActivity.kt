package com.salvaperez.challenge.presentation.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.salvaperez.challenge.presentation.utils.EventObserver
import com.salvaperez.challenge.presentation.utils.Resource
import com.salvaperez.challenge.R
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.presentation.extensions.invisible
import com.salvaperez.challenge.presentation.extensions.open
import com.salvaperez.challenge.presentation.extensions.visible
import com.salvaperez.challenge.presentation.payment.PaymentActivity
import com.salvaperez.challenge.presentation.products.adapter.ProductsAdapter
import kotlinx.android.synthetic.main.products_activity.*
import kotlinx.android.synthetic.main.products_toolbar.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsActivity : AppCompatActivity() {

    private val vModel: ProductsViewModel by currentScope.viewModel(this)
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products_activity)

        vModel.onInit()
        initViews()
        loadObservers()
    }

    override fun onResume() {
        super.onResume()
        vModel.checkIfNeedShowShoppingIcon()
    }

    private fun initViews(){
        productsAdapter = ProductsAdapter(
            onClickProduct = {onClickProduct(it)}
        )

        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = productsAdapter
    }

    private fun loadObservers(){
        vModel.products.observe(this, Observer { products ->
            when(products.status){
                Resource.Status.SUCCESS -> showProducts(products.data as List<ProductsModel>)
                Resource.Status.ERROR -> showError()
                Resource.Status.LOADING -> showLoading()
            }
        })

        vModel.shoppingItemCount.observe(this, EventObserver { numberItems ->
            cntShoppingCart.visible()
            txShoppingTotalNumberToolbar.text = numberItems
        })

        vModel.clickToolbarIcon .observe(this, EventObserver {
            open(PaymentActivity::class.java)
        })

        vModel.hasProductsInShoppingCart.observe(this, EventObserver {
            cntShoppingCart.invisible()

        })

        cntShoppingCart.setOnClickListener {
            vModel.clickOnToolbarIcon()
        }
    }

    private fun showProducts(productsList: List<ProductsModel>){
        productsAdapter.productsList = productsList
    }

    private fun showLoading(){
        doInvisibleLoading()
    }

    private fun showError(){
        doInvisibleLoading()
        txVoucherUnavailable.visible()
    }

    private fun doInvisibleLoading(){
        pbLoadingProducts.invisible()
    }

    private fun onClickProduct(product: ProductsModel){
        vModel.addProductToShoppingCart(product)
    }
}