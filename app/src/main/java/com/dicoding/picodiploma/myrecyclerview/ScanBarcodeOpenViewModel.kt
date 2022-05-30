package com.dicoding.picodiploma.myrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class ScanBarcodeOpenViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun getAlamat(alamat: String) {
        viewModelScope.launch {
            pref.getAlamat(alamat)
        }
    }
}