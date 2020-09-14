package com.salvaperez.challenge.presentation.products.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salvaperez.challenge.R
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.presentation.extensions.basicDiffUtil
import com.salvaperez.challenge.presentation.extensions.formatCredit
import com.salvaperez.challenge.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(private val onClickProduct: (ProductsModel) -> Unit): RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){

    var productsList: List<ProductsModel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new ->
            old.name == new.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_product, false)
        return ViewHolder(view, onClickProduct)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productModel = productsList[position]
        holder.bind(productModel)
    }

    class ViewHolder(view: View, val onClickProduct: (ProductsModel) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(productItem: ProductsModel) {

            itemView.txVoucherName.text = productItem.name
            itemView.txVoucherCode.text = productItem.code
            itemView.txVoucherPrice.text = itemView.resources.getString(R.string.euro, productItem.price.toString().formatCredit())

            itemView.cvItemProduct.setOnClickListener {
                onClickProduct(productItem)
            }
        }
    }
}