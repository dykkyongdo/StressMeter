package com.example.dyk_kyong_do_stressmeter.ui.results

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dyk_kyong_do_stressmeter.R
import com.example.dyk_kyong_do_stressmeter.data.StressEntry
import android.view.*
import android.widget.LinearLayout

class ResultsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<StressEntry> = emptyList()

    class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val ts: TextView = v.findViewById(R.id.timestamp)
        val sc: TextView = v.findViewById(R.id.score)
        val container: LinearLayout = v as LinearLayout
    }

    fun submit(list: List<StressEntry>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_result_row, p, false)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val e = data[position]
            // Show raw timestamp as specified
            holder.ts.text = e.epochSeconds.toString()
            holder.sc.text = e.score.toString()
            
            // Add horizontal divider line at bottom of each row
            holder.container.setBackgroundColor(android.graphics.Color.WHITE)
        }
    }

    override fun getItemCount(): Int = data.size
}