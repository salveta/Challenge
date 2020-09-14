package com.salvaperez.challenge.presentation.payment.adapter

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salvaperez.challenge.R
import com.salvaperez.challenge.domain.model.ShoppingProductModel
import com.salvaperez.challenge.presentation.extensions.basicDiffUtil
import com.salvaperez.challenge.presentation.extensions.formatCredit
import com.salvaperez.challenge.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_payment.view.*

class PaymentAdapter(): RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    var paymentList: List<ShoppingProductModel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new ->
            old.name == new.name
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_payment, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productModel = paymentList[position]
        holder.bind(productModel)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(productItem: ShoppingProductModel) {
            itemView.txProductName.text = productItem.name

            when {
                productItem.code.contentEquals(VOUCHER_DISCOUNT_CODE) -> {
                    when (productItem.units) {
                        1 -> concatenatedNoOffer(itemView.txUnits, itemView.resources, productItem)
                        else -> concatenatedOfferr(itemView.txUnits, itemView.resources, productItem, itemView.resources.getString(R.string.offer_voucher))
                    }
                }
                productItem.code.contentEquals(TSHIRT_DISCOUNT_CODE) -> {
                    when (productItem.units) {
                        1,2 -> concatenatedNoOffer(itemView.txUnits, itemView.resources, productItem)
                        else -> concatenatedOfferr(itemView.txUnits, itemView.resources, productItem, itemView.resources.getString(R.string.offer_tshirt))
                    }
                }
                else -> {
                    itemView.txUnits.text = itemView.resources.getString(R.string.units, productItem.units)
                }
            }

            itemView.txTotalPriceCheckout.text = itemView.resources.getString(R.string.euro, productItem.price.toString().formatCredit())
        }

        private fun concatenatedOfferr(tx_units: TextView, resources: Resources, checkoutItem: ShoppingProductModel, offerString: String){
            val concatenatedOffer = resources.getString(R.string.units_offer, checkoutItem.units, offerString)
            tx_units.text = concatenatedOffer
        }

        private fun concatenatedNoOffer(tx_units: TextView, resources: Resources, checkoutItem: ShoppingProductModel){
            val concatenatedNoOffer = resources.getString(R.string.units, checkoutItem.units)
            tx_units.text = concatenatedNoOffer
        }
    }

    companion object {
        const val TSHIRT_DISCOUNT_CODE = "TSHIRT"
        const val VOUCHER_DISCOUNT_CODE = "VOUCHER"
    }
}