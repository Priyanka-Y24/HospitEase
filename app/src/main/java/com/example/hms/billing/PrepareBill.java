package com.example.hms.billing;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrepareBill extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private EditText editTextPatientName, editTextDoctorVisited, editTextTotalAmount;
    private RadioGroup radioGroupPaymentMethod;
    private TextView textViewSelectedDate;

    private DatabaseHelper databaseHelper;

    private Calendar selectedDateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepare_bill);

        // Initialize your UI components
        editTextPatientName = findViewById(R.id.editTextPatientName);
        editTextDoctorVisited = findViewById(R.id.editTextDoctorVisited);
        editTextTotalAmount = findViewById(R.id.editTextTotalAmount);
        radioGroupPaymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        Button buttonPickDate = findViewById(R.id.buttonPickDate);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize Calendar for date picking
        selectedDateCalendar = Calendar.getInstance();

        // Set click listeners
        buttonPickDate.setOnClickListener(view -> showDatePickerDialog());

        buttonSave.setOnClickListener(view -> {
            // Get entered details
            String patientName = editTextPatientName.getText().toString();
            String doctorVisited = editTextDoctorVisited.getText().toString();
            String totalAmount = editTextTotalAmount.getText().toString();
            int selectedPaymentMethodId = radioGroupPaymentMethod.getCheckedRadioButtonId();
            RadioButton selectedPaymentMethod = findViewById(selectedPaymentMethodId);
            String paymentMethod = selectedPaymentMethod.getText().toString();
            String selectedDate = textViewSelectedDate.getText().toString();

            // Perform database insertion
            long rowId = databaseHelper.insertData(patientName, doctorVisited, totalAmount, paymentMethod, selectedDate);

            if (rowId != -1) {
                showSuccessMessage();
                navigateToAnotherPage();
            } else {
                showErrorMessage();
            }
        });

        buttonCancel.setOnClickListener(view -> {
            startActivity(new Intent(PrepareBill.this, AccountDashboardActivity.class));
            finish();
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PrepareBill.this,
                (datePicker, year, month, day) -> {
                    selectedDateCalendar.set(Calendar.YEAR, year);
                    selectedDateCalendar.set(Calendar.MONTH, month);
                    selectedDateCalendar.set(Calendar.DAY_OF_MONTH, day);

                    updateSelectedDate();
                },
                selectedDateCalendar.get(Calendar.YEAR),
                selectedDateCalendar.get(Calendar.MONTH),
                selectedDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set the minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void updateSelectedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = dateFormat.format(selectedDateCalendar.getTime());
        textViewSelectedDate.setText(formattedDate);
    }

    private void showSuccessMessage() {
        Toast.makeText(PrepareBill.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
    }

    private void navigateToAnotherPage() {
        startActivity(new Intent(PrepareBill.this, AccountDashboardActivity.class));
        finish();
    }

    private void showErrorMessage() {
        Toast.makeText(PrepareBill.this, "Error inserting data", Toast.LENGTH_SHORT).show();
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
