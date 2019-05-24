package com.jaredbeckham.wallpapershuffle.selector

import android.net.Uri
import com.jaredbeckham.wallpapershuffle.StorageManager

/**
 * Local implementation of [WallpaperSelector]. Selects wallpaper from a list of local candidates.
 */
class LocalWallpaperSelector(private val storageManager: StorageManager) : BaseWallpaperSelector() {

    override fun retrieveCandidateImages(): List<Uri> {
        val selections = storageManager.readSelections()

        val candidates = ArrayList<Uri>()
        selections.forEach { candidates.add(Uri.parse(it)) }

        return candidates
    }
}