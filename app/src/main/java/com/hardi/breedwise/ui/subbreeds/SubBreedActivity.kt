package com.hardi.breedwise.ui.subbreeds

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.breedwise.R
import com.hardi.breedwise.databinding.ActivitySubBreedBinding
import com.hardi.breedwise.utils.AppConstant.BREED_NAME
import com.hardi.breedwise.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubBreedActivity : AppCompatActivity() {

    private lateinit var viewModel: SubBreedViewModel

    private lateinit var binding: ActivitySubBreedBinding

    private lateinit var breedName: String

    @Inject
    lateinit var adapter: SubBreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySubBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpView()
        setUpViewModel()
        setUpResponse()
    }

    private fun setUpResponse() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTexView.visibility = View.GONE
                            binding.subBreedRcv.visibility = View.VISIBLE
                            setResultToAdapter(it.data)
                        }

                        is UIState.Error -> {
                            binding.errorTexView.visibility = View.VISIBLE
                            binding.errorTexView.text = it.message
                            binding.subBreedRcv.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        }

                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.subBreedRcv.visibility = View.GONE
                            binding.errorTexView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setResultToAdapter(subBreedList: List<String>) {
        adapter.addData(subBreedList)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[SubBreedViewModel::class.java]
        viewModel.getSubBreed(breedName)
    }

    private fun setUpView() {
        val recyclerView = binding.subBreedRcv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        recyclerView.adapter = adapter

        breedName = intent.getStringExtra(BREED_NAME).toString()
        binding.breedName.text = breedName
    }
}