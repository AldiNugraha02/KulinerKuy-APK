package org.d3ifcool.kulinerkuy.detail

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.d3ifcool.kulinerkuy.R
import org.d3ifcool.kulinerkuy.dashboard.DashboardActivity
import org.d3ifcool.kulinerkuy.dashboard.DashboardViewModel
import org.d3ifcool.kulinerkuy.dashboard.DashboardViewModelFactory
import org.d3ifcool.kulinerkuy.data.KulinerKuyDB
import org.d3ifcool.kulinerkuy.databinding.ActivityDetailBinding
import org.d3ifcool.kulinerkuy.model.KulinerKuy

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by lazy {
        val dataSource = KulinerKuyDB.getInstance(this).dao
        val factory = DetailViewModelFactory(dataSource)
        ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }
    var nama = "Nama Restoran"
    var alamat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        val buttonKembali = findViewById<ImageButton>(R.id.backButton) as ImageButton
        buttonKembali.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        val buttonSelesai = findViewById<TextView>(R.id.buttonSimpan) as TextView
        buttonSelesai.setOnClickListener {
            val etNamaRestoran = findViewById<EditText>(R.id.tv_namaRestoran) as EditText
            val etAlamatRestoran = findViewById<EditText>(R.id.tv_alamatRestoran) as EditText
            nama = etNamaRestoran.text.toString()
            alamat = etAlamatRestoran.text.toString()
            val id = intent.getIntExtra("id", 0)
            viewModel.updateData(id, nama, alamat)
            Toast.makeText(this, "Update Complate", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        val id = intent.getIntExtra("id", 0)
        viewModel.getData(id).observe(this, {
            val etNamaRestoran = findViewById<EditText>(R.id.tv_namaRestoran) as EditText
            val etAlamatRestoran = findViewById<EditText>(R.id.tv_alamatRestoran) as EditText
            etNamaRestoran.setText(it.nama)
            etAlamatRestoran.setText(it.alamat)
            val image = findViewById<ImageView>(R.id.imageView) as ImageView
            //base64 to image
            val imageBytes = android.util.Base64.decode(it.foto, android.util.Base64.DEFAULT)
            val decodedImage = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            image.setImageBitmap(decodedImage)
            val time = findViewById<TextView>(R.id.time) as TextView
            time.setText("Pada Jam " + it.jam)
        })

    }

    companion object {
        val EXTRA_ID: String? = null
    }

}