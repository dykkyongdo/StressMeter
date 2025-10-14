package com.example.dyk_kyong_do_stressmeter.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.dyk_kyong_do_stressmeter.data.CsvRepository
import com.example.dyk_kyong_do_stressmeter.data.StressEntry
import kotlinx.coroutines.launch

class StressViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = CsvRepository(app)

    private val _entries = MutableLiveData<List<StressEntry>>(emptyList())
    val entries: LiveData<List<StressEntry>> = _entries

    fun refresh() = viewModelScope.launch { _entries.value = repo.readAll() }

    fun addEntry(entry: StressEntry, onDone: () -> Unit = {}) = viewModelScope.launch {
        repo.appendEntry(entry)
        _entries.value = repo.readAll()
        onDone()
    }
}