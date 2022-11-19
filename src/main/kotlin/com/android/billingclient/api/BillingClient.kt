package com.android.billingclient.api

import android.content.Context

class BillingClient(ctx: Context){

    object BillingResponseCode {
        const val OK = 0
    }

    object SkuType {
        const val INAPP = "inapp"
    }

    fun startConnection(listener: BillingClientStateListener){

    }

    fun querySkuDetailsAsync(params: SkuDetailsParams, listener: (billingResult: BillingResult, skuDetailsList: List<SkuDetails>) -> Unit){

    }

    fun querySkuDetailsAsync(params: SkuDetailsParams, listener: SkuDetailsResponseListener){

    }

    fun setListener(listener: (billingResult: BillingResult?, purchases: MutableList<Purchase>?) -> Unit): BillingClient = this

    fun build() = this

    companion object {

        fun newBuilder(ctx: Context) = BillingClient(ctx)

    }

}