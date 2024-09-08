package ru.justlearn.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {

    @Suppress("DEPRECATION")
    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            bundle.getParcelable(key)
        }

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putParcelable(key, value)

    override fun parseValue(value: String): T {
        val decodedUri = Uri.decode(value)
        return Json.decodeFromString(serializer, decodedUri)
    }

    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(serializer, value).let {
            // it's workaround because Jetpack Navigation uses Uri.parse()
            // under the hood but not encode special characters
            Uri.encode(it)
        }
    }

    override val name: String = clazz.name
}