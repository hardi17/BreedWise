package com.hardi.breedwise.utils

import androidx.recyclerview.widget.DiffUtil
import com.hardi.breedwise.data.model.DogBreeds

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