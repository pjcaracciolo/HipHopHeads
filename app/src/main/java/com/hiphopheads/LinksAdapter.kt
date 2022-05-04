package com.hiphopheads

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hiphopheads.R
import com.example.hiphopheads.databinding.AdapterLinksBinding

class LinksAdapter(private val onLinkClick: (position: Int) -> Unit): RecyclerView.Adapter<LinksViewHolder>() {
    private var links = ArrayList<Link?>()
    fun setLinksList(links: ArrayList<Link?>) {
        this.links = links
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterLinksBinding.inflate(inflater, parent, false)
        return LinksViewHolder(binding, onLinkClick)
    }
    override fun onBindViewHolder(holder: LinksViewHolder, position: Int) {
        val link = links[position]
        holder.binding.linkButton.text = link?.service
        holder.binding.linkButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, link!!.color))
    }
    override fun getItemCount(): Int {
        return links.size
    }
}

class LinksViewHolder(val binding: AdapterLinksBinding, private val onLinkClick: (position: Int) -> Unit): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.linkButton.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val position = adapterPosition
        onLinkClick(position)
    }


}