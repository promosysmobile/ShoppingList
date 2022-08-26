package com.nabilla.shoppinglist.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nabilla.shoppinglist.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getShoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = Shopping("Title",5.00,123456789,0,id = 1)
        dao.insert(shoppingItem)

        val allShoppingList = dao.getAllShoppingList().getOrAwaitValue()

        assertThat(allShoppingList).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = Shopping("Title",5.00,123456789,0,id = 2)
        dao.insert(shoppingItem)
        dao.delete(shoppingItem)

        val allShoppingList = dao.getAllShoppingList().getOrAwaitValue()

        assertThat(allShoppingList).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val shoppingItem1 = Shopping("Title1",5.00,123456789,0,id = 1)
        val shoppingItem2 = Shopping("Title2",5.00,123456789,0,id = 2)
        val shoppingItem3 = Shopping("Title3",5.00,123456789,0,id = 3)

        dao.insert(shoppingItem1)
        dao.insert(shoppingItem2)
        dao.insert(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(5.00+5.00+5.00)
    }

    @Test
    fun getCheckedList() = runTest {
        val shoppingItem = Shopping("Title",5.00,123456789,1,id = 1)
        dao.insert(shoppingItem)

        val allCheckedList = dao.getAllCheckedItem().getOrAwaitValue()
        val allUncheckList = dao.getAllUncheckItem().getOrAwaitValue()

        assertThat(allCheckedList).contains(shoppingItem)
        assertThat(allUncheckList.size).isEqualTo(0)

    }

    @Test
    fun getUncheckedList() = runTest {
        val shoppingItem = Shopping("Title",5.00,123456789,0,id = 1)
        dao.insert(shoppingItem)

        val allCheckedList = dao.getAllCheckedItem().getOrAwaitValue()
        val allUncheckList = dao.getAllUncheckItem().getOrAwaitValue()

        assertThat(allUncheckList).contains(shoppingItem)
        assertThat(allCheckedList.size).isEqualTo(0)
    }

    @Test
    fun testCheckItem() = runTest {
        val shoppingItem = Shopping("Title",5.00,123456789,0,id = 1)
        dao.insert(shoppingItem)

        val allUncheckList = dao.getAllUncheckItem().getOrAwaitValue()
        allUncheckList[0].isSelected = 1
        dao.update(allUncheckList[0])

        val allCheckedList = dao.getAllCheckedItem().getOrAwaitValue()
        assertThat(allCheckedList).contains(allUncheckList[0])
    }

}