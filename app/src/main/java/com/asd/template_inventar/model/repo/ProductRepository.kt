package com.asd.template_inventar.model.repo

import android.util.Log
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.repo.localrepo.LocalDatabase
import com.asd.template_inventar.model.repo.localrepo.LocalEntitiesRepository
import com.asd.template_inventar.MainActivity
import com.asd.template_inventar.model.service.ProductService
import com.asd.template_inventar.utils.InternetStatus
import com.asd.template_inventar.utils.InternetStatusLive

interface ProductRepository {
    suspend fun getAll(): List<Product>
    suspend fun add(entity: Product): Product
    suspend fun reconnection(): List<Product>
}

class BaseProductRepository @Inject constructor(
    private val service: ProductService
) : ProductRepository {
    private var retrieved = false
    private val localRepo = LocalEntitiesRepository(LocalDatabase.getDatabase(MainActivity.bcontext).entityDao())

    override suspend fun getAll(): List<Product> {
        return localRepo.getAll()
    }

    override suspend fun add(product: Product): Product {
        if(InternetStatusLive.status.value?.equals(InternetStatus.OFFLINE) == true) {
            Log.d("Debug","Local add: $product")

            product.isLocal = true
        } else {
            Log.d("Debug","Remote+local add: $product")

            val saved = service.add(product)
            product.id = saved.id
        }

        return localRepo.save(product)
    }

    override suspend fun reconnection(): List<Product> {
        localRepo.getAll().forEach { product ->
            if(product.isLocal) {
                try {
                    Log.d("Debug","Sync to remote: $product")
                    service.add(product)
                } catch (ex: HttpException) {
                    Log.d("Debug","Failed to add $product")
                }
            }
        }

        var entities = listOf<Product>()

        try {
            entities = service.getAll()
            entities.forEach {
                if(!localRepo.exists(it.id)) {
                    Log.d("Debug","Sync from remote: $it")
                    localRepo.save(it)
                }
            }
            retrieved = true
        } catch (ex: ConnectException) {
            Log.d("Debug", "Connection exception: " + ex.message.toString())
        }

        return entities
    }
}