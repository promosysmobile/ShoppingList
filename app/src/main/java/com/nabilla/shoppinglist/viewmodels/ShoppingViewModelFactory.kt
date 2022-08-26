package com.nabilla.shoppinglist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
class ShoppingViewModelFactory(
    val application: Application
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(application) as T
    }
}
*/

class ShoppingViewModelFactory(val mApplication: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(mApplication) as T
    }
}