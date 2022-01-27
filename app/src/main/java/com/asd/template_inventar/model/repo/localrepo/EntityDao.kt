package com.asd.template_inventar.model.repo.localrepo

import androidx.room.*
import com.asd.template_inventar.model.domain.Product

@Dao
interface EntityDao {
    @Query("select COUNT(*) from entity where id= :id")
    fun find(id: Int): Int

    @Query("select * from entity")
    fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Product) : Long

    @Query("delete from entity")
    fun deleteAll()
}