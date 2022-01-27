package com.asd.template_inventar.model.usecase

import javax.inject.Inject
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.repo.ProductRepository

interface AddProductsUseCase {
    suspend operator fun invoke(entity: Product): Product
}

class BaseAddProductsUseCase @Inject constructor(
    private val repo: ProductRepository
) : AddProductsUseCase {
    override suspend fun invoke(product: Product): Product {
        return repo.add(product)
    }
}