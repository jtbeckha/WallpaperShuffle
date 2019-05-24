package com.jaredbeckham.wallpapershuffle

import java.io.File

/**
 * Responsible for managing the persistent storage files used by the app. In particular, this includes the
 * file used to persist selected images.
 *
 * @param filesDir Directory containing persistent app files
 */
class StorageManager(private val filesDir: File) {

    companion object {
        const val SELECTIONS_FILE_NAME = "selections"
    }

    /**
     * Reads and returns the stored wallpaper selections.
     */
    fun readSelections() : ArrayList<String> {
        val selectionsFile = File(filesDir, SELECTIONS_FILE_NAME)
        val selections = ArrayList<String>()

        if (selectionsFile.length() != 0L) {
            for (line in selectionsFile.readLines()) {
                selections.add(line)
            }
        }

        return selections
    }

    /**
     * Update the stored wallpaper selections. This method will clear out all previous selections and replace
     * them with the provided selections
     */
    fun updateSelections(selections : ArrayList<String>) {
        val tmpPersistentStorage = File(filesDir, SELECTIONS_FILE_NAME + "_tmp")

        if (tmpPersistentStorage.length() != 0L) {
            tmpPersistentStorage.delete()
        }

        for (selection in selections) {
            tmpPersistentStorage.appendText("$selection\n")
        }

        val persistentStorage = File(filesDir, SELECTIONS_FILE_NAME)
        persistentStorage.delete()
        tmpPersistentStorage.renameTo(persistentStorage)
    }
}