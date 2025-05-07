package com.hardi.breedwise.ui.allbreed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.databinding.LayoutSubBreedNameBinding

class AllBreedAdapter(
    private val list: ArrayList<DogBreeds>
) : RecyclerView.Adapter<AllBreedAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: LayoutSubBreedNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dogBreeds: DogBreeds) {
            binding.tvBreedName.text = "${dogBreeds.breed.uppercase()} :"
            binding.tvSubBreedName.text = dogBreeds.subBreedList?.joinToString(", ")
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        LayoutSubBreedNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(newList: List<DogBreeds>) {
        val diffCallback = DogBreedDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class DogBreedDiffCallback(
    private val oldList: List<DogBreeds>,
    private val newList: List<DogBreeds>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].breed == newList[newItemPosition].breed
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}

