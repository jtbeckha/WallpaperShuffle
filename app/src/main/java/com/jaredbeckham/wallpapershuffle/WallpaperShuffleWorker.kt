package com.jaredbeckham.wallpapershuffle

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class WallpaperShuffleWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        private const val TAG = "WPShuffle"
        private val log = LoggerFactory.getLogger(WallpaperShuffleWorker::class.java)

        fun enqueueWallpaperShuffleWorker(repeatInterval : Long, timeUnit: TimeUnit) {
            val worker = PeriodicWorkRequestBuilder<WallpaperShuffleWorker>(repeatInterval, timeUnit)
                .addTag(TAG)
                .build()
            WorkManager.getInstance().enqueue(worker)
        }

        fun dequeueAllWallpaperShuffleWorkers() {
            WorkManager.getInstance().cancelAllWorkByTag(TAG)
        }
    }

    override fun doWork(): Result {
        log.info("Running")

        if (!changeWallpaper(applicationContext)) {
            log.warn("Failed to change wallpaper")
            return Result.failure()
        }

        return Result.success()
    }
}
