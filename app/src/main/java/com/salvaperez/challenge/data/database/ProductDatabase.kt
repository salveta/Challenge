package com.salvaperez.challenge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salvaperez.challenge.data.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "products-db"
        ).build()
    }

    abstract fun productDao(): ProductDao
}