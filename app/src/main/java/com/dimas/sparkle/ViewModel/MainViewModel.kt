package com.dimas.sparkle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dimas.sparkle.Model.UserModel
import com.dimas.sparkle.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}