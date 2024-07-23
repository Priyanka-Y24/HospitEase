package com.example.hms.billing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewBill extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private Spinner spinnerPatientNames;
    private DatePicker datePicker;
    private TextView textViewSelectedDate;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bill);

        spinnerPatientNames = findViewById(R.id.spinnerPatientNames);
        datePicker = findViewById(R.id.datePicker);
        Button buttonViewDetails = findViewById(R.id.buttonViewDetails);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize patient names list from the database
        List<String> patientNames = getPatientNamesFromDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, patientNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatientNames.setAdapter(adapter);

        buttonViewDetails.setOnClickListener(v -> showDetails());

        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                null);

        // Set a listener to update the selected date
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> updateSelectedDate());
        }
    }

    private List<String> getPatientNamesFromDatabase() {
        List<String> names = new ArrayList<>();
        Cursor cursor = databaseHelper.getPatientNames();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATIENT_NAME_BILL));
                names.add(name);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return names;
    }

    private void showDetails() {
        String selectedPatientName = spinnerPatientNames.getSelectedItem().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Months are zero-indexed
        int year = datePicker.getYear();

        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);


        // Pass the selected patient name and date to the BillActivity
        Intent intent = new Intent(ViewBill.this, BillActivity.class);
        intent.putExtra("SELECTED_PATIENT_NAME", selectedPatientName);
        intent.putExtra("SELECTED_DATE", selectedDate);
        startActivity(intent);
    }

    private void updateSelectedDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Months are zero-indexed
        int year = datePicker.getYear();

        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
        textViewSelectedDate.setText(selectedDate);
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
