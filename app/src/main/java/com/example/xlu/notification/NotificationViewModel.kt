package com.example.xlu.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel: ViewModel() {

    private val _selectedNotification = MutableLiveData<Notification>()
    val selectedNotification: LiveData<Notification> = _selectedNotification

    fun handleNotificationMessage(message: Notification) {
        _selectedNotification.postValue(message)
    }

}