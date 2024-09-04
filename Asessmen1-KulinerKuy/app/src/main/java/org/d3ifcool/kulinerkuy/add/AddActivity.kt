package org.d3ifcool.kulinerkuy.add

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.d3ifcool.kulinerkuy.dashboard.DashboardActivity
import org.d3ifcool.kulinerkuy.R
import org.d3ifcool.kulinerkuy.data.KulinerKuyDB
import org.d3ifcool.kulinerkuy.databinding.ActivityAddBinding
import org.d3ifcool.kulinerkuy.model.KulinerKuy
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.Calendar

class AddActivity : AppCompatActivity() {
    private val viewModel: AddViewModel by lazy {
        val dataSource = KulinerKuyDB.getInstance(this).dao
        val factory = AddViewModelFactory(dataSource)
        ViewModelProvider(this, factory)[AddViewModel::class.java]
    }
    private lateinit var binding: ActivityAddBinding
    private lateinit var butNav : BottomNavigationView
    private lateinit var imagePicker: ActivityResultLauncher<Intent>
    var foto : String = ""
    var tanggal : String = ""
    var jam : String = ""
    var label_value : Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0
    private lateinit var dashboardActivity: DashboardActivity



    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardActivity = DashboardActivity()

        imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val selectedImageUri: Uri? = data.data
                    val fileName: String? = getFileNameFromUri(selectedImageUri)
                    binding.namaFilePoto.text = fileName
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                    val byteArray = stream.toByteArray()
                    foto = Base64.getEncoder().encodeToString(byteArray)

                }
            }
        }

        binding.tvGambarRestoran.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePicker.launch(intent)
            closeKeyboard()
        }


        binding.buttonSimpan.setOnClickListener {
            Log.d("Simpan", "berhasil klik simpan")
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calender -> {
                    showDatePickerDialog()
                    return@setOnItemSelectedListener true
                }
                // Handle item "alarm" and "label" here
                R.id.alarm -> {
                    TimePickerDialog(this, { _, hour, minute ->
                        this.hour = hour
                        this.minute = minute
                        jam = "$hour:$minute"
                    }, 0, 0, true).show()
                    return@setOnItemSelectedListener true
                }
                R.id.label -> {
                    // Aksi yang akan dilakukan ketika item "label" diklik
                    val label_icon = binding.bottomNavigationView.findViewById<View>(R.id.label)
                    PopupMenu(this, label_icon).apply {
                        menuInflater.inflate(R.menu.menu_label, menu)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.label1 -> {
                                    label_value = 1
                                    return@setOnMenuItemClickListener true
                                }
                                R.id.label2 -> {
                                    label_value = 2
                                    return@setOnMenuItemClickListener true
                                }
                                R.id.label3 -> {
                                    label_value = 3
                                    return@setOnMenuItemClickListener true
                                }
                                else -> false
                            }
                        }
                        setForceShowIcon(true)
                        show()
                    }
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }


        binding.simpan.setOnClickListener {
            val nama = binding.tvNamaRestoran.text.toString()
            val alamat = binding.tvAlamatRestoran.text.toString()
            val tanggal = tanggal
            val jam = jam
            val tag = label_value
            val foto = foto
            viewModel.insertData(
                KulinerKuy(
                    nama = nama,
                    alamat = alamat,
                    tanggal = tanggal,
                    jam = jam,
                    tag = tag,
                    foto = foto,
                    isChecked = false,
                )
            )

            startActivity(Intent(this, DashboardActivity::class.java))
            this.finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Tindakan yang akan dilakukan ketika pengguna memilih tanggal
            // Di sini Anda dapat menampilkan tanggal yang dipilih atau melakukan tindakan lain.
            tanggal = "$dayOfMonth-${month + 1}-$year"
            this.year = year
            this.month = month
            this.day = dayOfMonth
        }, year, month, day)

        datePickerDialog.show()

    }
    private fun closeKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun getFileNameFromUri(uri: Uri?): String? {
        if (uri == null) return null

        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val foto = it.getString(columnIndex)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    /*private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.tvNamaRestoran.text.toString()
        val message = "Jangan lupa makan di $title pada puku $jam"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun getTime(): Long
    {
        val minute = this.minute
        val hour = this.hour
        val day = this.day
        val month = this.month
        val year = this.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }*/

    private fun getTime(): Long
    {
        val minute = this.minute
        val hour = this.hour
        val day = this.day
        val month = this.month
        val year = this.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    //buatkan fungsi untuk membuat schedule notifikasi
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.tvNamaRestoran.text.toString()
        val message = "Jangan lupa makan di $title pada puku $jam"
        intent.putExtra("Tittle", title)
        intent.putExtra("MessageExtra", message)

        val notificationID =1
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

}