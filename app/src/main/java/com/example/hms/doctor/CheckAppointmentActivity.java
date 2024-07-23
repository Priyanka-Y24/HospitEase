package com.example.hms.doctor;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.DoctorProfile;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;

/** @noinspection ALL*/
public class CheckAppointmentActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private ListView listViewAppointments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_appointment);

        // Get logged-in username from Intent
        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        // Fetch doctor's name from DoctorProfile table based on the logged-in username
        String loggedInDoctorName = getDoctorName(loggedInUsername);

        listViewAppointments = findViewById(R.id.listViewAppointments);

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
                // Toggle strikethrough on the clicked item
                TextView textView = (TextView) view;
                toggleStrikethrough(textView);
            }
        });

        listViewAppointments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Long click: Remove strikethrough
                TextView textView = (TextView) view;
                removeStrikethrough(textView);
                return true;
            }
        });
    }

    private void toggleStrikethrough(TextView textView) {
        // Toggle strikethrough on the TextView
        if ((textView.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
            // Remove strikethrough
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // Add strikethrough
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void removeStrikethrough(TextView textView) {
        // Remove strikethrough on the TextView
        textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
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
