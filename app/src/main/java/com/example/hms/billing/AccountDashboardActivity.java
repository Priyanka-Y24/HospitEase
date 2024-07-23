package com.example.hms.billing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.hms.MainActivity;
import com.example.hms.R;

public class AccountDashboardActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_dashboard);

        // Find the CardViews by their IDs
        CardView prepareBillCardView = findViewById(R.id.prepareBillCardView);
        CardView viewBillsCardView = findViewById(R.id.viewBillsCardView);

        // Set click listeners for the CardViews
        prepareBillCardView.setOnClickListener(v -> {
            // Handle click for Prepare-Bill CardView
            Toast.makeText(AccountDashboardActivity.this, "Prepare-Bill Clicked", Toast.LENGTH_SHORT).show();
            // Redirect to the next activity/page (replace NextActivity.class with your desired activity)
            startActivity(new Intent(AccountDashboardActivity.this, PrepareBill.class));
        });

        viewBillsCardView.setOnClickListener(v -> {
            // Handle click for View-Bills CardView
            Toast.makeText(AccountDashboardActivity.this, "View-Bills Clicked", Toast.LENGTH_SHORT).show();
            // Redirect to the next activity/page (replace NextActivity.class with your desired activity)
            startActivity(new Intent(AccountDashboardActivity.this, ViewBill.class));
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
