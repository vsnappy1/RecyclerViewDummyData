package com.vishalkumar.recyclerviewdummydata

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.annotation.AnimRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDummyData private constructor(
    private val dummyViewResourceId: Int,
    private val itemCount: Int,
    private val customAnimationResourceId: Int,
    private val animationType: AnimationType,
    private val animationSpeed: Float,
    private val shimmerWidth: Int,
) {
    companion object {
        private const val TAG = "RecyclerViewDummyData"
        private val DEFAULT_DUMMY_LAYOUT_RESOURCE_ID = R.layout.default_dummy_layout
        private const val DEFAULT_ITEM_COUNT = 6
        private const val DEFAULT_ANIMATION_SPEED = 2.5f
        private const val DEFAULT_SHIMMER_HEIGHT_MULTIPLE = 3
        private const val DEFAULT_SHIMMER_ELEVATION = 0f
        private const val DEFAULT_SHIMMER_ROTATION = 45f
        private const val DEFAULT_SHIMMER_WIDTH = 50
    }

    fun loadDummyData(context: Context?, recyclerView: RecyclerView?) {
        try {
            when {
                context == null -> throw Exception("Context can not be null")
                recyclerView == null -> throw Exception("RecyclerView can not be null")

                else -> recyclerView.adapter =
                    DummyDataAdapter(
                        context,
                        dummyViewResourceId,
                        itemCount,
                        customAnimationResourceId,
                        animationType,
                        animationSpeed,
                        shimmerWidth
                    )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "ERROR: ${e.message}")
        }
    }

    private inner class DummyDataAdapter(
        private val context: Context,
        private val viewResourceId: Int,
        private val itemCount: Int,
        private val customAnimationResourceId: Int,
        private val animationType: AnimationType,
        private val animationSpeed: Float,
        private val shimmerWidth: Int
    ) :
        RecyclerView.Adapter<DummyDataAdapter.ViewHolder>() {

        private var max: Float = 0.0f
        private var shimmerHeight: Int = 0
        private var animationDuration: Long = 0L
        private var animation: Animation? = null

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(viewResourceId, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onViewAttachedToWindow(holder: ViewHolder) {
            super.onViewAttachedToWindow(holder)
            when (animationType) {
                AnimationType.CUSTOM -> startCustomAnimation(holder.itemView)
                AnimationType.FADE_IN_FADE_OUT -> startFadeAnimation(holder.itemView)
                AnimationType.SHIMMER -> {
                    /*Since we need to know the size of item before we can add shimmer effect to it therefore
                        we wait for the item to be loaded and displayed on screen then we start the animation
                         */
                    holder.itemView.post {
                        startShimmerAnimation(holder.itemView)
                    }
                }
            }
        }

        override fun onViewDetachedFromWindow(holder: ViewHolder) {
            super.onViewDetachedFromWindow(holder)
            holder.itemView.clearAnimation()
        }

        private fun startCustomAnimation(view: View) {
            view.startAnimation(
                AnimationUtils.loadAnimation(
                    context,
                    customAnimationResourceId
                )
            )
        }

        private fun startFadeAnimation(view: View) {
            if (animation == null) {
                animationDuration = (1000 / animationSpeed).toLong()
                animation = AlphaAnimation(1f, 0.5f)
                animation!!.duration = animationDuration
                animation!!.repeatCount = Animation.INFINITE
                animation!!.repeatMode = Animation.REVERSE
            }
            animation!!.start()
            val group = view as ViewGroup
            group.addView(View(context))
            group.animation = animation
        }

        private fun startShimmerAnimation(view: View) {
            if (animation == null) {
                max = maxOf(view.width.toFloat(), view.height.toFloat())
                shimmerHeight = (max * DEFAULT_SHIMMER_HEIGHT_MULTIPLE).toInt()
                animationDuration = (max * 10 / animationSpeed).toLong()
                animation = TranslateAnimation(
                    -shimmerWidth.toFloat(),
                    max + 2 * shimmerWidth.toFloat(),
                    -shimmerWidth.toFloat(),
                    max + 2 * shimmerWidth.toFloat()
                )
                animation!!.duration = animationDuration
                animation!!.repeatCount = Animation.INFINITE
            }
            animation!!.start()
            val group = view as ViewGroup
            val shimmer = getShimmerView()
            shimmer.animation = animation
            group.addView(shimmer)
        }

        private fun getShimmerView(): View {
            val shimmer = View(context)
            shimmer.setBackgroundResource(R.drawable.shimmer_background)
            shimmer.layoutParams = FrameLayout.LayoutParams(shimmerWidth, shimmerHeight)
            shimmer.y = (-1 * shimmerHeight / 2).toFloat()
            shimmer.rotation = DEFAULT_SHIMMER_ROTATION
            shimmer.elevation = DEFAULT_SHIMMER_ELEVATION
            return shimmer
        }
    }

    class Builder {
        private var dummyViewResourceId: Int = DEFAULT_DUMMY_LAYOUT_RESOURCE_ID
        private var itemCount: Int = DEFAULT_ITEM_COUNT
        private var customAnimationResourceId: Int = -1
        private var animationType: AnimationType = AnimationType.FADE_IN_FADE_OUT
        private var shimmerWidth: Int = DEFAULT_SHIMMER_WIDTH
        private var animationSpeed: Float = DEFAULT_ANIMATION_SPEED

        fun setDummyViewResourceId(@LayoutRes dummyViewResourceId: Int): Builder {
            this.dummyViewResourceId = dummyViewResourceId
            return this
        }

        fun setItemCount(itemCount: Int): Builder {
            this.itemCount = itemCount
            return this
        }

        fun setCustomAnimationResourceId(@AnimRes animationResourceId: Int): Builder {
            this.customAnimationResourceId = animationResourceId
            return this
        }

        fun setAnimationType(animationType: AnimationType): Builder {
            this.animationType = animationType
            return this
        }

        fun setAnimationSpeed(animationSpeed: Float): Builder {
            this.animationSpeed = animationSpeed
            return this
        }

        fun setShimmerWidth(shimmerWidth: Int): Builder {
            this.shimmerWidth = shimmerWidth
            return this
        }

        fun build(): RecyclerViewDummyData? {
            try {
                when {
                    animationType == AnimationType.CUSTOM && customAnimationResourceId == -1 -> throw Exception("No custom animation resource id provided")
                    itemCount < 0 -> throw Exception("Item count can not be zero or negative")
                    animationSpeed < 0 -> throw Exception("Shimmer speed can not be zero or negative")
                    shimmerWidth < 0 -> throw Exception("Shimmer width can not be zero or negative")
                    else -> return RecyclerViewDummyData(
                        dummyViewResourceId,
                        itemCount,
                        customAnimationResourceId,
                        animationType,
                        animationSpeed,
                        shimmerWidth,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "ERROR: ${e.message}")
            }
            return null
        }
    }
}
