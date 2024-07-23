package com.example.hms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.admin.AdminLoginActivity;
import com.example.hms.billing.AccountLoginActivity;
import com.example.hms.doctor.DoctorLoginActivity;
import com.example.hms.reception.ReceptionLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button doctorButton = findViewById(R.id.doctorButton);
        Button receptionButton = findViewById(R.id.receptionButton);
        Button accountButton = findViewById(R.id.accountButton);
        Button adminButton = findViewById(R.id.adminButton);

        doctorButton.setOnClickListener(v -> {
            // Handle the click event for the doctor button
            // Start Doctor's login or signup activity
            Intent intent = new Intent(MainActivity.this, DoctorLoginActivity.class);
            startActivity(intent);
        });

        receptionButton.setOnClickListener(v -> {
            // Handle the click event for the reception button
            // Start Receptionist's login or signup activity
            Intent intent = new Intent(MainActivity.this, ReceptionLoginActivity.class);
            startActivity(intent);
        });

        accountButton.setOnClickListener(v -> {
            // Handle the click event for the account button
            // Start Accountant's login or signup activity
            Intent intent = new Intent(MainActivity.this, AccountLoginActivity.class);
            startActivity(intent);
        });

        adminButton.setOnClickListener(v -> {
            // Handle the click event for the account button
            // Start Accountant's login or signup activity
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}
