package com.example.movielistxml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListMovieAdapter (
    private val listMovie: ArrayList<Movie>,
    private val onImdbClick: (String) -> Unit,
    private val onDetailClick: (String, Int, String, String, String) -> Unit
    ):RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>(){

        class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)
            val tvName: TextView = itemView.findViewById(R.id.tv_item_title)
            val years: TextView = itemView.findViewById(R.id.tv_item_years)
            val plot: TextView = itemView.findViewById(R.id.tv_item_plot)
            val desc: TextView = itemView.findViewById(R.id.tv_item_desc)
            val btnImdb: Button = itemView.findViewById(R.id.btn_imdb)
            val btnDetail: Button = itemView.findViewById(R.id.btn_detail)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder{
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ListViewHolder(view)
    }
    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        val (title, url, image, plot, years, desc) = listMovie[position]
        holder.tvName.text = title
        holder.plot.text = plot
        holder.imgPoster.setImageResource(image)
        holder.years.text = years
        holder.desc.text = desc
        holder.btnImdb.setOnClickListener{onImdbClick(url)}
        holder.btnDetail.setOnClickListener{onDetailClick(title, image, plot, years, desc)}
    }

    fun updateData(newList: List<Movie>) {
        listMovie.clear()
        listMovie.addAll(newList)
        notifyDataSetChanged()
    }
    }