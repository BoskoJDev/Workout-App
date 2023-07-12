package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class MainActivity : AppCompatActivity()
{
    private var gymMembers = ArrayList<GymMember>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userField = findViewById<EditText>(R.id.userField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        val dbRef = FirebaseDatabase.getInstance().getReference("WorkoutChads")
        dbRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (!snapshot.exists())
                    return

                for (snap in snapshot.children)
                    gymMembers.add(snap.getValue(GymMember::class.java) as GymMember)
            }

            override fun onCancelled(error: DatabaseError)
            {

            }
        })

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            val userFieldStr = userField.text.toString()
            val passwordFieldStr = passwordField.text.toString()
            if (TextUtils.isEmpty(userFieldStr) || TextUtils.isEmpty(passwordFieldStr))
            {
                Toast.makeText(applicationContext, "Don't leave empty fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            for (gm in gymMembers)
            {
                if (gm.name == userFieldStr && gm.password == passwordFieldStr)
                {
                    val namera = Intent(this@MainActivity, ListActivity::class.java)
                    namera.putExtra("chadID", gm.id)
                    namera.putExtra("chadName", gm.name)
                    startActivity(namera)
                    return@setOnClickListener
                }
            }

            Toast.makeText(applicationContext, "No such user!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.signupBtn).setOnClickListener {
            startActivity(Intent(this@MainActivity, SignupActivity::class.java))
        }
    }
}