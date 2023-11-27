package com.bluelaned.submission1.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bluelaned.submission1.R
import com.bluelaned.submission1.adapter.SectionsPagerAdapter
import com.bluelaned.submission1.databinding.ActivityDetailsBinding
import com.bluelaned.submission1.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailsBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root) // Inflate the layout before accessing its views

        val username = intent.getStringExtra("USERNAME")
        val avatar = intent.getStringExtra("AVATAR")

        if (username != null) {
            val sectionPagerAdapter = SectionsPagerAdapter(this, username)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailsActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUser)
                binding.tvName.text = it.name
                binding.tvUsername.text = it.login
                binding.tvTotalFollowers.text = "${it.followers} Follower"
                binding.tvTotalFollowing.text = "${it.following} Following"
            }
        }

        detailViewModel.loading.observe(this) {
            showLoading(it)
        }
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
}
