package com.nabilla.shoppinglist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nabilla.shoppinglist.databinding.ShoppingItemBinding
import com.nabilla.shoppinglist.db.Shopping

class ShoppingAdapter(
    val shoppingClickDeleteInterface: ShoppingClickDeleteInterface,
    val shoppingClickInterface: ShoppingClickInterface,
    val shoppingCheckedInterface: ShoppingCheckedInterface
): RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>(){

    private val myList = ArrayList<Shopping>()

    inner class ShoppingViewHolder(val binding: ShoppingItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            ShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.binding.apply {
            val shoppingObj = myList[position]
            txtShoppingTitle.text = shoppingObj.shoppingTitle
            txtShoppingTimestamp.text = shoppingObj.shoppingPrice.toString()

            idIVDelete.setOnClickListener {
                shoppingClickDeleteInterface.onDeleteIconClick(shoppingObj)
            }

            when(shoppingObj.isSelected){
                0 ->  checkItem.isChecked = false
                1 ->  checkItem.isChecked = true
            }

            icEdit.setOnClickListener{
                shoppingClickInterface.onShoppingClick(shoppingObj)
            }

            checkItem.setOnClickListener {
                when(shoppingObj.isSelected){
                    0 -> {
                        shoppingObj.isSelected = 1
                        //checkItem.isChecked = true
                    }

                    1 -> {
                        shoppingObj.isSelected = 0
                        //checkItem.isChecked = false
                    }
                }
                shoppingCheckedInterface.onShoppingChecked(shoppingObj)
            }

        }
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Shopping>) {
        myList.clear()
        myList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = myList.size

    interface ShoppingClickDeleteInterface {
        fun onDeleteIconClick(shopping: Shopping)
    }

    interface ShoppingClickInterface {
        fun onShoppingClick(shopping: Shopping)
    }

    interface ShoppingCheckedInterface {
        fun onShoppingChecked(shopping: Shopping)
    }

}