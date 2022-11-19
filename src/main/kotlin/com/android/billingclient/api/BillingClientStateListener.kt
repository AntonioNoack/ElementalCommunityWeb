package com.android.billingclient.api

interface BillingClientStateListener {
    fun onBillingSetupFinished(billingResult: BillingResult)
    fun onBillingServiceDisconnected()
}