package com.example.armysecurity.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.armysecurity.data.Cemetery

@Database(entities = [Cemetery::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): DBDao

    companion object{
        @Volatile private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java, "myDB"
                ).build()
            }
        }
    }
}

@Dao
interface DBDao {
    @Insert
    fun insertAll(vararg users: Cemetery)

}