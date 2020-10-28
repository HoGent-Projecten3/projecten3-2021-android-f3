package com.example.faith

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_hotel)
        val navController = findNavController(R.id.myNavHostFragment)
        val navConfig = AppBarConfiguration(setOf(R.id.mediumListFragment, R.id.cinemaFragment, R.id.infobalieFragment, R.id.penthouseFragment, R.id.dagboekListFragment2))
        setupActionBarWithNavController(navController, navConfig)
        bottomNavigationView.setupWithNavController(navController)
    }

    /**
     * Allows for the up button to "navigate up"
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    /**
     * Allows for a global insertion of the menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    /**
     * Handler for specific selections in the menu bar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                NavigationUI.onNavDestinationSelected(item, this.findNavController(R.id.myNavHostFragment))
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Prints whether a mobile device or tablet is being used, using a toast.
     */
}
