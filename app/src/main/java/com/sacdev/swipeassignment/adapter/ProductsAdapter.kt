package com.sacdev.swipeassignment.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sacdev.swipeassignment.databinding.ItemProductsListBinding
import com.sacdev.swipeassignment.model.ProductModel


class ProductsAdapter(private var productList: List<ProductModel>) : RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun updateList(products: List<ProductModel>) {
        productList = products
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemProductsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductModel) {
            binding.productModel = product
            binding.executePendingBindings()
        }
    }
}
