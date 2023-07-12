package com.example.workoutapp

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.workoutapp.databinding.ActivityListBinding
import com.google.firebase.database.FirebaseDatabase

class ListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_list)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menu?.add("End membership")
        menu?.add("Change password")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val chadID = intent.getStringExtra("chadID")!!
        val chadName = intent.getStringExtra("chadName")!!
        if (item.title == "End membership")
        {
            FirebaseDatabase.getInstance().getReference("WorkoutChads").child(chadID).removeValue()
            return super.onOptionsItemSelected(item)
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter new password")

        val passInput = EditText(this)
        passInput.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(passInput)

        builder.setPositiveButton("Ok") { _, _ ->
            val databaseRef = FirebaseDatabase.getInstance().getReference("WorkoutChads").child(chadID)
            databaseRef.setValue(GymMember(chadID, chadName, passInput.text.toString()))
        }.
        setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }.create().show()

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_list)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}