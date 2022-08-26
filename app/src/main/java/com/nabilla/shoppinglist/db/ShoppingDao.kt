package com.nabilla.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingObj: Shopping)

    @Delete
    suspend fun delete(shoppingObj: Shopping)

    @Update
    suspend fun update(shoppingObj: Shopping)

    @Query("Select * from shopping_list order by id ASC")
    fun getAllShoppingList(): LiveData<List<Shopping>>

    @Query("Select * from shopping_list where selected = '0'")
    fun getAllUncheckItem(): LiveData<List<Shopping>>

    @Query("Select * from shopping_list where selected = '1'")
    fun getAllCheckedItem(): LiveData<List<Shopping>>

    @Query("SELECT SUM(price) FROM shopping_list")
    fun observeTotalPrice(): LiveData<Double>

}