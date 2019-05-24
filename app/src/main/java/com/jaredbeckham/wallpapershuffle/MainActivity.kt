package com.jaredbeckham.wallpapershuffle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.snackbar.Snackbar
import com.zfdang.multiple_images_selector.ImagesSelectorActivity
import com.zfdang.multiple_images_selector.SelectorSettings
import java.util.concurrent.TimeUnit

/**
 * Main activity for the WallpaperShuffle app.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val SELECT_IMAGES_REQUEST_CODE = 1
        const val DEFAULT_SHUFFLE_INTERVAL = 24L
        val DEFAULT_SHUFFLE_INTERVAL_TIMEUNIT = TimeUnit.HOURS
    }

    private lateinit var storageManager : StorageManager
    private var selectedPhotos = ArrayList<String>(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageManager = StorageManager(filesDir)
        selectedPhotos = storageManager.readSelections()

        Fresco.initialize(applicationContext)

        setContentView(R.layout.activity_main)

        val selectWallpapersButton = findViewById<Button>(R.id.select_wallpapers)
        selectWallpapersButton.setOnClickListener {
            val intent = Intent(this, ImagesSelectorActivity::class.java)
                .putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1000)
                // Filter out small images (e.g. icons)
                .putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000)
                .putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false)
                .putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, selectedPhotos)
            startActivityForResult(intent, SELECT_IMAGES_REQUEST_CODE)
        }

        val shuffleNowButton = findViewById<Button>(R.id.shuffle_now)
        shuffleNowButton.setOnClickListener {
            val snackbarText = if (changeWallpaper(applicationContext))
                R.string.shuffle_now_success else R.string.shuffle_now_failure

            Snackbar.make(
                findViewById(R.id.parent_coordinator_layout),
                snackbarText,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedPhotos = data?.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS) ?: ArrayList(0)
            storageManager.updateSelections(selectedPhotos)

            WallpaperShuffleWorker.dequeueAllWallpaperShuffleWorkers()
            WallpaperShuffleWorker.enqueueWallpaperShuffleWorker(
                DEFAULT_SHUFFLE_INTERVAL, DEFAULT_SHUFFLE_INTERVAL_TIMEUNIT
            )
        }
    }
}
