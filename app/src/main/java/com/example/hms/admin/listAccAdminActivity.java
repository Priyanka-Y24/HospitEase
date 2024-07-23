package com.example.hms.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.Accountant;
import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;
import java.util.List;

public class listAccAdminActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private DatabaseHelper dbHelper;
    private ListView listView;
    private AccListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_acc_admin);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        displayaccs();
        setupItemClick();
        setupAddButtonClick(); // Call the method to set up "Add" button click

    }

    private void displayaccs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Accountant> accs = new ArrayList<>();

        // Query the database and populate the accs list
        Cursor cursor = db.query(DatabaseHelper.ACCOUNTANT_TABLE_NAME,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

            Accountant acc = new Accountant(id, username, password);
            accs.add(acc);
        }

        cursor.close();
        db.close();

        // Use the custom adapter to display the data in the ListView
        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Replace with your actual DatabaseHelper instantiation
        adapter = new AccListAdapter(this, accs, databaseHelper);
        listView.setAdapter(adapter);
    }

    private void setupItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                Accountant selectedAccountant = (Accountant) parent.getItemAtPosition(position);
                if (selectedAccountant != null) {
                    // Open your edit page with the details of selectedAccountant
                    openEditPage(selectedAccountant);
                }
            }
        });
    }
    private void openEditPage(Accountant accountant) {
        // Implement the logic to open your edit page with the details of the selected accountant
        // For example, you can start a new activity or show a dialog.
        // Here, I'm showing a Toast message as an example.
        Toast.makeText(this, "Clicked on " + accountant.getUsername(), Toast.LENGTH_SHORT).show();

        // If you want to open a new activity, you can do something like this:
         Intent intent = new Intent(this, Edit_accinfo.class);
        intent.putExtra("accountantId", accountant.getId());
         startActivity(intent);
    }
    public void onDeleteButtonClick(View view) {
        // Find the position of the clicked item
        int position = listView.getPositionForView(view);

        // Check if adapter is not null before calling deleteItem
        if (adapter != null) {
            adapter.deleteItem(position);

            // Notify the adapter that the data set has changed
            adapter.notifyDataSetChanged();
        }
    }


    private void setupAddButtonClick() {
        Button addButton = findViewById(R.id.addButton); // Adjust the ID based on your XML layout
        addButton.setOnClickListener(view -> {
            // Open AddaccInfoActivity when the "Add" button is clicked
            Intent intent = new Intent(listAccAdminActivity.this, AddAccInfoActivity.class);
            startActivity(intent);
            // Finish the current activity to prevent going back to it from the main activity
            finish();
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
