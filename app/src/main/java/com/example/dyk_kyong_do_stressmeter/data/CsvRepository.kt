package com.example.dyk_kyong_do_stressmeter.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CsvRepository(private val context: Context) {

    private val fileName = "stress_timestamp.csv"

    private fun file(): File = File(context.filesDir, fileName)

    suspend fun appendEntry(entry: StressEntry) = withContext(Dispatchers.IO) {
        val f = file()
        if (!f.exists()) f.writeText("timestamp,score\n")
        f.appendText("${entry.epochSeconds},${entry.score}\n")
    }

    suspend fun readAll(): List<StressEntry> = withContext(Dispatchers.IO) {
        val f = file()
        if (!f.exists()) return@withContext emptyList()
        f.readLines()
            .drop(1) // header
            .mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) {
                    parts[0].toLongOrNull()?.let { ts ->
                        parts[1].toIntOrNull()?.let {sc ->
                            StressEntry(ts, sc)
                        }
                    }
                } else null
            }
    }
}