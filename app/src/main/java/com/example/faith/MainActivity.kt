package com.example.faith

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Enabling support for the up button, allowing for a more controlled backstack */
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)


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
                NavigationUI.onNavDestinationSelected(item!!, this.findNavController(R.id.myNavHostFragment))
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}