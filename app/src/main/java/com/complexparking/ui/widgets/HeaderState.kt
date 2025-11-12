package com.complexparking.ui.widgets

import com.complexparking.R
import com.complexparking.entities.UserData

data class HeaderState(
    val userData: UserData = UserData()
) {
    fun getUserTypeId(): Int {
        return if(userData.isAdmin) {
            R.string.home_screen_header_profile_admin
        } else{
            R.string.home_screen_header_profile_security_guard
        }
    }
}