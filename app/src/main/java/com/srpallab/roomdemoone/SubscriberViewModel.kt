package com.srpallab.roomdemoone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srpallab.roomdemoone.db.Subscriber
import com.srpallab.roomdemoone.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(
    private val subscriberRepository: SubscriberRepository
    ) : ViewModel() {
    val subscribers = subscriberRepository.subscribers
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputEmail.value = ""
        inputName.value = ""
    }

    fun clearOrDelete(){
        clearAll()
    }

    fun insert( subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.insert(subscriber)
    }

    fun update( subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.update(subscriber)
    }

    fun delete( subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.delete(subscriber)
    }

    fun clearAll() =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.deleteAll()
    }
}