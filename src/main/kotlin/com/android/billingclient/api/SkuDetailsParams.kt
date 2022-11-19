package com.android.billingclient.api

class SkuDetailsParams(){

    fun build() = this

    fun setSkusList(list: List<String>): SkuDetailsParams {

        return this
    }

    fun setType(type: String){

    }

    companion object {
        fun newBuilder() = SkuDetailsParams()
    }

}