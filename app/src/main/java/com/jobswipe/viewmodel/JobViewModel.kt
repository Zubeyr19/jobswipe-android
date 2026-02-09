package com.jobswipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jobswipe.data.local.JobDatabase
import com.jobswipe.data.model.Job
import com.jobswipe.data.repository.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JobRepository

    val unswipedJobs: StateFlow<List<Job>>
    val savedJobs: StateFlow<List<Job>>

    private val _currentJobIndex = MutableStateFlow(0)
    val currentJobIndex: StateFlow<Int> = _currentJobIndex

    init {
        val jobDao = JobDatabase.getDatabase(application).jobDao()
        repository = JobRepository(jobDao)

        unswipedJobs = repository.unswipedJobs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        savedJobs = repository.savedJobs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Load sample jobs on first launch
        viewModelScope.launch {
            repository.loadSampleJobsIfEmpty()
        }
    }

    fun swipeRight(job: Job) {
        viewModelScope.launch {
            repository.saveJob(job.id)
        }
    }

    fun swipeLeft(job: Job) {
        viewModelScope.launch {
            repository.skipJob(job.id)
        }
    }

    fun unsaveJob(job: Job) {
        viewModelScope.launch {
            repository.unsaveJob(job.id)
        }
    }

    fun resetJobs() {
        viewModelScope.launch {
            repository.resetAllJobs()
        }
    }
}
