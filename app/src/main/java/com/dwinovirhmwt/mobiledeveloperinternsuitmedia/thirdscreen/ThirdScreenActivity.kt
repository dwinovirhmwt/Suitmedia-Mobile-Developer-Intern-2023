package com.dwinovirhmwt.mobiledeveloperinternsuitmedia.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.R
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.databinding.ActivityThirdScreenBinding
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network.User
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network.UserResponse
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadMoreData()
                    }
                }
            }
        })

        setupToolbar()
        fetchData()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbarThird)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { selectedUser ->
            // Handle item click here
            updateSelectedUserName(selectedUser)
        }
        binding.rvUser.adapter = userAdapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchData() {
        showLoading()
        val api = ApiConfig.apiService
        val call = api.getUsers(currentPage)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                hideLoading()
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        userAdapter.addAll(it.data)
                        currentPage++
                        isLastPage = currentPage > userResponse.totalPages
                    }
                } else {
                    // Log error message or response code
                    Log.e("API", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                hideLoading()
                t.printStackTrace()
                // Log failure message
                Log.e("API", "Failed to fetch data")
            }
        })
    }

    private fun refreshData() {
        currentPage = 1
        isLastPage = false
        userAdapter.clear()
        fetchData()
    }

    private fun loadMoreData() {
        isLoading = true
        fetchData()
    }

    private fun showLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun hideLoading() {
        isLoading = false
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun updateSelectedUserName(selectedUser: User) {
        val resultIntent = Intent()
        resultIntent.putExtra("selectedUser", selectedUser)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

}