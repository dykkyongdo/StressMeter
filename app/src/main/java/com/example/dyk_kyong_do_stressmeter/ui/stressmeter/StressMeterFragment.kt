package com.example.dyk_kyong_do_stressmeter.ui.stressmeter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.dyk_kyong_do_stressmeter.databinding.FragmentStressMeterBinding
import com.example.dyk_kyong_do_stressmeter.ui.confirm.ConfirmSelectionActivity
import com.example.dyk_kyong_do_stressmeter.util.ImageManager

class StressMeterFragment : Fragment() {

    private var _b: FragmentStressMeterBinding? = null
    private val b get() = _b!!
    private var page = 0
    private var context: android.content.Context? = null
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?) =
        FragmentStressMeterBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context = requireContext()
        prefs = requireContext().getSharedPreferences("stress_meter", Context.MODE_PRIVATE)
        
        savedInstanceState?.let {
            page = it.getInt("page", 0)
        }
        
        setAdapter()

        b.grid.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val resId = (b.grid.adapter as ImageGridAdapter).getItem(position) as Int
            val intent = Intent(requireContext(), ConfirmSelectionActivity::class.java)
            intent.putExtra("resId", resId)
            intent.putExtra("gridPosition", position)
            startActivity(intent)
        }

        b.btnMore.setOnClickListener {
            page++
            setAdapter()
        }
    }

    override fun onResume() {
        super.onResume()
        val shouldShuffle = prefs.getBoolean("should_shuffle", false)
        if (shouldShuffle) {
            ImageManager.shuffleImages()
            prefs.edit().putBoolean("should_shuffle", false).apply()
        }
        setAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("page", page)
    }

    private fun setAdapter() {
        context?.let { ctx ->
            b.grid.adapter = ImageGridAdapter(ctx, page)
        }
    }

    override fun onDestroyView() {
        _b = null
        context = null
        super.onDestroyView()
    }
}