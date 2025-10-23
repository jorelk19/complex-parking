package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.BrandDto

interface IBrandRepository {
    suspend fun getBrandList(): List<BrandDto>
    suspend fun insertBrandList(brandList: List<BrandDto>)
}