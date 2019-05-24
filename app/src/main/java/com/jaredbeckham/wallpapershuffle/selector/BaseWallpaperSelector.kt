package com.jaredbeckham.wallpapershuffle.selector

import android.net.Uri

/**
 * Base implements of [WallpaperSelector].
 */
abstract class BaseWallpaperSelector : WallpaperSelector {

    /**
     * @see [WallpaperSelector.selectWallpaper]
     */
    override fun selectWallpaper() : Uri? {
        val candidates = retrieveCandidateImages()

        if (candidates.isEmpty()) {
            return null
        }

        return candidates.shuffled().first()
    }

    /**
     * Retrieve the list of candidate wallpapers.
     */
    abstract fun retrieveCandidateImages() : List<Uri>
}