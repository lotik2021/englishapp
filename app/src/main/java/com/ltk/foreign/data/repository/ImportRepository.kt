package com.ltk.foreign.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.ltk.foreign.data.db.ForeignDatabase
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word
import com.ltk.foreign.utils.CsvIo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileReader
import java.util.zip.ZipInputStream

class ImportRepository(
    private val provider: ForeignDatabase,
    private val context: Context,
    private val mutex: Mutex,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun import(uri: Uri) {
        withContext(dispatcher + scope.coroutineContext) {
            mutex.withLock {
                try {
                    val restoreDir = File(context.externalCacheDir, "restore")
                    if (restoreDir.exists()) restoreDir.deleteRecursively()
                    restoreDir.mkdir()
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        ZipInputStream(BufferedInputStream(stream)).use { zip ->
                            var entry = zip.nextEntry
                            while (entry != null) {
                                val file = File(restoreDir, entry.name)
                                try {
                                    file.outputStream().use { output -> zip.copyTo(output) }
                                } catch (_: Exception) {
                                }
                                entry = zip.nextEntry
                            }
                        }
                    }

                    val lf = restoreDir.listFiles()
                    for (f in lf!!) {
                        val csvReader = CsvIo.Reader(FileReader(File(restoreDir, f.name)))
                        val rows = csvReader.rows()
                        csvReader.close()

                        val dictId = provider.dictDao().insertDict(
                            Dict(
                                id = 0,
                                title = f.nameWithoutExtension,
                                subtitle = null,
                                inside = 0,
                                current = 0,
                                wordId = 0
                            )
                        )

                        importWordsInDict(rows, dictId)
                    }
                } catch (e: Exception) {
                    Log.e(">>>>>>>", "Import dict", e)
                }
            }
        }
    }

    private suspend fun importWordsInDict(rows: List<Array<String>>, dictId: Long) {
        rows.forEach { columns ->
            val nameWord = columns[0]
            val transliterateWord: String?
            val translateWord: String

            if (columns.size >= 3) {
                transliterateWord = columns[1]
                translateWord = columns[2]
                Log.d(">>>>3>>>", nameWord)
            } else if (columns.size == 2) {
                transliterateWord = null
                translateWord = columns[1]
                Log.d(">>>>2>>>", nameWord)
            } else {
                Log.d(">>>>3>>>", nameWord)
                return@forEach
            }

            val word = Word(
                id = 0,
                name = nameWord,
                transcription = transliterateWord,
                translate = translateWord,
                term = null,
                checked = 0,
                dictId = dictId,
                inside = 0
            )
            try {
                provider.wordDao().insertWord(word)
            } catch (_: Exception) {
            }
        }
    }
}
