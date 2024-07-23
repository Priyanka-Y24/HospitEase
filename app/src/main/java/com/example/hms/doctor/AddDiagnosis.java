package com.example.hms.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

/** @noinspection ALL*/
public class AddDiagnosis extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private EditText assessmentEditText, planEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diagnosis);
        String patientName = getIntent().getStringExtra("PATIENT_NAME");
        setTitle("Add Diagnosis for " + patientName);
        // Initialize views
        TextView appointmentDetailsContentTextView = findViewById(R.id.appointmentDetailsContentTextView);
        assessmentEditText = findViewById(R.id.assessmentEditText);
        planEditText = findViewById(R.id.planEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button printButton = findViewById(R.id.printButton);
        // Get appointment details from the previous activity
        String appointmentDetails = getIntent().getStringExtra("APPOINTMENT_DETAILS");
        appointmentDetailsContentTextView.setText(appointmentDetails);

        // Save button click listener
        saveButton.setOnClickListener(v -> saveAssessmentAndPlan());

        // Print button click listener
        printButton.setOnClickListener(v -> printAssessmentAndPlan());
    }

    private void saveAssessmentAndPlan() {
        // Get patient name from appointment details (you may need to parse it)
        String patientName = getIntent().getStringExtra("PATIENT_NAME");
        // Get assessment and plan from EditTexts
        String assessment = assessmentEditText.getText().toString().trim();
        String plan = planEditText.getText().toString().trim();

        // Perform the save operation in your database
        // You need to implement a DatabaseHelper class for database operations

        // Example: Save to a new table named "AssessmentPlanTable"
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean saveSuccessful = databaseHelper.saveAssessmentAndPlan(patientName, assessment, plan);

        if (saveSuccessful) {
            Toast.makeText(this, "Assessment and Plan saved successfully", Toast.LENGTH_SHORT).show();
            // Finish the current activity to prevent going back to it from the main activity
            finish();
        } else {
            Toast.makeText(this, "Failed to save Assessment and Plan", Toast.LENGTH_SHORT).show();
        }
    }

    private void printAssessmentAndPlan() {
        // Retrieve the saved assessment and plan details from the database
        // You need to implement a DatabaseHelper class for database operations

        String patientName = getIntent().getStringExtra("PATIENT_NAME");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String assessment = databaseHelper.getAssessment(patientName);
        String plan = databaseHelper.getPlan(patientName);

        // Create a WebView to hold the content for printing
        WebView webView = new WebView(this);
        String contentToPrint = "Patient: " + patientName + "<br/>"
                + "Assessment: " + assessment + "<br/>"
                + "Plan: " + plan;

        // Load the content into the WebView
        webView.loadDataWithBaseURL(null, contentToPrint, "text/html", "UTF-8", null);

        // Create a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        // Set the print job name
        String jobName = getString(R.string.app_name) + " Document";

        // Start a print job
        PrintJob printJob = printManager.print(jobName, new MyPrintDocumentAdapter(webView.createPrintDocumentAdapter()), null);
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
