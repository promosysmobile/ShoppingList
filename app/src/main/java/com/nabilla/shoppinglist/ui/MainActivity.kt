package com.nabilla.shoppinglist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nabilla.shoppinglist.R
import com.nabilla.shoppinglist.db.Shopping
import com.nabilla.shoppinglist.viewmodels.ShoppingViewModel
import com.nabilla.shoppinglist.viewmodels.ShoppingViewModelFactory


class MainActivity : AppCompatActivity(){

    lateinit var shoppingViewModel: ShoppingViewModel
    var isUpdateItem = false
    lateinit var myShoppingItem:Shopping

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shoppingViewModel =
            ViewModelProvider(this, ShoppingViewModelFactory(application))
                .get(
                    ShoppingViewModel::class.java
                )
    }
}