package com.bluelaned.submission1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelaned.submission1.activity.DetailsActivity
import com.bluelaned.submission1.adapter.ListFollowersAdapter
import com.bluelaned.submission1.databinding.FragmentFollowersBinding
import com.bluelaned.submission1.viewmodel.DetailViewModel


class FollowersFragment : Fragment() {
    var position = 0
    var username: String = ""

    private lateinit var binding: FragmentFollowersBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_USERNAME = "0"
        const val ARG_POSITION = "Joshua Felix"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "Joshua Feliex"
        }

        detailViewModel.getFollowing(username)
        detailViewModel.getFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager

        detailViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) { followers ->
                val adapter = ListFollowersAdapter { username ->

                    val intent = Intent(requireContext(), DetailsActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                }
                adapter.submitList(followers)
                binding.rvFollowers.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                val adapter = ListFollowersAdapter { username ->

                    val intent = Intent(requireContext(), DetailsActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                }
                adapter.submitList(following)
                binding.rvFollowers.adapter = adapter
            }
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