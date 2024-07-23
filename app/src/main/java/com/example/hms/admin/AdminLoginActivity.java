package com.example.hms.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.hms.DatabaseHelper;
import com.example.hms.R;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login); // Correct the layout file name
    }


    public void onLoginButtonClick(View view) {
        // Remove unnecessary initialization of adminLoginButton

        // Inside your activity or controller class
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {

// Handle button click
            view.setOnClickListener(view1 -> {
                @SuppressLint("WrongViewCast") AppCompatEditText usernameEditText = findViewById(R.id.adminUsername); // Replace with actual ID
                @SuppressLint("WrongViewCast") AppCompatEditText passwordEditText = findViewById(R.id.adminPassword); // Replace with actual ID

                String username = Objects.requireNonNull(usernameEditText.getText()).toString();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString();


                boolean success = dbHelper.loginAdmin(username, password);

                if (success) {
                    // Admin login was successful

                    // Start the AdminDashboardActivity
                    Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);

                    // Finish the current activity if needed
                    finish();
                } else {
                    // Admin login failed
                    // Display an error message or perform error handling here
                    Toast.makeText(AdminLoginActivity.this, "Admin login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
