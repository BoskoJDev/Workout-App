package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val databaseRef = FirebaseDatabase.getInstance().getReference("WorkoutChads")
        val newUserField = findViewById<EditText>(R.id.newUserField)
        val newPasswordField = findViewById<EditText>(R.id.newPasswordField)
        val confirmPasswordField = findViewById<EditText>(R.id.passwordConfirm)

        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            val newUser = newUserField.text.toString()
            val newPass = newPasswordField.text.toString()
            val confirmedPass = confirmPasswordField.text.toString()
            if (TextUtils.isEmpty(newUser) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmedPass))
            {
                Toast.makeText(applicationContext, "Enter something!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmedPass)
            {
                Toast.makeText(applicationContext, "Passwords don't match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = databaseRef.push().key!!
            databaseRef.child(id).setValue(GymMember(id, newUser, newPass)).addOnCompleteListener {
                Toast.makeText(applicationContext, "You were inserted successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignupActivity, MainActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Error during insertion", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.cancelBtn).setOnClickListener {
            startActivity(Intent(this@SignupActivity, MainActivity::class.java))
        }
    }
}