package com.android.billingclient.api

interface SkuDetailsResponseListener {

    fun onSkuDetailsResponse(billingResult: BillingResult, skuDetailsList: List<SkuDetails>)

}