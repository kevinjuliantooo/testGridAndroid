package com.kevin.testgridandroid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevin.testgridandroid.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {


    val listItem = ArrayList<Data>()

    fun setData(compositeDisposable: CompositeDisposable, totalPage: Int) {

        Log.d("COMPOS", compositeDisposable.toString())

        val requestInterface = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GetData::class.java)

        compositeDisposable.add(requestInterface.getData(totalPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
        )

    }

    private fun handleResponse(list: List<Data>){

        listImage.postValue(list)

    }

    private fun handleError(error: Throwable) {

        Log.e("ERROR", error.localizedMessage)

    }

    internal var listImage = MutableLiveData<List<Data>>()

    internal fun getData(): LiveData<List<Data>> {
        return listImage
    }
}