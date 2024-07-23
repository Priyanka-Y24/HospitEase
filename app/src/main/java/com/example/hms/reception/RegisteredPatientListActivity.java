package com.example.hms.reception;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;

public class RegisteredPatientListActivity extends AppCompatActivity {
    private ListView listView;
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_patient_list);

        listView = findViewById(R.id.listViewPatients);

        // Fetch and display patient records
        displayPatients();
    }

    private void displayPatients() {
        // Assuming you have a DatabaseHelper class with a method to get all patients
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<String> patientsList = databaseHelper.getAllPatients();

        // Use an ArrayAdapter to populate the ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patientsList);
        listView.setAdapter(arrayAdapter);
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

