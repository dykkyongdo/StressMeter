package com.example.dyk_kyong_do_stressmeter.ui.stressmeter

import android.content.Context
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.dyk_kyong_do_stressmeter.R
import com.example.dyk_kyong_do_stressmeter.util.ImageManager

class ImageGridAdapter(
    private val context: Context,
    private val pageIndex: Int
) : BaseAdapter() {

    private fun getData(): IntArray {
        return ImageManager.getPageImages(pageIndex)
    }

    override fun getCount() = getData().size
    override fun getItem(pos: Int) = getData()[pos]
    override fun getItemId(pos: Int) = pos.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val v = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_grid_image, parent, false)
        v.findViewById<ImageView>(R.id.img).setImageResource(getData()[pos])
        return v
    }
}