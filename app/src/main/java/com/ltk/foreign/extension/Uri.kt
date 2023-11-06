package com.ltk.foreign.extension

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toFile
import java.io.File

fun Uri.toFile(context: Context): File? {
    if (!exists()) return null

    return try {
        toFile()
    } catch (e: IllegalArgumentException) {
        context.getFileInfo(this) {
            File(getString(it))
        }
    }
}

fun Uri.exists(): Boolean {
    return try {
        toFile().exists()
    } catch (e: IllegalArgumentException) {
        val path = path?.replace(
            "external_files",
            Environment.getExternalStorageDirectory().toString()
        ).toString()
        File(path).exists()
    }
}
