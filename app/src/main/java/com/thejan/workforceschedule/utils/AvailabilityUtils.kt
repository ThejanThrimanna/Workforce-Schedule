package com.thejan.workforceschedule.utils

import com.thejan.workforceschedule.features.employees.data.models.AvailabilitySlot

object AvailabilityUtils {
    fun parseAvailability(json: String): List<AvailabilitySlot> {
        return try {
            val type = object : com.google.gson.reflect.TypeToken<List<AvailabilitySlot>>() {}.type
            com.google.gson.Gson().fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun mergeEmployeeIds(
        existing: String?,
        newIds: List<String>
    ): String {
        val existingSet = existing
            ?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?.toSet()
            ?: emptySet()

        val merged = existingSet + newIds

        return merged.joinToString(",")
    }
}