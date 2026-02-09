package com.jobswipe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val company: String,
    val location: String,
    val salary: String? = null,
    val description: String,
    val applyUrl: String,
    val logoUrl: String? = null,
    val postedDate: String? = null,
    val jobType: String? = null, // Full-time, Part-time, Contract, etc.
    val isSaved: Boolean = false,
    val isSwiped: Boolean = false
)

// Sample jobs for demo/testing
object SampleJobs {
    val jobs = listOf(
        Job(
            id = 1,
            title = "Android Developer",
            company = "Google",
            location = "Mountain View, CA",
            salary = "$120,000 - $180,000",
            description = "Join our Android team to build the next generation of mobile experiences. You'll work on apps used by billions of users worldwide.",
            applyUrl = "https://careers.google.com",
            jobType = "Full-time"
        ),
        Job(
            id = 2,
            title = "Senior Software Engineer",
            company = "Spotify",
            location = "Stockholm, Sweden",
            salary = "€70,000 - €95,000",
            description = "Help us build the future of audio streaming. Work on backend services that power music recommendations for millions.",
            applyUrl = "https://www.lifeatspotify.com/jobs",
            jobType = "Full-time"
        ),
        Job(
            id = 3,
            title = "Full Stack Developer",
            company = "Stripe",
            location = "Remote",
            salary = "$140,000 - $200,000",
            description = "Build payment infrastructure that powers the internet economy. Work with cutting-edge technologies and scale.",
            applyUrl = "https://stripe.com/jobs",
            jobType = "Full-time"
        ),
        Job(
            id = 4,
            title = "Mobile Engineer",
            company = "Netflix",
            location = "Los Gatos, CA",
            salary = "$150,000 - $220,000",
            description = "Create seamless streaming experiences on mobile devices. Work on video playback, UI/UX, and performance optimization.",
            applyUrl = "https://jobs.netflix.com",
            jobType = "Full-time"
        ),
        Job(
            id = 5,
            title = "Backend Engineer",
            company = "Airbnb",
            location = "San Francisco, CA",
            salary = "$130,000 - $190,000",
            description = "Build scalable services that connect travelers with unique stays around the world. Work on search, booking, and payments.",
            applyUrl = "https://careers.airbnb.com",
            jobType = "Full-time"
        ),
        Job(
            id = 6,
            title = "Junior Developer",
            company = "Startup Inc",
            location = "Copenhagen, Denmark",
            salary = "DKK 400,000 - 500,000",
            description = "Join our fast-growing startup and learn from experienced engineers. Great opportunity for recent graduates.",
            applyUrl = "https://example.com/jobs",
            jobType = "Full-time"
        ),
        Job(
            id = 7,
            title = "DevOps Engineer",
            company = "Microsoft",
            location = "Seattle, WA",
            salary = "$125,000 - $175,000",
            description = "Build and maintain cloud infrastructure on Azure. Automate deployments and ensure reliability at scale.",
            applyUrl = "https://careers.microsoft.com",
            jobType = "Full-time"
        ),
        Job(
            id = 8,
            title = "iOS Developer",
            company = "Apple",
            location = "Cupertino, CA",
            salary = "$140,000 - $200,000",
            description = "Create incredible experiences for iPhone and iPad users. Work on apps that ship to millions of devices.",
            applyUrl = "https://www.apple.com/careers",
            jobType = "Full-time"
        )
    )
}
