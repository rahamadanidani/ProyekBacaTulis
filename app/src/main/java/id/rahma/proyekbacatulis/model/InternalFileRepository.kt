package id.rahma.proyekbacatulis.model

import android.content.Context
import java.io.File
// kelas ini berfungsi untuk menyimpan data yang akan kita masukan pada saat menjalankan aplikasi.
class InternalFileRepository(var context: Context) :
    NoteRepository {

    private fun noteFile(fileName: String): File = File(noteDirectory(), fileName)
    private fun noteDirectory(): String = context.filesDir.absolutePath
// kita akan memasukan file dengan  cara menulis nama file dengan format yang benar.
    override fun addNote(note: Note) {
        context.openFileOutput(note.fileName, Context.MODE_PRIVATE).use { output ->
            output.write(note.noteText.toByteArray())
        }
    }
// kita akan membuka file yang telah di masukan sebelumnya dengan membaca kembali text tersebut.
    override fun getNote(fileName: String): Note {
        val note = Note(fileName, "")
        context.openFileInput(fileName).use { stream ->
            val text = stream.bufferedReader().use {
                it.readText()
            }
            note.noteText = text
        }
        return note
    }
// Pada saat semua file telah dimasukan,maka jika tidak ingin penyimpannya maka kita dapat menghapusnya.
    override fun deleteNote(fileName: String): Boolean {
        return noteFile(fileName).delete()
    }
}
