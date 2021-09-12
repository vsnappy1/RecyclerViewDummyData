package com.vishal.recyclerviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.vishalkumar.recyclerviewdummydata.AnimationType
import com.vishalkumar.recyclerviewdummydata.RecyclerViewDummyData
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var recyclerViewDummyData: RecyclerViewDummyData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Find view by id
        recyclerView = findViewById(R.id.recyclerView)
        //Initialize the real adapter
        productAdapter = ProductAdapter()

        // Create an instance of RecyclerViewDummyData with the Builder class
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            // set the dummy layout resource id
            .setDummyViewResourceId(R.layout.item_product_dummy)
            // Optional you can use you own custom animation, make sure its repeatCount property is set to infinity
            .setAnimationResourceId(R.anim.custom_animtion)
            // Optional you can select between different animation types, default value is AnimationType.FADE_IN_FADE_OUT
            .setAnimationType(AnimationType.SHIMMER)
            // Optional you can set it dummy item count, default value is 6
            .setItemCount(4)
            // Optional you can set the animation speed, default value is 2.5
            .setAnimationSpeed(5f)
            // Optional you can set the shimmer width, default value is 50
            .setShimmerWidth(70)
            // This build method builds the RecyclerViewDummyData instance with given configuration
            .build()

        // This populates the recycler view with dummy data
        recyclerViewDummyData?.loadDummyData(this, recyclerView)
        fakeNetworkCall()
    }

    private fun fakeNetworkCall() {
        Timer().schedule(timerTask {
            runOnUiThread {
                recyclerView.adapter = productAdapter
            }
        }, 20000)
    }
}