package com.example.hms.reception;

import static com.example.hms.R.id;
import static com.example.hms.R.layout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentSchedulingActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private EditText editTextFullName, editTextAge, editTextAppointmentDate, editTextAppointmentTime,
            editTextDisease, editTextAddress;
    private RadioGroup radioGroupGender;
    private Spinner doctorSpinner;
    private DatabaseHelper databaseHelper;

    // Add other necessary variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.appointment_scheduling); // Replace with the actual layout name
        databaseHelper = new DatabaseHelper(this);
        // Initialize views
        editTextFullName = findViewById(id.editTextFullName);
        editTextAge = findViewById(id.editTextAge);
        editTextAppointmentDate = findViewById(id.editTextAppointmentDate);
        editTextAppointmentTime = findViewById(id.editTextAppointmentTime);
        editTextDisease = findViewById(id.editTextDisease);
        editTextAddress = findViewById(id.editTextAddress);
        radioGroupGender = findViewById(id.radioGroupGender);
        Button datePickerButton = findViewById(id.datePickerButton);
        Button timePickerButton = findViewById(id.timePickerButton);
        doctorSpinner = findViewById(id.doctorSpinner);
        Button buttonSave = findViewById(id.buttonSave);

        // Populate the doctor spinner with items from the doctor table
        populateDoctorSpinner();

        // Set click listeners
        datePickerButton.setOnClickListener(v -> showDatePicker());

        timePickerButton.setOnClickListener(v -> showTimePicker());

        buttonSave.setOnClickListener(v -> saveAppointment());
    }

    private void populateDoctorSpinner() {
        // Fetch doctor names from the database (replace with your actual implementation)
        String[] doctorNames = getDoctorNamesFromDatabase();

        // Create an ArrayAdapter using the doctor names and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        doctorSpinner.setAdapter(adapter);

        // Set item click listener for the doctor spinner
        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selection if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private String[] getDoctorNamesFromDatabase() {
        // TODO: Replace this with your actual database query to fetch doctor names
        // For now, using dummy data
        Cursor cursor = databaseHelper.getDoctorNames(); // Assuming getDoctorNames() is a method in your DatabaseHelper to retrieve doctor names

        // Check if the cursor is not null and has data
        if (cursor != null && cursor.moveToFirst()) {
            List<String> doctorNames = new ArrayList<>();

            do {
                // Assuming the column index for the doctor name is 0, replace it with the actual index
                String doctorName = cursor.getString(0);
                doctorNames.add(doctorName);
            } while (cursor.moveToNext());

            // Convert the list to an array
            return doctorNames.toArray(new String[0]);
        } else {
            // Handle the case where no doctor names are found
            return new String[]{};
        }
    }


    private void showDatePicker() {
        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Update the EditText with the selected date
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextAppointmentDate.setText(selectedDate);
                },
                year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Get the current time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    // Update the EditText with the selected time
                    String selectedTime = hourOfDay + ":" + minute1;
                    editTextAppointmentTime.setText(selectedTime);
                },
                hour, minute, false);

        // Show the time picker dialog
        timePickerDialog.show();
    }

    private void saveAppointment() {
        // Retrieve values from UI elements
        String fullName = editTextFullName.getText().toString();
        String age = editTextAge.getText().toString();
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        String gender = getGenderFromId(selectedGenderId);
        String appointmentDate = editTextAppointmentDate.getText().toString();
        String appointmentTime = editTextAppointmentTime.getText().toString();
        String selectedDoctor = doctorSpinner.getSelectedItem().toString(); // Get selected doctor from spinner
        String disease = editTextDisease.getText().toString();
        String address = editTextAddress.getText().toString();

        // Perform any validation if needed

        // Insert appointment details into the database
        long newRowId = databaseHelper.insertAppointment(fullName, age, gender, appointmentDate,
                appointmentTime, selectedDoctor, disease, address);

        // Display a toast message with the result
        String saveMessage;
        if (newRowId != -1) {
            saveMessage = "Appointment saved successfully!";
            // Finish the current activity to prevent going back to it from the main activity
            finish();
        } else {
            saveMessage = "Failed to save appointment. Please try again.";
        }
        Toast.makeText(this, saveMessage, Toast.LENGTH_SHORT).show();
    }

    private String getGenderFromId(int selectedGenderId) {
        if (selectedGenderId == R.id.radioButtonMale) {
            return "Male";
        } else if (selectedGenderId == R.id.radioButtonFemale) {
            return "Female";
        } else if (selectedGenderId == R.id.radioButtonOther) {
            return "Other";
        } else {
            return ""; // Handle the default case as needed
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
