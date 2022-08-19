package com.example.armysecurity.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.armysecurity.data.*

@Database(entities = [Cemetery::class,Relics::class, Sale::class, War::class, WarMan::class], version = 1)
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
    @Query("SELECT * FROM Relics ORDER BY ttl ASC")
    fun selectRelics(): PagingSource<Int, Relics>
    @Query("SELECT * FROM Relics WHERE ttl LIKE :value ORDER BY ttl ASC")
    fun selectRelicsTtl(value: String): PagingSource<Int, Relics>
    @Query("SELECT * FROM Relics WHERE natlthourperiod_1_ttl_1 LIKE :value ORDER BY ttl ASC")
    fun selectRelicsPlace(value: String): PagingSource<Int, Relics>
    @Query("SELECT * FROM Relics WHERE obtmplace LIKE :value ORDER BY ttl ASC")
    fun selectRelicsObtmPlc(value: String): PagingSource<Int, Relics>
    @Query("SELECT * FROM Relics WHERE relafr LIKE :value ORDER BY ttl ASC")
    fun selectRelicsRelafr(value: String): PagingSource<Int, Relics>
    @Query("SELECT * FROM Relics WHERE wrtmyn LIKE :value ORDER BY ttl ASC")
    fun selectRelicsWrtmyn(value: String): PagingSource<Int, Relics>




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSale(data: List<Sale>)
    @Query("DELETE FROM Sale")
    suspend fun deleteSale()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWar(data: List<War>)
    @Query("DELETE FROM War")
    suspend fun deleteWar()
    @Query("SELECT * FROM War ORDER BY title ASC")
    fun selectWar(): PagingSource<Int, War>
    @Query("SELECT * FROM War WHERE title LIKE :value ORDER BY title ASC")
    fun selectWarName(value: String): PagingSource<Int, War>
    @Query("SELECT * FROM War WHERE place LIKE :value ORDER BY title ASC")
    fun selectWarPlace(value: String): PagingSource<Int, War>
    @Query("SELECT * FROM War WHERE man LIKE :value ORDER BY title ASC")
    fun selectWarOrder(value: String): PagingSource<Int, War>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWarMan(data: List<WarMan>)
    @Query("DELETE FROM WarMan")
    suspend fun deleteWarMan()
    @Query("SELECT * FROM WarMan ORDER BY title ASC")
    fun selectWarMan(): PagingSource<Int, WarMan>
    @Query("SELECT * FROM WarMan WHERE title LIKE :value ORDER BY title ASC")
    fun selectWarManName(value: String): PagingSource<Int, WarMan>
    @Query("SELECT * FROM WarMan WHERE born LIKE :value ORDER BY title ASC")
    fun selectWarManPlace(value: String): PagingSource<Int, WarMan>
    @Query("SELECT * FROM WarMan WHERE rank LIKE :value ORDER BY title ASC")
    fun selectWarManRank(value: String): PagingSource<Int, WarMan>
    @Query("SELECT * FROM WarMan WHERE award LIKE :value ORDER BY title ASC")
    fun selectWarManAward(value: String): PagingSource<Int, WarMan>

}