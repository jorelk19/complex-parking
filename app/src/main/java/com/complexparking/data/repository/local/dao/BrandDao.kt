package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.BrandDto

@Dao
interface BrandDao {
    @Upsert
    suspend fun insertBrand(brandDto: BrandDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrandList(brandList: List<BrandDto>)

    @Query("SELECT * FROM brand")
    suspend fun getBrandList() : List<BrandDto>
}