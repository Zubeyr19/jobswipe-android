package com.jobswipe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jobswipe.data.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs WHERE isSwiped = 0 ORDER BY id ASC")
    fun getUnswipedJobs(): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE isSaved = 1 ORDER BY id DESC")
    fun getSavedJobs(): Flow<List<Job>>

    @Query("SELECT * FROM jobs ORDER BY id ASC")
    fun getAllJobs(): Flow<List<Job>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<Job>)

    @Update
    suspend fun updateJob(job: Job)

    @Query("UPDATE jobs SET isSaved = 1, isSwiped = 1 WHERE id = :jobId")
    suspend fun saveJob(jobId: Long)

    @Query("UPDATE jobs SET isSwiped = 1 WHERE id = :jobId")
    suspend fun skipJob(jobId: Long)

    @Query("UPDATE jobs SET isSaved = 0 WHERE id = :jobId")
    suspend fun unsaveJob(jobId: Long)

    @Query("DELETE FROM jobs")
    suspend fun deleteAllJobs()

    @Query("SELECT COUNT(*) FROM jobs")
    suspend fun getJobCount(): Int
}
