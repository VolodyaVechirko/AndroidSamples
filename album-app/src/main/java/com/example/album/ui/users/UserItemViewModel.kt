package com.example.album.ui.users

import com.example.album.core.ItemViewModel
import com.example.album.data.UserModel

class UserItemViewModel(
    val user: UserModel,
    val onClick: () -> Unit
) : ItemViewModel {
    val address: String = with(user.address) {
        "$city, $street st. $suite; $zipcode"
    }

    override fun isSameItemAs(other: ItemViewModel): Boolean {
        return other is UserItemViewModel && user.id == other.user.id
    }

    override fun isSameContentAs(other: ItemViewModel): Boolean {
        return other is UserItemViewModel && user == other.user
    }
}
