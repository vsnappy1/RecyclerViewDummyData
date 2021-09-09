package com.vishal.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.vishalkumar.recyclerviewdummydata.RecyclerViewDummyData
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerViewDummyData: RecyclerViewDummyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Find view by id
        recyclerView = findViewById(R.id.recyclerView)
        //Initialize the real adapter
        productAdapter = ProductAdapter()
        //Initialize the RecyclerViewDummyData object
        recyclerViewDummyData = RecyclerViewDummyData(this)

        //sets the dummy data into the recycler view
        //First parameter to setDummyData method is the RecyclerView
        //Second parameter to setDummyData method is the Id of the dummy layout
        //Third(optional, default value is 6) parameter to setDummyData method is the number of items
        //Fourth(optional) parameter to setDummyData method is the Id of repeating animation
        recyclerViewDummyData.setDummyData(recyclerView, R.layout.item_product_dummy)
        fakeNetworkCall()
    }

    private fun fakeNetworkCall() {
        Timer().schedule(timerTask {
            runOnUiThread {
                recyclerView.adapter = productAdapter
            }
        }, 5000)
    }
}