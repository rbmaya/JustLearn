package ru.justlearn.ui.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import java.io.Serializable

fun NavController.navigate(route: String, param: Pair<String, Serializable>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    val args = Bundle().apply {
        param?.let { putSerializable(param.first, param.second)  }
    }

    navigate(route, builder)
}

fun NavController.navigate(route: String, params: List<Pair<String, Serializable>>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    params?.let {
        val arguments = this.currentBackStackEntry?.arguments
        params.forEach { arguments?.putSerializable(it.first, it.second) }
    }

    navigate(route, builder)
}

fun NavController.navigate(route: String, params: Bundle?, builder: NavOptionsBuilder.() -> Unit = {}) {
    this.currentBackStackEntry?.arguments?.putAll(params)

    navigate(route, builder)
}

