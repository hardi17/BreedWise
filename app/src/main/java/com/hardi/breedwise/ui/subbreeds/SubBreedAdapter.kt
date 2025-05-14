package com.hardi.breedwise.ui.subbreeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hardi.breedwise.databinding.LayoutSubBreedNameBinding
import com.hardi.breedwise.ui.allbreed.AllBreedAdapter.DataViewHolder

class SubBreedAdapter(
    private val list: ArrayList<String>
): RecyclerView.Adapter<SubBreedAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: LayoutSubBreedNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subBreed: String) {
            binding.tvBreedName.text = subBreed
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )= DataViewHolder(
        LayoutSubBreedNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SubBreedAdapter.DataViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }


    fun addData(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}