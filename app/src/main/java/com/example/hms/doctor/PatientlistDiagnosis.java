package com.example.hms.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.DoctorProfile;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;

/** @noinspection ALL*/
public class PatientlistDiagnosis extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private ListView listViewAppointments;

    public PatientlistDiagnosis() {
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patientdiagnosis);

        // Get logged-in username from Intent
        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        // Fetch doctor's name from DoctorProfile table based on the logged-in username
        String loggedInDoctorName = getDoctorName(loggedInUsername);

        listViewAppointments = findViewById(R.id.listViewAppointments1);

        // Fetch and display doctor's appointments
        displayAppointments(loggedInDoctorName);
    }

    private String getDoctorName(String username) {
        // Assuming you have a DatabaseHelper class with a method to get doctor's name by username
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DoctorProfile doctorProfile = databaseHelper.getDoctorProfileByUsername(username);

        if (doctorProfile != null) {
            return doctorProfile.getName();
        } else {
            return null; // Handle the case where the doctor profile is not found
        }
    }

    private void displayAppointments(String doctorName) {
        // Assuming you have a DatabaseHelper class with a method to get appointments for a doctor
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<String> appointmentsList = databaseHelper.getDoctorAppointments(doctorName);

        // Use an ArrayAdapter to populate the ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointmentsList);
        listViewAppointments.setAdapter(arrayAdapter);

        // Set a click listener for the items in the ListView
        listViewAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item text
                String clickedItemText = (String) parent.getItemAtPosition(position);

                // Open the AddDiagnosis page with the clicked item information
                openAddDiagnosisPage(clickedItemText);
            }
        });
    }

    private void openAddDiagnosisPage(String appointmentInfo) {
        // Extract the patient's name from the appointmentInfo
        String[] appointmentDetails = appointmentInfo.split("\n");
        String patientName = appointmentDetails[0].replace("Patient: ", "");

        // Start the AddDiagnosis activity and pass the patient's name
        Intent intent = new Intent(PatientlistDiagnosis.this, AddDiagnosis.class);
        intent.putExtra("PATIENT_NAME", patientName);
        startActivity(intent);
        // Finish the current activity to prevent going back to it from the main activity
        finish();
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