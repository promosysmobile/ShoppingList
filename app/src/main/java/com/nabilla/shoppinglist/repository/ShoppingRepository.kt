package com.nabilla.shoppinglist.repository

import androidx.lifecycle.LiveData
import com.nabilla.shoppinglist.db.Shopping
import com.nabilla.shoppinglist.db.ShoppingDao
import kotlinx.coroutines.flow.StateFlow

class ShoppingRepository(private val shoppingDao:ShoppingDao) {

    val allList:LiveData<List<Shopping>> = shoppingDao.getAllShoppingList()
    val uncheckedList:LiveData<List<Shopping>> = shoppingDao.getAllUncheckItem()
    val checkedList:LiveData<List<Shopping>> = shoppingDao.getAllCheckedItem()

    //val uncheckList2:StateFlow<List<Shopping>> = shoppingDao.getAllUncheckItem2()

    suspend fun insert(shopping: Shopping) {
        shoppingDao.insert(shopping)
    }

    suspend fun delete(shopping: Shopping) {
        shoppingDao.delete(shopping)
    }

    suspend fun update(shopping: Shopping) {
        shoppingDao.update(shopping)
    }

}