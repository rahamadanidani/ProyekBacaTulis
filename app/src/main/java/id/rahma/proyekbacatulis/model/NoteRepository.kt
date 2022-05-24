package id.rahma.proyekbacatulis.model
// semua data atau apapun yang ada pada aplikasi bisa dilihat dan dapat digunakan oleh pengguna.
interface NoteRepository {
    fun addNote(note: Note)
    fun getNote(fileName: String): Note
    fun deleteNote(fileName: String): Boolean
}