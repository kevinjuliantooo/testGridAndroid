package com.kevin.testgridandroid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_image.view.*
import java.util.zip.Inflater

class Adapter : RecyclerView.Adapter<Adapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private var mData = ArrayList<Data>()

    fun setData(item: ArrayList<Data>){
//        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image, parent, false)
        return GridViewHolder(view)

    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data){

            val imageView = itemView.findViewById<ImageView>(R.id.imageView)

            with(itemView) {
                Glide.with(this).load(data.download_url).into(imageView)

            }

            itemView.setOnClickListener { onItemClickCallback.onItemClicked(mData[position]) }
            }
        }

    interface OnItemClickCallback {
        fun onItemClicked(data: Data)
    }

}
