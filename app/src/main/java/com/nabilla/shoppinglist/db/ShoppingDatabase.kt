package com.nabilla.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Shopping::class], version = 1)
abstract class ShoppingDatabase:RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao

    companion object {

        @Volatile
        private var INSTANCE: ShoppingDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context:Context): ShoppingDatabase {
            //if INSTANCE is not null, then return it
            //if INSTANCE is null, create the database
            return INSTANCE ?: synchronized(LOCK) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_db.db"
                ).build()
                INSTANCE = instance

                //return instance
                instance
            }
        }

    }

}