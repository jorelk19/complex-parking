package com.complexparking.ui.utilities

enum class ErrorType(val value: String) {
    WRONG_USER_PASSWORD("WRONG_USER_PASSWORD"),
    INVALID_EMAIL("INVALID_EMAIL"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    EMPTY_UNIT("EMPTY_UNIT"),
    INVALID_UNIT("INVALID_UNIT"),
    EMPTY_PLATE("EMPTY_PLATE"),
    INVALID_PLATE("INVALID_PLATE"),
    NONE("NONE");

    companion object {
        fun getValue(value: String): ErrorType? {
            return ErrorType.entries.firstOrNull { value == it.value }
        }
    }
}