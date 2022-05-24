package id.rahma.proyekbacatulis
// Perintah ini digunakan untuk memanggil activity atau data lainnya yang telah dibuat agar saling
//terhubung.
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.rahma.proyekbacatulis.databinding.ActivityMainBinding
import id.rahma.proyekbacatulis.model.InternalFileRepository
import id.rahma.proyekbacatulis.model.Note
import id.rahma.proyekbacatulis.model.NoteRepository
// Pada kelas ini semua kode yang di bikin memiliki keterkaitan dengan tampilan aplikasi yang akan kita buat
// dimana menulis text,membaca dan menghapus data atau file yang telah dimasukkan.
class MainActivity : AppCompatActivity() {
    private val repo: NoteRepository by lazy { InternalFileRepository(this) }
    private lateinit var binding: ActivityMainBinding

    //ini digunakan untuk pemanggilan
    // kelas super onCreate untuk menyelesaikan pembuatan aktivitas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
// Pada saat melakukan penulisan pada file jika file yang ditulis tidak sesuai maka file yang ditulis
// akan dinyatakan tidak failed atau penginputannya terjadi kesalahan.
        binding.btnWrite.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    repo.addNote(
                        Note(
                            binding.editFileName.text.toString(),
                            binding.editTeksCatatan.text.toString()
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "File Write Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
// Pada saat membaca file yang sebelumnya telah di tulis,maka pada saat membacanya nama filenya juga harus
// sesuai dengan yang telah ditulis.Jika tidak sama,akan ada pemberitahuan file yang dibaca tidak failed.
        binding.btnRead.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    val note = repo.getNote(binding.editFileName.text.toString())
                    binding.editTeksCatatan.setText(note.noteText)
                } catch (e: Exception) {
                    Toast.makeText(this, "File Read Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
//Pada saat mengklik tombol hapus,maka harus terbaca terlebih dahulu file yang telah diinputkan.
// Jika tidak terbaca maka file tidak bisa atau tidak ada data yang akan dihapus.Maka jika masih
// melakukan penghapusan saat file tidak ada maka kita akan diminta untuk memberikan nama filenya
// Dan jika file yang dihapus namanya tidak sesuai maka akan dinyatakan penghapusan file tidak valid
        binding.btnDelete.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    if (repo.deleteNote(binding.editFileName.text.toString())) {
                        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "File Could Not Be Deleted", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "File Delete Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
    }
}