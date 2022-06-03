package com.dimas.sparkle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.sparkle.ViewModel.MainViewModel
import com.dimas.sparkle.ViewModel.ScanBarcodeExitViewModel
import com.dimas.sparkle.ViewModel.ScanBarcodeOpenViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ScanBarcodeOpenViewModel::class.java) -> {
                ScanBarcodeOpenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ScanBarcodeExitViewModel::class.java) -> {
                ScanBarcodeExitViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}