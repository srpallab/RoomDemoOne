package com.srpallab.roomdemoone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srpallab.roomdemoone.db.Subscriber
import com.srpallab.roomdemoone.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(
    private val subscriberRepository: SubscriberRepository
    ) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber
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
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputEmail.value = ""
            inputName.value = ""
        }
    }

    fun clearOrDelete(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    private fun insert(subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
            subscriberRepository.insert(subscriber)
    }

    fun update( subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.update(subscriber)
        withContext(Dispatchers.Main) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    private fun delete(subscriber: Subscriber ) =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.delete(subscriber)
        withContext(Dispatchers.Main) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }
    }

    private fun clearAll() =  viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.deleteAll()
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }
}