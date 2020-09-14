package com.salvaperez.challenge.data.manager

import com.salvaperez.challenge.data.entity.ShoppingProductEntity

const val TSHIRT_DISCOUNT_CODE = "TSHIRT"
const val VOUCHER_DISCOUNT_CODE = "VOUCHER"
const val MUG = "MUG"
const val DISCOUNT_TSHIRT = 19.0

class CalculateFinalPriceManager {

    private val sum: Int = 1
    private var priceShirt : Double = 0.0
    private var priceVoucher : Double = 0.0
    private var priceMug : Double = 0.0


    fun getTotalSumProductCheckOut(vouchers: MutableList<ShoppingProductEntity>): Double{
        priceShirt = 0.0
        priceVoucher = 0.0
        priceMug = 0.0
        return  getDiscountTshirt(vouchers) + getDiscountVoucher(vouchers) + getTotalMug(vouchers)
    }

    fun getDiscountTshirt(vouchers: MutableList<ShoppingProductEntity>) : Double{
        var tshirt: Int = 0
        for(checkDiscount in vouchers){
            if(checkDiscount.code.contentEquals(TSHIRT_DISCOUNT_CODE)){
                tshirt = tshirt.plus(sum)
                priceShirt = checkDiscount.price
            }
        }

        return calculateShirtDiscount(tshirt, priceShirt)
    }

    fun calculateShirtDiscount(tshirt: Int, priceShirt: Double): Double{
        return when (tshirt) {
            1, 2 -> tshirt.times(priceShirt)
            else -> tshirt.times(DISCOUNT_TSHIRT)
        }
    }

    fun getDiscountVoucher(vouchers: MutableList<ShoppingProductEntity>): Double{
        var voucherCount: Int = 0
        for(checkDiscount in vouchers){
            if(checkDiscount.code.contentEquals(VOUCHER_DISCOUNT_CODE)){
                voucherCount = voucherCount.plus(sum)
                priceVoucher = checkDiscount.price
            }
        }

        return when (voucherCount) {
            1 -> voucherCount.times(priceVoucher)
            else -> calculateVoucherDiscount(voucherCount, priceVoucher)
        }
    }

    fun calculateVoucherDiscount(voucher : Int, priceVoucher: Double): Double{
        if(voucher.rem(2) == 0){
            return voucher * priceVoucher / 2
        }

        return ((voucher - 1) * priceVoucher) / 2 + priceVoucher
    }

    fun getTotalMug(vouchers: MutableList<ShoppingProductEntity>): Double{
        var mug: Int = 0

        for(checkDiscount in vouchers){
            if(checkDiscount.code.contentEquals(MUG)){
                mug = mug.plus(sum)
                priceMug = checkDiscount.price
            }
        }
        return mug.times(priceMug)
    }
}