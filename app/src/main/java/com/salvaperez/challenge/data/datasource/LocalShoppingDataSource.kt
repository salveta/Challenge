package com.salvaperez.challenge.data.datasource

import com.salvaperez.challenge.data.entity.ShoppingProductEntity
import com.salvaperez.challenge.data.manager.CalculateFinalPriceManager
import com.salvaperez.challenge.domain.model.ShoppingCartModel
import com.salvaperez.challenge.domain.model.toShoppingCartModel

class LocalShoppingDataSource(private val calculateFinalPrice: CalculateFinalPriceManager): ShoppingDataSource {

    private var vouchers: MutableList<ShoppingProductEntity> = mutableListOf()

    override suspend fun addItemShoppingCart(item: ShoppingProductEntity) {
        vouchers.add(item)
    }

    override suspend fun clearItemsShoppingCart() {
        vouchers.clear()
    }

    override fun createCheckout(): ShoppingCartModel {
        val removeEqualElements = vouchers
        var singleItems = removeEqualElements.distinct()

        singleItems.find { it.code == VOUCHER_DISCOUNT_CODE }?.units = vouchers.count { it.code == VOUCHER_DISCOUNT_CODE }
        singleItems.find { it.code == TSHIRT_DISCOUNT_CODE }?.units = vouchers.count { it.code == TSHIRT_DISCOUNT_CODE }
        singleItems.find { it.code == MUG }?.units = vouchers.count { it.code == MUG }

        singleItems.find { it.code == VOUCHER_DISCOUNT_CODE }?.price = calculateFinalPrice.getDiscountVoucher(vouchers)
        singleItems.find { it.code == TSHIRT_DISCOUNT_CODE }?.price = calculateFinalPrice.getDiscountTshirt(vouchers)
        singleItems.find { it.code == MUG }?.price = calculateFinalPrice.getTotalMug(vouchers)

        singleItems = singleItems.filter { it.units != 0 }

        return ShoppingCartModel(singleItems.map { it.toShoppingCartModel() }, calculateFinalShoppingPrice())
    }

    private fun calculateFinalShoppingPrice(): Double {
        return calculateFinalPrice.getTotalSumProductCheckOut(vouchers)
    }

    companion object {
        const val TSHIRT_DISCOUNT_CODE = "TSHIRT"
        const val VOUCHER_DISCOUNT_CODE = "VOUCHER"
        const val MUG = "MUG"
    }
}