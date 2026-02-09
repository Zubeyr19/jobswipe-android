package com.jobswipe.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobswipe.ui.components.JobCard
import com.jobswipe.ui.components.SwipeableCard
import com.jobswipe.ui.theme.SwipeLeft
import com.jobswipe.ui.theme.SwipeRight
import com.jobswipe.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: JobViewModel,
    onNavigateToSaved: () -> Unit
) {
    val jobs by viewModel.unswipedJobs.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "JobSwipe",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.resetJobs() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset")
                    }
                    IconButton(onClick = onNavigateToSaved) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Saved Jobs")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (jobs.isEmpty()) {
                // No more jobs
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No more jobs!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Check your saved jobs or refresh to start over",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        FloatingActionButton(
                            onClick = { viewModel.resetJobs() },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset")
                        }
                        FloatingActionButton(
                            onClick = onNavigateToSaved,
                            containerColor = SwipeRight
                        ) {
                            Icon(Icons.Default.Bookmark, contentDescription = "Saved")
                        }
                    }
                }
            } else {
                // Show top job card
                val currentJob = jobs.first()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Card Stack
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        SwipeableCard(
                            onSwipeLeft = {
                                viewModel.swipeLeft(currentJob)
                            },
                            onSwipeRight = {
                                viewModel.swipeRight(currentJob)
                                // Open job application URL
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentJob.applyUrl))
                                context.startActivity(intent)
                            }
                        ) {
                            JobCard(job = currentJob)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Skip Button
                        FloatingActionButton(
                            onClick = { viewModel.swipeLeft(currentJob) },
                            modifier = Modifier.size(64.dp),
                            shape = CircleShape,
                            containerColor = SwipeLeft
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Skip",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Jobs remaining counter
                        Text(
                            text = "${jobs.size} jobs",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        // Like Button
                        FloatingActionButton(
                            onClick = {
                                viewModel.swipeRight(currentJob)
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentJob.applyUrl))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.size(64.dp),
                            shape = CircleShape,
                            containerColor = SwipeRight
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Apply",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
