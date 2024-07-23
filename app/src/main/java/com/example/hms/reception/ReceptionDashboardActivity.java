package com.example.hms.reception;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.MainActivity;
import com.example.hms.R;

public class ReceptionDashboardActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reception_dashboard);

        // Get references to buttons
        Button btnAppointment = findViewById(R.id.btnAppointment);
        Button viewAppoints = findViewById(R.id.viewAppoints);
        Button btnNewAppointment = findViewById(R.id.btnNewAppointment);
        Button btnCheckInRegistration = findViewById(R.id.btnCheckInRegistration);

        // Set click listeners
        btnAppointment.setOnClickListener(view -> {
            // Handle button click for patient registration
            Toast.makeText(ReceptionDashboardActivity.this, "Patient Registration Clicked", Toast.LENGTH_SHORT).show();
            // Add intent to start the next activity (replace NextActivity.class with the actual class name)
            Intent intent = new Intent(ReceptionDashboardActivity.this, PatientRegActivity.class);
            startActivity(intent);
        });

        viewAppoints.setOnClickListener(view -> {
            // Handle button click for view appointments
            Toast.makeText(ReceptionDashboardActivity.this, "View Appointments Clicked", Toast.LENGTH_SHORT).show();
            // Add intent to start the next activity (replace NextActivity.class with the actual class name)
            Intent intent = new Intent(ReceptionDashboardActivity.this, ViewAppointmentsActivity.class);
            startActivity(intent);
        });

        btnNewAppointment.setOnClickListener(view -> {
            // Handle button click for appointment scheduling
            Toast.makeText(ReceptionDashboardActivity.this, "Appointment Scheduling Clicked", Toast.LENGTH_SHORT).show();
            // Add intent to start the next activity (replace NextActivity.class with the actual class name)
            Intent intent = new Intent(ReceptionDashboardActivity.this, AppointmentSchedulingActivity.class);
            startActivity(intent);
        });

        btnCheckInRegistration.setOnClickListener(view -> {
            // Handle button click for check-in/out
            Toast.makeText(ReceptionDashboardActivity.this, "patient list Clicked", Toast.LENGTH_SHORT).show();
            // Add intent to start the next activity (replace NextActivity.class with the actual class name)
            Intent intent = new Intent(ReceptionDashboardActivity.this, RegisteredPatientListActivity.class);
            startActivity(intent);
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
