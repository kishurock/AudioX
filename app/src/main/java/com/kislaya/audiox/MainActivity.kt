package com.kislaya.audiox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    lateinit var sliderLayout: SliderLayout
    lateinit var viewPager2: ViewPager2
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var layoutManager2: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: SongRecyclerAdapter
    lateinit var recyclerViewOne: RecyclerView
    lateinit var recyclerViewTwo: RecyclerView

    val sliderList = arrayListOf<SliderItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2 = findViewById(R.id.viewPagerImageSlider)
        sliderLayout = findViewById(R.id.imageSlider)
        recyclerViewOne = findViewById(R.id.recyclerViewOne)
        recyclerViewTwo = findViewById(R.id.recyclerViewTwo)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL)
        sliderLayout.scrollTimeInSec = 2
        setSliderViews()

        sliderList.add(SliderItem(R.drawable.one, "Money On Mind", R.raw.song))
        sliderList.add(SliderItem(R.drawable.two, "Caroline", R.raw.song))
        sliderList.add(SliderItem(R.drawable.three, "Lucky You", R.raw.song))
        sliderList.add(SliderItem(R.drawable.four, "Lucus Fenics", R.raw.song))
        sliderList.add(SliderItem(R.drawable.five, "Malibu", R.raw.song))

        recyclerAdapter = SongRecyclerAdapter(this@MainActivity, sliderList)
        recyclerViewOne.layoutManager = layoutManager
        recyclerViewOne.adapter = recyclerAdapter

        recyclerViewTwo.layoutManager = layoutManager2
        recyclerViewTwo.adapter = recyclerAdapter

        viewPager2.adapter = SliderAdapter(this@MainActivity, sliderList, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { view: View, fl: Float ->
            val r = 1 - abs(fl)
            view.scaleY = 0.85f + r * 0.15f

        }
        viewPager2.setPageTransformer(compositePageTransformer)
    }

    private fun setSliderViews() {
        for (i in 0 until 5) {
            val sliderView = DefaultSliderView(this@MainActivity)
            when (i) {
                0 -> {
                    sliderView.setImageDrawable(R.drawable.one)
                }
                1 -> {
                    sliderView.setImageDrawable(R.drawable.two)
                }
                2 -> {
                    sliderView.setImageDrawable(R.drawable.three)
                }
                3 -> {
                    sliderView.setImageDrawable(R.drawable.four)
                }
                4 -> {
                    sliderView.setImageDrawable(R.drawable.five)
                }

            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderLayout.addSliderView(sliderView)
        }
    }
}
