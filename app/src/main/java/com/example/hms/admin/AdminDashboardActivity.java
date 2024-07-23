package com.example.hms.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.MainActivity;
import com.example.hms.R;

public class AdminDashboardActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        findViewById(R.id.cardAddEditDoctor).setOnClickListener(view -> {
            // Handle the click event for the "Add/Edit Doctor" card
            Toast.makeText(AdminDashboardActivity.this, "Add/Edit Doctor Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic for this card here
            // Start the AddAdminTypeActivity
            Intent intent = new Intent(AdminDashboardActivity.this, listDoctAdminActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cardAddEditReceptionist).setOnClickListener(view -> {
            // Handle the click event for the "Add/Edit Receptionist" card
            Toast.makeText(AdminDashboardActivity.this, "Add/Edit Receptionist Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic for this card here
            // Start the AddAdminTypeActivity
            Intent intent = new Intent(AdminDashboardActivity.this, listRecepAdminActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cardAddEditBilling).setOnClickListener(view -> {
            // Handle the click event for the "Add/Edit Billing" card
            Toast.makeText(AdminDashboardActivity.this, "Add/Edit Billing Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic for this card here
            // Start the AddAdminTypeActivity
            Intent intent = new Intent(AdminDashboardActivity.this, listAccAdminActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cardAddEditAdmin).setOnClickListener(view -> {
            // Handle the click event for the "Add/Edit Admin" card
            Toast.makeText(AdminDashboardActivity.this, "Add/Edit Admin Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic for this card here
            // Start the AddAdminTypeActivity
            Intent intent = new Intent(AdminDashboardActivity.this, listAdminAdminActivity.class);
            startActivity(intent);
        });

        // Repeat for other cards if needed
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
