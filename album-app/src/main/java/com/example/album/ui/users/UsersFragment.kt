package com.example.album.ui.users

import android.content.Context
import androidx.fragment.app.viewModels
import com.example.album.R
import com.example.album.core.CoreFragment
import com.example.album.databinding.ActivityUsersBinding

class UsersFragment : CoreFragment<UsersViewModel, ActivityUsersBinding>() {
    override val viewModel: UsersViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_users

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.init()
    }
}