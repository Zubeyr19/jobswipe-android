package com.jobswipe.data.repository

import com.jobswipe.data.local.JobDao
import com.jobswipe.data.model.Job
import com.jobswipe.data.model.SampleJobs
import kotlinx.coroutines.flow.Flow

class JobRepository(private val jobDao: JobDao) {

    val unswipedJobs: Flow<List<Job>> = jobDao.getUnswipedJobs()
    val savedJobs: Flow<List<Job>> = jobDao.getSavedJobs()

    suspend fun saveJob(jobId: Long) {
        jobDao.saveJob(jobId)
    }

    suspend fun skipJob(jobId: Long) {
        jobDao.skipJob(jobId)
    }

    suspend fun unsaveJob(jobId: Long) {
        jobDao.unsaveJob(jobId)
    }

    suspend fun loadSampleJobsIfEmpty() {
        if (jobDao.getJobCount() == 0) {
            jobDao.insertJobs(SampleJobs.jobs)
        }
    }

    suspend fun resetAllJobs() {
        jobDao.deleteAllJobs()
        jobDao.insertJobs(SampleJobs.jobs)
    }
}
