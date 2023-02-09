package com.example.pkart.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModul::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var dataBase: AppDatabase? = null

        private var DATABASE_NAME = "pkart"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
            }
            return dataBase!!
        }
    }

    abstract fun productDao(): ProductDao

}