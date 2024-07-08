package com.developer.android.dev.technologia.androidapp.sprightlyadmin

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.dashboardFragment -> showBottomNavigation()
                R.id.addFragment -> showBottomNavigation()
                R.id.chatFragment -> showBottomNavigation()
                R.id.profileFragment -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
    }

    private fun hideBottomNavigation(){
        binding.bottomNavigation.visibility = View.GONE
    }
    private fun showBottomNavigation(){
        binding.bottomNavigation.visibility = View.VISIBLE
    }

}