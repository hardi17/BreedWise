package com.hardi.breedwise.ui

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
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.databinding.ActivityMainBinding
import com.hardi.breedwise.ui.allbreed.AllBreedAdapter
import com.hardi.breedwise.ui.allbreed.AllBreedViewmodel
import com.hardi.breedwise.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var allBreedViewmodel: AllBreedViewmodel

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var allBreedAdapter: AllBreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
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

    private fun setUpView() {
        val recyclerView = binding.allBreedRcv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = allBreedAdapter
    }

    private fun setUpResponse() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allBreedViewmodel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorTexView.visibility = View.GONE
                            binding.allBreedRcv.visibility = View.VISIBLE
                            setResultToAdapter(it.data)
                        }

                        is UIState.Error -> {
                            binding.errorTexView.visibility = View.VISIBLE
                            binding.errorTexView.text = it.message
                            binding.allBreedRcv.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        }

                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.allBreedRcv.visibility = View.GONE
                            binding.errorTexView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setResultToAdapter(dogBreedList: List<DogBreeds>) {
        allBreedAdapter.addData(dogBreedList)
    }


    private fun setUpViewModel() {
        allBreedViewmodel = ViewModelProvider(this)[AllBreedViewmodel::class.java]
        allBreedViewmodel.loadAllBreed()
    }

}