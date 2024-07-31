package com.alexandermaynard_comp304lab3_ex1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginBtn = findViewById<Button>(R.id.to_login_page_btn)
        loginBtn.setOnClickListener {
            val i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }

    //inflate the options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    //provide options for when a options menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return menuItemSelected(item) //call method to provide the functionality
    }

    //method that provides functionality for all the options items when clicked
    private fun menuItemSelected(item: MenuItem): Boolean {
        //check the item id
        when (item.itemId) {
            //when login option is pressed
            R.id.login_page_option -> {
                //go to the DetachedHome Screen
                val nextScreenIntent = Intent(this, Login::class.java)
                startActivity(nextScreenIntent)
                return true
            }
            //when patients page option is pressed
            R.id.patients_page_option -> {
                //go to the SemiDetachedHome Screen
                val nextScreenIntent = Intent(this, Patient::class.java)
                startActivity(nextScreenIntent)
                return true
            }
            //when tests page option is pressed
            R.id.tests_page_option -> {
                //go to the CondominiumApartment Screen
                //val nextScreenIntent = Intent(this, CondominiumApartment::class.java)
                //startActivity(nextScreenIntent)
                return true
            }
            //when view tests info page option is pressed
            R.id.view_tests_info_page_option -> {
                //go to the Townhouse Screen
                // val nextScreenIntent = Intent(this, Townhouse::class.java)
                //startActivity(nextScreenIntent)
                return true
            }

            //when update patient page option is pressed
            R.id.update_patient_page_option -> {
                //go to the Townhouse Screen
                //val nextScreenIntent = Intent(this, Townhouse::class.java)
                //startActivity(nextScreenIntent)
                return true
            }
        }
        return false
    }
}