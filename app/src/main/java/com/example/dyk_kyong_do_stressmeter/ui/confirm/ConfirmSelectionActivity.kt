package com.example.dyk_kyong_do_stressmeter.ui.confirm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dyk_kyong_do_stressmeter.data.StressEntry
import com.example.dyk_kyong_do_stressmeter.databinding.ActivityConfirmSelectionBinding
import com.example.dyk_kyong_do_stressmeter.domain.StressScorer
import com.example.dyk_kyong_do_stressmeter.ui.main.StressViewModel

class ConfirmSelectionActivity : AppCompatActivity() {

    private lateinit var b: ActivityConfirmSelectionBinding
    private val vm: StressViewModel by viewModels()

    override fun onCreate(savedInstancesState: Bundle?) {
        super.onCreate(savedInstancesState)
        b = ActivityConfirmSelectionBinding.inflate(layoutInflater)
        setContentView(b.root)

        val resId = intent.getIntExtra("resId", 0)
        val pos = intent.getIntExtra("gridPosition", -1)

        if (resId == 0 || pos == -1) { finish(); return }

        b.preview.setImageResource(resId)

        b.btnCancel.setOnClickListener { finish() }

        b.btnSubmit.setOnClickListener {
            val score = StressScorer.scoreFor(pos)
            val ts = System.currentTimeMillis() / 1000 // Convert to seconds
            vm.addEntry(StressEntry(ts, score)) {
                Toast.makeText(this, "Saved score: $score", Toast.LENGTH_SHORT).show()
                // App is no longer visible to the user
                moveTaskToBack(true)
                finish()
            }
        }
    }
}