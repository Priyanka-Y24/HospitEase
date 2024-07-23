package com.example.hms.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hms.Admin;
import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

public class Edit_admininfo extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private DatabaseHelper dbHelper;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private long adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_admin);

        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        Button updateButton = findViewById(R.id.buttonSave);

        // Retrieve admin ID from Intent
        adminId = getIntent().getIntExtra("adminId", -1);

        // Fetch admin details from the database based on the ID
        Admin admin = dbHelper.getAdminById(adminId);

        // Populate the form with admin details
        if (admin != null) {
            usernameEditText.setText(admin.getUsername());
            passwordEditText.setText(admin.getPassword());
        }

        // Update button click handling
        updateButton.setOnClickListener(view -> {
            // Get updated values from the form
            String newUsername = usernameEditText.getText().toString();
            String newPassword = passwordEditText.getText().toString();

            // Update the admin in the database
            dbHelper.updateAdmin((int) adminId, newUsername, newPassword);

            // Optionally, you can go back to the AdminListActivity or do other actions
            Intent intent = new Intent(Edit_admininfo.this, listAdminAdminActivity.class);
            startActivity(intent);
            finish(); // Optional, to finish the current activity
        });
    }
    public void logoutAndNavigateToMainActivity(View view) {
        clearUserAuthentication();

        // Go to the main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // Finish the current activity to prevent going back to it from the main activity
        finish();
    }

    private void clearUserAuthentication() {
        // Clear user authentication information (adjust this based on your implementation)
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_USER_LOGGED_IN, false);
        editor.apply();
    }
}
