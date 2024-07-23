package com.example.hms.reception;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.Appointment;
import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentsActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private List<Appointment> appointmentsList;
    private AppointmentsAdapter appointmentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointments);

        // Get a reference to the LinearLayout where appointment details will be displayed
        ListView appointmentsListView = findViewById(R.id.appointmentsListView);
        appointmentsList = new ArrayList<>();

        // Retrieve appointment details from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAppointmentDetails();

        // Check if there are records in the cursor
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    // Extract data from the cursor
                    @SuppressLint("Range") String patientName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_NAME));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_GENDER));
                    @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_AGE));
                    @SuppressLint("Range") String appointmentDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_APPOINTMENT_DATE));
                    @SuppressLint("Range") String appointmentTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_APPOINTMENT_TIME));
                    @SuppressLint("Range") String doctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_DOCTOR));
                    @SuppressLint("Range") String disease = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_DISEASE));
                    @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.APPOINTMENT_COLUMN_ADDRESS));

                    // Create an Appointment object
                    Appointment appointment = new Appointment(patientName, gender, age, appointmentDate, appointmentTime, doctorName, disease, address);
                    appointmentsList.add(appointment);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        databaseHelper.close();

        // Set up the adapter and list view
        appointmentsAdapter = new AppointmentsAdapter(this, R.layout.appointment_item, appointmentsList);
        appointmentsListView.setAdapter(appointmentsAdapter);

        // Set item click listener for deletion
        appointmentsListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the appointment at the clicked position
            Appointment clickedAppointment = appointmentsList.get(position);

            // Delete the appointment from the database
            databaseHelper.deleteAppointment(clickedAppointment);

            // Remove the appointment from the list
            appointmentsList.remove(position);
            appointmentsAdapter.notifyDataSetChanged();
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
