package com.srpallab.roomdemoone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.srpallab.roomdemoone.databinding.ActivityMainBinding
import com.srpallab.roomdemoone.db.Subscriber
import com.srpallab.roomdemoone.db.SubscriberDatabase
import com.srpallab.roomdemoone.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(
            this, factory
        )[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(
            this,
            Observer {
                // Log.i("Subscriber", it.toString())
                binding.subscriberRecyclerView.adapter = MyRecyclerViewAdapter(
                    it
                ) { selectedItem: Subscriber -> listItemClicked(selectedItem) }
            }
        )
    }

    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(
            this, "Selected Name is ${subscriber.name}", Toast.LENGTH_LONG
        ).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}