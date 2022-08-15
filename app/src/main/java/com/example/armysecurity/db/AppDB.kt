package com.example.armysecurity.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.armysecurity.data.*

@Database(entities = [Cemetery1::class,Cemetery2::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun dbDao(): DBDao

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
    fun insertCemetery1(users: List<Cemetery1>)
    @Insert
    fun insertCemetery2(users: List<Cemetery2>)

    @Query("DELETE FROM Cemetery2")
    suspend fun deleteCemetery2()
    @Query("DELETE FROM Cemetery1")
    suspend fun deleteCemetery1()

}