package com.nabilla.shoppinglist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nabilla.shoppinglist.R
import com.nabilla.shoppinglist.db.Shopping
import com.nabilla.shoppinglist.viewmodels.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.shopping_item.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentAddItem:Fragment(R.layout.fragment_add_item) {

    lateinit var shoppingViewModel: ShoppingViewModel
    lateinit var mainActivity: MainActivity
    var noteID = -1;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = (activity as MainActivity)
        shoppingViewModel = mainActivity.shoppingViewModel

        btnSaved.setOnClickListener {
            val shoppingItem = edtItem.text.toString()
            val shoppingPrice = edtPrice.text.toString()

            if(mainActivity.isUpdateItem){
                if (shoppingItem.isNotEmpty() && shoppingPrice.isNotEmpty()) {
                    val updateItem = Shopping(shoppingItem,shoppingPrice.toDouble(), mainActivity.myShoppingItem.timestamp, mainActivity.myShoppingItem.isSelected)
                    updateItem.id = mainActivity.myShoppingItem.id
                    shoppingViewModel.updateObject(updateItem)
                    Toast.makeText(mainActivity.applicationContext, "$shoppingItem Added", Toast.LENGTH_SHORT).show()
                }
            }else{
                if (shoppingItem.isNotEmpty() && shoppingPrice.isNotEmpty()) {
                    shoppingViewModel.addObject(Shopping(shoppingItem,shoppingPrice.toDouble(), System.currentTimeMillis(), 0))
                    Toast.makeText(mainActivity.applicationContext, "$shoppingItem Added", Toast.LENGTH_SHORT).show()
                }
            }
            view.findNavController().navigate(R.id.action_fragmentAddItem_to_fragmentList)
        }

        btnBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_fragmentAddItem_to_fragmentList)
        }

        if(mainActivity.isUpdateItem){
            displayItem()
        }
    }

    private fun displayItem(){
        edtItem.setText(mainActivity.myShoppingItem.shoppingTitle)
        edtPrice.setText(mainActivity.myShoppingItem.shoppingPrice.toString())
    }

    override fun onDestroy() {
        mainActivity.isUpdateItem = false
        super.onDestroy()
    }
}