package com.example.dyk_kyong_do_stressmeter.ui.results

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dyk_kyong_do_stressmeter.data.StressEntry
import com.example.dyk_kyong_do_stressmeter.databinding.FragmentResultsBinding
import com.example.dyk_kyong_do_stressmeter.ui.main.StressViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*

class ResultFragment : Fragment() {

    private var _b: FragmentResultsBinding? = null
    private val b get() = _b!!
    private val vm: StressViewModel by activityViewModels()
    private val adapter = ResultsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?) =
        FragmentResultsBinding.inflate(inflater, container, false).also { _b = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter
        
        // Add horizontal divider decoration
        b.recycler.addItemDecoration(BlackDividerDecoration())

        vm.entries.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
            renderChart(list)
        }
    }

    override fun onResume() {
        super.onResume()
        vm.refresh()
    }

    private fun renderChart(list: List<StressEntry>) {
        val entries = list.mapIndexed { idx, e -> Entry(idx.toFloat(), e.score.toFloat()) }
        val set = LineDataSet(entries, "").apply {
            // Blue line with Material Blue 700 (#1976D2)
            color = android.graphics.Color.parseColor("#1976D2")
            lineWidth = 2f
            
            // Small circular markers
            setDrawCircles(true)
            circleRadius = 3f
            setCircleColor(android.graphics.Color.parseColor("#1976D2"))
            
            // Translucent blue fill (~33% opacity)
            setDrawFilled(true)
            fillColor = android.graphics.Color.parseColor("#1976D2")
            fillAlpha = 85
            
            // Hide decimal values - show integers only
            setDrawValues(false)
        }
        
        val lineData = LineData(set)
        b.chart.data = lineData
        
        // Chart styling - clean, no legend/description
        b.chart.description.isEnabled = false
        b.chart.setTouchEnabled(false)  // Disable touch to allow scrolling
        b.chart.setPinchZoom(false)    // Disable pinch zoom to allow scrolling
        b.chart.setBackgroundColor(android.graphics.Color.WHITE)
        b.chart.setDrawGridBackground(false)
        
        // Axis styling with muted colors
        val xAxis = b.chart.xAxis
        xAxis.textSize = 11f
        xAxis.textColor = android.graphics.Color.parseColor("#757575")
        xAxis.setDrawGridLines(true)
        xAxis.gridColor = android.graphics.Color.parseColor("#E0E0E0")
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = if (list.size > 1) (list.size - 1).toFloat() else 1f
        xAxis.granularity = 1f
        xAxis.setDrawAxisLine(false)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM // Move labels to bottom
        
        val leftAxis = b.chart.axisLeft
        leftAxis.textSize = 11f
        leftAxis.textColor = android.graphics.Color.parseColor("#757575")
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = android.graphics.Color.parseColor("#E0E0E0")
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 16f
        leftAxis.setLabelCount(9, true) // 0, 2, 4, 6, 8, 10, 12, 14, 16
        leftAxis.setDrawAxisLine(false)
        
        val rightAxis = b.chart.axisRight
        rightAxis.isEnabled = false
        
        // No legend
        val legend = b.chart.legend
        legend.isEnabled = false
        
        // Make chart bigger in width by reducing horizontal offsets
        b.chart.setExtraOffsets(25f, 0f, 25f, 25f)
        
        b.chart.invalidate()
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}

class BlackDividerDecoration(private val heightPx: Int = 1) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply { 
        color = android.graphics.Color.BLACK
        strokeWidth = heightPx.toFloat()
        isAntiAlias = false // Ensure solid lines
        style = Paint.Style.STROKE
    }
    
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        
        // Draw horizontal lines between all rows
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val y = child.bottom.toFloat()
            c.drawLine(left, y, right, y, paint)
        }
    }
    
    override fun getItemOffsets(outRect: android.graphics.Rect, view: android.view.View, parent: RecyclerView, state: RecyclerView.State) {
        // Add bottom margin to create space for the divider line
        outRect.bottom = heightPx
    }
}