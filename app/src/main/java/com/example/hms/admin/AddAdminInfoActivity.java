package com.example.hms.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

public class AddAdminInfoActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private DatabaseHelper dbHelper;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addadmin);

        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.editTextNewUsername);
        passwordEditText = findViewById(R.id.editTextNewPassword);

        Button addButton = findViewById(R.id.buttonAddAdminInfo);
        addButton.setOnClickListener(view -> addAdmin());
    }

    private void addAdmin() {
        String newUsername = usernameEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            long result = dbHelper.insertAdmin(newUsername, newPassword);

            if (result != -1) {
                // Successfully added new admin
                Toast.makeText(AddAdminInfoActivity.this, "Admin added successfully", Toast.LENGTH_SHORT).show();

                // Navigate back to AdminListActivity
                Intent intent = new Intent(AddAdminInfoActivity.this, listAdminAdminActivity.class);
                startActivity(intent);

                // Finish the current activity to remove it from the back stack
                finish();
            } else {
                // Failed to add admin
                Toast.makeText(AddAdminInfoActivity.this, "Failed to add admin", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Username or password is empty
            Toast.makeText(AddAdminInfoActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
        }
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
