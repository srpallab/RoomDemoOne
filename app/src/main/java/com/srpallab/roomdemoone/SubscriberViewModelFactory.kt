package com.srpallab.roomdemoone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srpallab.roomdemoone.db.Subscriber
import com.srpallab.roomdemoone.db.SubscriberRepository

class SubscriberViewModelFactory(
    private val subscriberRepository: SubscriberRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(subscriberRepository) as T
        }
        throw  IllegalAccessException("Unknown ViewModel Class")
    }
}