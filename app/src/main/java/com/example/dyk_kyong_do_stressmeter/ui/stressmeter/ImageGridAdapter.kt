package com.example.dyk_kyong_do_stressmeter.ui.stressmeter

import android.content.Context
import android.view.*
import  android.widget.BaseAdapter
import android.widget.ImageView
import com.example.dyk_kyong_do_stressmeter.R
import kotlin.random.Random

class ImageGridAdapter (
    private val context: Context,
    private val pageIndex: Int // 0,1,2
) : BaseAdapter() {

    // 16 unique images; 3 pages just cycle with different orderings if desired.
    private val all =  intArrayOf(
        R.drawable.psm_stressed_cat, R.drawable.psm_cat, R.drawable.psm_stressed_person, R.drawable.psm_stressed_person3,
        R.drawable.psm_bar, R.drawable.fish_normal017, R.drawable.psm_alarm_clock2, R.drawable.psm_angry_face,
        R.drawable.psm_yoga4, R.drawable.psm_anxious, R.drawable.psm_beach3, R.drawable.psm_lonely2,
        R.drawable.psm_hiking3, R.drawable.psm_stressed_person7, R.drawable.psm_clutter, R.drawable.psm_exam4
    )

    private fun pageSlice(): IntArray = when (pageIndex % 3) {
        0 -> all
        1 -> all.copyOf().apply {  reverse() }
        else -> all.sortedArray() // any different ordering is fine
    }

    private val data = pageSlice()

    override fun getCount() = data.size
    override fun getItem(pos: Int) = data[pos]
    override fun getItemId(pos: Int) = pos.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val v = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_grid_image, parent, false)
        v.findViewById<ImageView>(R.id.img).setImageResource(data[pos])
        return v
    }
}