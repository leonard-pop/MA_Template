package com.asd.template_inventar.model.repo.localrepo

import android.util.Log
import javax.inject.Inject
import com.asd.template_inventar.model.domain.Product

class LocalEntitiesRepository @Inject constructor(
    private val entityDao: EntityDao
) {

    fun getAll(): List<Product> {
        return entityDao.getAll()
    }

    fun save(entity: Product): Product {
        entityDao.save(entity)
        Log.d("Debug","Local DB add: $entity")

        return entity
    }

    fun exists(id: Int): Boolean {
        return (entityDao.find(id) > 0)
    }

    fun deleteAll() {
        entityDao.deleteAll()
    }
}