package com.jamessc94.autocompletetvj.DB

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface Dao_search {

    @Query("Select * FROM search WHERE category = :cate")
    suspend fun getCateSearch(cate : String) : List<D_search>

    @Query("Select * FROM search WHERE category = :cate AND title = :title")
    suspend fun getCateTitleSearch(cate : String, title : String) : List<D_search>

    @Query("Select * FROM search")
    suspend fun getAllSearch() : List<D_search>

    @Query("Select * FROM search WHERE category = :cate ORDER BY created_at DESC")
    fun obsvCateSearch(cate : String) : LiveData<List<D_search>>

    @Query("Select * FROM search ORDER BY created_at DESC")
    fun obsvAllSearch() : LiveData<List<D_search>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(ds : D_search)

    @Query("UPDATE search SET created_at = :date, extra = :extra WHERE id = :id")
    suspend fun updateSearch(id : Long, date : String, extra : String)

    @Query("DELETE FROM search WHERE category = :cate AND title = :title")
    suspend fun deleteSearch(cate : String, title : String)

    @Query("DELETE FROM search WHERE id = :id")
    suspend fun deleteSearch(id : Long)

    @Query("DELETE FROM search WHERE category = :cate")
    suspend fun deleteSearchAll(cate : String)

    @Query("DELETE FROM search")
    suspend fun deleteSearchAll()

}