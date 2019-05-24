package com.jaredbeckham.wallpapershuffle

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import com.jaredbeckham.wallpapershuffle.selector.LocalWallpaperSelector
import com.jaredbeckham.wallpapershuffle.selector.WallpaperSelector
import org.slf4j.LoggerFactory

const val LOGGER_NAME = "com.jaredbeckham.wallpapershuffle.WallpaperChanger"

/**
 * Selects a wallpaper and sets it as the system wallpaper.
 *
 * @return True if the wallpaper was successfully updated, false otherwise
 */
fun changeWallpaper(context: Context): Boolean {
    val wallpaperSelector: WallpaperSelector = LocalWallpaperSelector(StorageManager(context.filesDir))

    val selected = wallpaperSelector.selectWallpaper()
    if (selected == null) {
        LoggerFactory.getLogger(LOGGER_NAME).warn("Failed to select a wallpaper")

        return false
    }

    val wallpaperManager = WallpaperManager.getInstance(context)
    val bitmap = BitmapFactory.decodeFile(selected.encodedPath)
    wallpaperManager.setBitmap(bitmap)

    return true
}
