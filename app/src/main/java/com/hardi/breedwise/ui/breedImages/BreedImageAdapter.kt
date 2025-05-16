package com.hardi.breedwise.ui.breedImages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hardi.breedwise.databinding.LayoutBreedImageBinding

class BreedImageAdapter(
    private val list: ArrayList<String>
) : RecyclerView.Adapter<BreedImageAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: LayoutBreedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            Glide.with(binding.breedImg.context)
                .load(imgUrl)
                .into(binding.breedImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        LayoutBreedImageBinding.inflate(
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

    fun addData(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}