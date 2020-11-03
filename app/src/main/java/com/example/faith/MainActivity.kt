package com.example.faith

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Check on type of used device */
        printTypeOfUsedDevice()
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
            R.id.penthouseFragment -> {
                this.findNavController(R.id.myNavHostFragment).navigate(R.id.action_hotelFragment_to_penthouseFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Prints whether a mobile device or tablet is being used, using a toast.
     */
    fun printTypeOfUsedDevice()
    {
        val manager =
            applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Objects.requireNonNull(manager).phoneType == TelephonyManager.PHONE_TYPE_NONE) {
            Toast.makeText(
                this@MainActivity,
                "Detected... You're using a Tablet",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this@MainActivity,
                "Detected... You're using a Mobile Phone",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}