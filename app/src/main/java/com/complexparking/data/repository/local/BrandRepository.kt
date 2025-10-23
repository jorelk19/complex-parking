package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.BrandDto

class BrandRepository(
    private val db: ParkingDatabase
): IBrandRepository {
    override suspend fun getBrandList(): List<BrandDto> {
        return db.brandDao.getBrandList()
    }

    override suspend fun insertBrandList(brandList: List<BrandDto>) {
        db.brandDao.insertBrandList(brandList)
    }
}