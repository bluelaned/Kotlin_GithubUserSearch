package com.bluelaned.submission1.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelaned.submission1.adapter.ListFollowersAdapter
import com.bluelaned.submission1.databinding.ActivityMainBinding
import com.bluelaned.submission1.response.ItemsItem
import com.bluelaned.submission1.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    MainViewModel.getListSearch(searchView.text.toString())
                    MainViewModel.listSearch.observe(this@MainActivity) {
                        if (it.isNullOrEmpty()) {
                            showNotFound(true)
                        } else {
                            showNotFound(false)
                        }
                    }
                    false
                }
        }

        MainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listSearch.layoutManager = layoutManager

        MainViewModel.listSearch.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }
    }

    private fun setUserData(user: List<ItemsItem>) {
        val adapter = ListFollowersAdapter { username ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }
        adapter.submitList(user)
        binding.listSearch.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                listSearch.visibility = View.GONE
            } else {
                listSearch.visibility = View.VISIBLE
            }
        }
    }

}