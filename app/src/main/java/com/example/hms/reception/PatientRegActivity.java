package com.example.hms.reception;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

public class PatientRegActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private EditText editTextName, editTextAge, editTextContact, editTextEmail, editTextAddress;
    private RadioGroup radioGroupGender;
    private DatabaseHelper databaseHelper; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_reg);

        // Find views by their IDs
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        editTextContact = findViewById(R.id.editTextContact);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        // Create a single instance of DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the Register button
        buttonRegister.setOnClickListener(v -> {
            // Handle Register button click
            registerPatient();
        });
    }

    private void registerPatient() {
        // Retrieve values from UI elements
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        String gender = getGenderFromId(selectedGenderId);
        String contact = editTextContact.getText().toString();
        String email = editTextEmail.getText().toString();
        String address = editTextAddress.getText().toString();

        // Perform any validation if needed

        // Insert patient details into the database
        long newRowId = databaseHelper.insertPatient(name, age, gender, contact, email, address);

        // Display a toast message with the registration details (for demonstration purposes)
        String registrationMessage;
        if (newRowId != -1) {
            registrationMessage = "Patient registered successfully!";
            // Finish the current activity to prevent going back to it from the main activity
            finish();
        } else {
            registrationMessage = "Registration failed. Please try again.";
        }
        Toast.makeText(this, registrationMessage, Toast.LENGTH_LONG).show();
    }

    private String getGenderFromId(int selectedId) {
        RadioButton radioButton = findViewById(selectedId);
        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
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
