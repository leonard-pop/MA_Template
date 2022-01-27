package com.asd.template_inventar.model.usecase

import javax.inject.Inject
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.repo.ProductRepository

interface SyncUseCase {
    suspend operator fun invoke(): List<Product>
}

class BaseSyncUseCase @Inject constructor(
    private val repo: ProductRepository
) : SyncUseCase {
    override suspend fun invoke(): List<Product> {
        return repo.reconnection()
    }
}