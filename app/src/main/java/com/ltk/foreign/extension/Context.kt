package com.ltk.foreign.extension

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import com.ltk.foreign.features.main.MainActivity

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.closeApp() {
    (this as? MainActivity)?.finish()
}

inline fun <T : Any, R> T?.withNotNull(block: (T) -> R): R? {
    return this?.let(block)
}

fun <T> Context.getFileInfo(
    file: Uri,
    columnName: String = OpenableColumns.DISPLAY_NAME,
    data: Cursor.(Int) -> T
) =
    contentResolver?.query(file, null, null, null, null)?.run {
        val index = getColumnIndex(columnName)
        moveToFirst()
        val result = data(index)
        close()
        result
    }

