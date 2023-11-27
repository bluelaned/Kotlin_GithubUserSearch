package com.bluelaned.submission1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluelaned.submission1.response.GithubResponse
import com.bluelaned.submission1.response.ItemsItem
import com.bluelaned.submission1.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        private val TAG = "MainViewModel"
        private const val typeUser = "User"
    }

    private val _ListSearch = MutableLiveData<List<ItemsItem>>()
    val listSearch: LiveData<List<ItemsItem>> = _ListSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var _isloaded = false



    fun getListSearch(namaUser : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getItemsItem(namaUser)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _ListSearch.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
