package com.complexparking.data.repository.local.converters

import androidx.room.TypeConverter
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.google.gson.Gson
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromBrandDtoObject(value: BrandDto?): String? {
        // Convert MyCustomObject to a String (e.g., JSON serialization)
        return value?.let{ Gson().toJson(value) }
    }

    @TypeConverter
    fun toBrandDtoObject(value: String?): BrandDto? {
        return value?.let { Gson().fromJson(it, BrandDto::class.java) }
    }

    @TypeConverter
    fun fromCarDtoObject(value: CarDto): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCarDtoObject(value: String?): CarDto? {
        // Convert String back to MyCustomObject (e.g., JSON deserialization)
        return value?.let { Gson().fromJson(it, CarDto::class.java) }
    }

    @TypeConverter
    fun fromDocumentTypeDtoObject(value: DocumentTypeDto): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDocumentTypeDtoObject(value: String?): DocumentTypeDto? {
        // Convert String back to MyCustomObject (e.g., JSON deserialization)
        return value?.let { Gson().fromJson(it, DocumentTypeDto::class.java) }
    }

    @TypeConverter
    fun fromPersonDtoObject(value: PersonDto): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPersonDtoObject(value: String?): PersonDto? {
        // Convert String back to MyCustomObject (e.g., JSON deserialization)
        return value?.let { Gson().fromJson(it, PersonDto::class.java) }
    }
}