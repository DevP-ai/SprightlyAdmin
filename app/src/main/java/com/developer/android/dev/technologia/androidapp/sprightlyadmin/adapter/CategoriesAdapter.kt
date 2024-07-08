package com.developer.android.dev.technologia.androidapp.sprightlyadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.databinding.CategoryRecycleDesignBinding
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.model.Categories

class CategoriesAdapter:ListAdapter<Categories,CategoriesAdapter.CategoryViewHolder>(CategoriesDiffUtil()){

    inner class CategoryViewHolder(val binding: CategoryRecycleDesignBinding):RecyclerView.ViewHolder(binding.root)


    class CategoriesDiffUtil:DiffUtil.ItemCallback<Categories>(){
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.catId == newItem.catId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryRecycleDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category  = getItem(position)
        holder.binding.recyclerCatName.text = category.catName
        Glide.with(holder.itemView)
            .load(category.catImage)
            .into(holder.binding.recyclerCatImage)
    }
}