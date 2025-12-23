package com.thejan.workforceschedule.core.data.local.mock

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetJsonReader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun read(path: String): String =
        context.assets.open(path).bufferedReader().use { it.readText() }
}