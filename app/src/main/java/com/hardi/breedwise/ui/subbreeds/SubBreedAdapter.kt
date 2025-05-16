package com.hardi.breedwise.ui.subbreeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hardi.breedwise.databinding.LayoutSubBreedNameBinding
import com.hardi.breedwise.ui.allbreed.AllBreedAdapter.DataViewHolder
import com.hardi.breedwise.utils.OnSubBreedClick

class SubBreedAdapter(
    private val list: ArrayList<String>
): RecyclerView.Adapter<SubBreedAdapter.DataViewHolder>() {

    lateinit var onSubBreedClick: OnSubBreedClick

    class DataViewHolder(private val binding: LayoutSubBreedNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subBreed: String, onSubBreedClick : OnSubBreedClick) {
            binding.tvBreedName.text = subBreed
            binding.tvBreedName.setOnClickListener {
                onSubBreedClick(subBreed)
            }
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

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(list[position], onSubBreedClick)
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