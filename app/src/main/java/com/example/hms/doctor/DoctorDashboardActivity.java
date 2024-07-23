package com.example.hms.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.ExampleAsyncTask;
import com.example.hms.MainActivity;
import com.example.hms.R;

public class DoctorDashboardActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_dashboard);
        new ExampleAsyncTask(this).execute();
        // Get references to the buttons
        Button btnMyProfile = findViewById(R.id.btnAppointment);
        Button btnAP = findViewById(R.id.viewAppoints);
        Button btnNewAppointment = findViewById(R.id.btnNewAppointment);
        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        // Set click listeners for the buttons
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open MyProfileActivity
                startDoctorProfileActivity(loggedInUsername);
            }
            private void startDoctorProfileActivity(String loggedInUsername) {
                Intent intent = new Intent(DoctorDashboardActivity.this, DoctorProfileActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);

            }
        });

        btnAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open AP
                startDoctorProfileActivity(loggedInUsername);
            }
            private void startDoctorProfileActivity(String loggedInUsername) {
                Intent intent = new Intent(DoctorDashboardActivity.this, PatientlistDiagnosis.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);

            }
        });

        btnNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open NewAppointmentActivity
                startDoctorProfileActivity(loggedInUsername);
            }
            private void startDoctorProfileActivity(String loggedInUsername) {
                Intent intent = new Intent(DoctorDashboardActivity.this, CheckAppointmentActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);

            }
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
