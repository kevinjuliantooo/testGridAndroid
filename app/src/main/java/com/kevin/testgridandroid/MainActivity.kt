package com.kevin.testgridandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: Adapter
    private lateinit var compositeDisposable: CompositeDisposable

    private var currentItemShown: Int = 0
    private var totalIteminAdapter: Int = 0
    private var scrolledOutOfScreen: Int = 0

    private var isScrolling: Boolean = false
    private var totalPage: Int = 1

    companion object {
        var BASE_URL = "https://picsum.photos/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        gridLayoutManager = GridLayoutManager(this, 3)

        adapter = Adapter()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayoutManager

        compositeDisposable = CompositeDisposable()

        addDataToAdapter(totalPage)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {

                if (BuildConfig.FLAVOR.equals("normal")) {
                    Toast.makeText(applicationContext, "Author: " + data.author, Toast.LENGTH_SHORT).show()


                } else {
                    tv_title.visibility = View.GONE
                    tv_description.visibility = View.GONE

                    Glide.with(applicationContext).load(data.download_url).into(displayedImage)
                    tv_author.text = data.author

                    displayedImage.visibility = View.VISIBLE
                    tv_author.visibility = View.VISIBLE
                }



            }

        })

        recyclerView.addOnScrollListener( object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }

            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItemShown = gridLayoutManager.childCount
                totalIteminAdapter = gridLayoutManager.itemCount
                Log.d("Total Item In Adapter", totalIteminAdapter.toString())
                scrolledOutOfScreen = gridLayoutManager.findFirstVisibleItemPosition()

                if (isScrolling && (currentItemShown + scrolledOutOfScreen == totalIteminAdapter)){

                    isScrolling = false

                    totalPage += 1

                    fetchData(totalPage)
                }


            }
        })

    }

    private fun fetchData(totalPage: Int) {
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed( object : Runnable {
            override fun run() {

                addDataToAdapter(totalPage)

                progressBar.visibility = View.GONE
            }

        }, 5000)
    }

    private fun addDataToAdapter(totalPage: Int) {

        mainViewModel.setData(compositeDisposable, totalPage)

        var called = 1

        var temp: List<Data>? = null

        mainViewModel.getData().observe(this, Observer {


            if (called == 1) {
                adapter.setData(it as ArrayList<Data>)
                called += 1
            } else {
                // Not set any new data
            }

        })



    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }



}
