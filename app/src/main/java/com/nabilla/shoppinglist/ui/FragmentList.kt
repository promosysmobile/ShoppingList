package com.nabilla.shoppinglist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nabilla.shoppinglist.R
import com.nabilla.shoppinglist.viewmodels.ShoppingViewModel
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabilla.shoppinglist.adapters.ShoppingAdapter
import com.nabilla.shoppinglist.databinding.FragmentListBinding
import com.nabilla.shoppinglist.db.Shopping
import kotlinx.android.synthetic.main.fragment_list.*

class FragmentList:Fragment(R.layout.fragment_list), ShoppingAdapter.ShoppingClickInterface,
    ShoppingAdapter.ShoppingClickDeleteInterface, ShoppingAdapter.ShoppingCheckedInterface {

    lateinit var shoppingViewModel: ShoppingViewModel
    lateinit var mainActivity: MainActivity
    private lateinit var uncheckedItemAdapter:ShoppingAdapter
    private lateinit var checkedItemAdapter:ShoppingAdapter

    private var totalPrice = 0.0
    private lateinit var myList:List<Shopping>
    private lateinit var myView:View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = (activity as MainActivity)
        myView = view
        setupUncheckRecyclerView()
        setupCheckedRecyclerView()
        shoppingViewModel = mainActivity.shoppingViewModel
        shoppingViewModel.uncheckedList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                uncheckedItemAdapter.updateList(it)
            }
        })

        shoppingViewModel.checkedList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                myList = list
                checkedItemAdapter.updateList(it)
                updatePrice()
            }
        })

        btn_add_item.setOnClickListener {
            view.findNavController().navigate(R.id.action_fragmentList_to_fragmentAddItem)
        }
    }

    private fun setupCheckedRecyclerView() = recvw_checked_item.apply {
        checkedItemAdapter = ShoppingAdapter(this@FragmentList,this@FragmentList,this@FragmentList)
        adapter = checkedItemAdapter
        layoutManager = LinearLayoutManager(mainActivity.applicationContext)
    }

    private fun setupUncheckRecyclerView() = recvw_unchecked_item.apply {
        uncheckedItemAdapter = ShoppingAdapter(this@FragmentList,this@FragmentList, this@FragmentList)
        adapter = uncheckedItemAdapter
        layoutManager = LinearLayoutManager(mainActivity.applicationContext)
    }

    override fun onDeleteIconClick(shopping: Shopping) {
        shoppingViewModel.deleteObject(shopping)
        Toast.makeText(mainActivity.applicationContext,"Item Deleted",Toast.LENGTH_SHORT).show()
    }

    override fun onShoppingClick(shopping: Shopping) {
        Log.i("FragmentList","onShoppingClick")
        mainActivity.myShoppingItem = shopping
        mainActivity.isUpdateItem = true
        myView.findNavController().navigate(R.id.action_fragmentList_to_fragmentAddItem)
    }

    override fun onShoppingChecked(shopping: Shopping) {
        Log.i("FragmentList","onShoppingChecked")
        shoppingViewModel.updateObject(shopping)
    }

    private fun updatePrice(){
        totalPrice = 0.0
        for (element in myList){
            totalPrice += element.shoppingPrice
        }
        if(myList.isNotEmpty()){
            layout_price.visibility = View.VISIBLE
        }else{
            layout_price.visibility = View.GONE
        }
        txt_price.text = totalPrice.toString()
    }

}