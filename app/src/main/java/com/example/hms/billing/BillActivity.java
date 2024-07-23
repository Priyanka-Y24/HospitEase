package com.example.hms.billing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

/** @noinspection ALL*/
public class BillActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";

    public BillActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        String selectedPatientName = getIntent().getStringExtra("SELECTED_PATIENT_NAME");
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");

        ListView listViewBill = findViewById(R.id.listViewBill);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getBillRecords(selectedPatientName, selectedDate);

        if (cursor != null && cursor.getCount() > 0) {
            String[] fromColumns = {
                    "_id",  // Include the _id column
                     DatabaseHelper.COLUMN_PATIENT_NAME_BILL,
                    DatabaseHelper.COLUMN_DOCTOR_VISITED,
                    DatabaseHelper.COLUMN_TOTAL_AMOUNT,
                    DatabaseHelper.COLUMN_PAYMENT_METHOD,
                    DatabaseHelper.COLUMN_VISIT_DATE
                    // Add more column names as needed
            };

            int[] toViews = {
                    R.id.billidTextView,
                     R.id.patientNameTextView,
                    R.id.doctorVisitedTextView,
                    R.id.totalAmountTextView,
                    R.id.paymentMethodTextView,
                    R.id.visitDateTextView,
                    // Add the print button ID
                    // Add more IDs as needed
            };


            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item_bill, cursor, fromColumns, toViews, 0) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    // Handle the print button click for each item
                    Button printButton = view.findViewById(R.id.printButton);
                    printButton.setTag(position);
                    printButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int clickedPosition = (int) v.getTag();
                            // Open system print page for the item at the clicked position
                            openPrintPage(cursor, clickedPosition);
                        }
                    });

                    return view;
                }
            };

            listViewBill.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No records found for " + selectedPatientName + " on " + selectedDate, Toast.LENGTH_SHORT).show();
        }
    }
    // Method to open system print page for the selected item
    private void openPrintPage(Cursor cursor, int position) {
        // Extract data for the selected item from the cursor
        cursor.moveToPosition(position);
        @SuppressLint("Range") String patientName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATIENT_NAME_BILL));
        @SuppressLint("Range") String doctorVisited = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOCTOR_VISITED));
        @SuppressLint("Range") String totalAmount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL_AMOUNT));
        @SuppressLint("Range") String paymentMethod = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PAYMENT_METHOD));
        @SuppressLint("Range") String visitDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VISIT_DATE));

        // Retrieve assessment and plan details from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String assessment = databaseHelper.getAssessment(patientName);
        String plan = databaseHelper.getPlan(patientName);

        // Create a WebView to hold the content for printing
        WebView webView = new WebView(this);
        String contentToPrint = "Patient Name: " + patientName +
                "<br/>Doctor Visited: " + doctorVisited +
                "<br/>Total Amount: " + totalAmount +
                "<br/>Payment Method: " + paymentMethod +
                "<br/>Visit Date: " + visitDate +
                "<br/><br/>Assessment: " + assessment +
                "<br/>Plan: " + plan;
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
