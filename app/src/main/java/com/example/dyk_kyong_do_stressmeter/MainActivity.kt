package com.example.dyk_kyong_do_stressmeter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dyk_kyong_do_stressmeter.databinding.ActivityMainBinding
import com.example.dyk_kyong_do_stressmeter.util.ImageManager
import com.example.dyk_kyong_do_stressmeter.util.VibrationAndSound
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        prefs = getSharedPreferences("stress_meter", Context.MODE_PRIVATE)

        setSupportActionBar(b.appBarMain.toolbar)
        
        navController = findNavController(R.id.nav_host_fragment_content_main)
        
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_stressmeter, R.id.nav_results),
            b.drawerLayout
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        b.navView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            VibrationAndSound.onStart(this)
            ImageManager.shuffleImages()
        }
    }

    override fun onResume() {
        super.onResume()
        val shouldShuffle = prefs.getBoolean("should_shuffle", false)
        if (shouldShuffle) {
            ImageManager.shuffleImages()
            prefs.edit().putBoolean("should_shuffle", false).apply()
        }
    }

    override fun onPause() {
        super.onPause()
        prefs.edit().putBoolean("should_shuffle", true).apply()
    }

    override fun onStop() {
        super.onStop()
        prefs.edit().putBoolean("should_shuffle", true).apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
