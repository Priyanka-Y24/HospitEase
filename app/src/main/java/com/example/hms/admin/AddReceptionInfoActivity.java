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

public class AddReceptionInfoActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private DatabaseHelper dbHelper;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecep);

        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.editTextNewUsername);
        passwordEditText = findViewById(R.id.editTextNewPassword);

        Button addButton = findViewById(R.id.buttonAddreceptionInfo);
        addButton.setOnClickListener(view -> addreception());
    }

    private void addreception() {
        String newUsername = usernameEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            long result = dbHelper.insertRecep(newUsername, newPassword);

            if (result != -1) {
                // Successfully added new reception
                Toast.makeText(AddReceptionInfoActivity.this, "receptionist added successfully", Toast.LENGTH_SHORT).show();

                // Navigate back to receptionListActivity
                Intent intent = new Intent(AddReceptionInfoActivity.this, listRecepAdminActivity.class);
                startActivity(intent);

                // Finish the current activity to remove it from the back stack
                finish();
            } else {
                // Failed to add reception
                Toast.makeText(AddReceptionInfoActivity.this, "Failed to add receptionist", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Username or password is empty
            Toast.makeText(AddReceptionInfoActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
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
