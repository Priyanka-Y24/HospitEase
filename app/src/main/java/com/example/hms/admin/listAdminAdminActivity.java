package com.example.hms.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.Admin;
import com.example.hms.DatabaseHelper;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.util.ArrayList;
import java.util.List;

public class listAdminAdminActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private DatabaseHelper dbHelper;
    private ListView listView;
    private AdminListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_admin_admin);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        displayAdmins();
        setupItemClick();
        setupAddButtonClick(); // Call the method to set up "Add" button click
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
    private void displayAdmins() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Admin> admins = new ArrayList<>();

        // Query the database and populate the admins list
        Cursor cursor = db.query(DatabaseHelper.ADMIN_TABLE_NAME,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

            Admin admin = new Admin(id, username, password);
            admins.add(admin);
        }

        cursor.close();
        db.close();

        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Replace with your actual DatabaseHelper instantiation
        adapter = new AdminListAdapter(this, admins, databaseHelper);
        listView.setAdapter(adapter);
    }

    private void setupItemClick() {
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            // Get the clicked Admin object
            Admin selectedAdmin = (Admin) adapterView.getItemAtPosition(position);

            // Start the EditAdminActivity, passing the Admin ID or necessary data
            Intent intent = new Intent(listAdminAdminActivity.this, Edit_admininfo.class);
            intent.putExtra("adminId", selectedAdmin.getId()); // Pass the Admin ID or necessary data
            startActivity(intent);
            // Finish the current activity to prevent going back to it from the main activity
            finish();
        });
    }

    private void setupAddButtonClick() {
        Button addButton = findViewById(R.id.addButton); // Adjust the ID based on your XML layout
        addButton.setOnClickListener(view -> {
            // Open AddAdminInfoActivity when the "Add" button is clicked
            Intent intent = new Intent(listAdminAdminActivity.this, AddAdminInfoActivity.class);
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
