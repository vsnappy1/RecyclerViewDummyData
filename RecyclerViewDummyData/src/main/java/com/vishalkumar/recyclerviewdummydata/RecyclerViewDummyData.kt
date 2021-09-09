package com.vishalkumar.recyclerviewdummydata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDummyData(private var context: Context) {
    companion object {
        private const val DEFAULT_ITEM_COUNT = 6
        private var DEFAULT_ANIMATION_RESOURCE_ID = R.anim.fade_in_fade_out_animation
    }

    fun setDummyData(
        recyclerView: RecyclerView,
        viewResourceId: Int,
        itemCount: Int = DEFAULT_ITEM_COUNT,
        animationResourceId: Int = DEFAULT_ANIMATION_RESOURCE_ID
    ) {
        try {
            context.resources.getLayout(viewResourceId)
            context.resources.getAnimation(animationResourceId)
            recyclerView.adapter =
                DummyDataAdapter(context, viewResourceId, itemCount, animationResourceId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private inner class DummyDataAdapter(
        private var context: Context,
        private var viewResourceId: Int,
        private var itemCount: Int,
        private var animationResourceId: Int
    ) :
        RecyclerView.Adapter<DummyDataAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(viewResourceId, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onViewAttachedToWindow(holder: ViewHolder) {
            super.onViewAttachedToWindow(holder)
            holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(
                    context,
                    animationResourceId
                )
            )
        }

        override fun onViewDetachedFromWindow(holder: ViewHolder) {
            super.onViewDetachedFromWindow(holder)
            holder.itemView.clearAnimation()
        }
    }
}
