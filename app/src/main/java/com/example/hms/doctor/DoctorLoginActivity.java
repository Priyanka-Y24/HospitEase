package com.example.hms.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.DoctorProfile;
import com.example.hms.R;

/** @noinspection ALL*/
public class DoctorLoginActivity extends AppCompatActivity {

    public DoctorLoginActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Button loginButton = findViewById(R.id.loginButton);

        final EditText usernameEditText = findViewById(R.id.doctorUsername);
        final EditText passwordEditText = findViewById(R.id.doctorPassword);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login
                String loggedInUsername = databaseHelper.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());

                // Check if login was successful
                if (loggedInUsername != null) {
                    DoctorProfile existingProfile =databaseHelper.getDoctorProfile(loggedInUsername);

                    // Update successful
                    if (existingProfile != null) {
                        // Redirect to the doctor's dashboard or another activity
                        startDoctorProfileActivity(loggedInUsername);
                    } else {
                        // Insert a new doctor profile if the update fails
                        boolean insertSuccessful = databaseHelper.insertDoctorProfile(
                                loggedInUsername,
                                "Dr. ABC", // Example name
                                "MBBS",
                                "Cardiologist",
                                "Contact details",
                                "11 years",
                                null // Example: you can pass the profile photo data here if available
                        );

                        // Check if the insert was successful
                        if (insertSuccessful) {
                            startDoctorProfileActivity(loggedInUsername);
                        } else {
                            Toast.makeText(DoctorLoginActivity.this, "Insert or update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Login failed
                    // Handle failure, show an error message, etc.
                    Toast.makeText(DoctorLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void startDoctorProfileActivity(String loggedInUsername) {
                Intent intent = new Intent(DoctorLoginActivity.this, DoctorDashboardActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);
                finish();
            }
        }



