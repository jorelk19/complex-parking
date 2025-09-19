package com.complexparking.ui.base


enum class EnumNavigationScreen(var value: String) {
    HOME_SCREEN("HOME_SCREEN"),
    LOGIN_SCREEN("LOGIN_SCREEN"),
    SPLASH_SCREEN("SPLASH_SCREEN");

    companion object {
        fun getName(value: String): EnumNavigationScreen? {
            return EnumNavigationScreen.entries.firstOrNull { it.value == value }
        }
    }
}