package com.nabilla.shoppinglist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.nabilla.shoppinglist.db.Shopping
import com.nabilla.shoppinglist.db.ShoppingDatabase
import com.nabilla.shoppinglist.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//class ShoppingViewModel(application: Application): ViewModel() {
class ShoppingViewModel(application: Application): AndroidViewModel(application) {

    val allList: LiveData<List<Shopping>>
    val checkedList: LiveData<List<Shopping>>
    val uncheckedList: LiveData<List<Shopping>>
    val repository:ShoppingRepository
    //private val _myUiState = MutableStateFlow<List<Shopping>>(emptyList())
    //val myUiState: StateFlow<List<Shopping>> = _myUiState

    init {
        Log.i("MainActivity","Init ViewModel")
        val dao = ShoppingDatabase.getDatabase(application).getShoppingDao()
        repository = ShoppingRepository(dao)
        allList = repository.allList
        checkedList = repository.checkedList
        uncheckedList = repository.uncheckedList

    }

    fun addObject(shopping: Shopping) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(shopping)
    }

    fun deleteObject(shopping: Shopping) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(shopping)
    }

    fun updateObject(shopping: Shopping) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(shopping)
    }

}