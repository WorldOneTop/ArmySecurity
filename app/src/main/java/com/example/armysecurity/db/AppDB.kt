package com.example.armysecurity.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.armysecurity.data.*

@Database(entities = [Cemetery::class,Relics::class, Sale::class], version = 1)
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
    fun insertCemetery(users: List<Cemetery>)

    @Query("DELETE FROM Cemetery")
    suspend fun deleteCemetery()


    @Query("SELECT * FROM Cemetery ORDER BY name ASC")
    fun selectCemetery(): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE name LIKE :value ORDER BY name ASC")
    fun selectCemeteryName(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE rank LIKE :value ORDER BY name ASC")
    fun selectCemeteryRank(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE identity LIKE :value ORDER BY name ASC")
    fun selectCemeteryIdentity(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE moveDate LIKE :value ORDER BY name ASC")
    fun selectCemeteryMoveDate(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE movePlc LIKE :value ORDER BY name ASC")
    fun selectCemeteryMovePlc(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE deathDate LIKE :value ORDER BY name ASC")
    fun selectCemeteryDeathDate(value:String): PagingSource<Int, Cemetery>
    @Query("SELECT * FROM Cemetery WHERE deathPlc LIKE :value ORDER BY name ASC")
    fun selectCemeteryDeathPlc(value:String): PagingSource<Int, Cemetery>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRelics(data: List<Relics>)
    @Query("DELETE FROM Relics")
    suspend fun deleteRelics()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSale(data: List<Sale>)
    @Query("DELETE FROM Sale")
    suspend fun deleteSale()
}