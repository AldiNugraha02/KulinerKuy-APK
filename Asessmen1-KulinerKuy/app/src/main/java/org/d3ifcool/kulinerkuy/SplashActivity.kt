package org.d3ifcool.kulinerkuy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.d3ifcool.kulinerkuy.dashboard.DashboardActivity
import org.d3ifcool.kulinerkuy.databinding.ActivitySplachscreenBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplachscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashDuration = 1500
        val intent = Intent(this, DashboardActivity::class.java)

        Handler().postDelayed({
            binding.splashImageView.setImageResource(R.drawable.logo_main)
            startActivity(intent)
            finish()
        }, splashDuration.toLong())
    }
}
