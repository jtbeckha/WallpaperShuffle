package com.jaredbeckham.wallpapershuffle.selector

import android.net.Uri

/**
 * Responsible for selecting wallpapers.
 */
interface WallpaperSelector {

    /**
     * Selects a wallpaper from the List of candidate wallpapers.
     * @return Uri pointing at the selected wallpaper, or null if no eligible candidates could be identified.
     */
    fun selectWallpaper() : Uri?

}