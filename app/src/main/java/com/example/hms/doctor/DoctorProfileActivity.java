package com.example.hms.doctor;
// DoctorProfileActivity.java
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hms.DatabaseHelper;
import com.example.hms.DoctorProfile;
import com.example.hms.ExampleAsyncTask;
import com.example.hms.MainActivity;
import com.example.hms.R;

/** @noinspection ALL*/
public class DoctorProfileActivity extends AppCompatActivity {
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doct_profile);
        new ExampleAsyncTask(this).execute();
        // Initialize TextViews and ImageView
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView educationTextView = findViewById(R.id.educationTextView);
        TextView specializationTextView = findViewById(R.id.specializationTextView);
        TextView contactInformationTextView = findViewById(R.id.contactInformationTextView);
        TextView experienceTextView = findViewById(R.id.experienceTextView);
        ImageView profilePhotoImageView = findViewById(R.id.profilePhotoImageView);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        // Get logged-in username from Intent
        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");
        // Set the click listener for the Edit Profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                // You may start a new activity for editing the profile here
                // For example:
                startDoctorProfileActivity(loggedInUsername);
            }
            private void startDoctorProfileActivity(String loggedInUsername) {
                Intent intent = new Intent(DoctorProfileActivity.this, EditDoctorProfileActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);
                finish();
            }
        });




        // Get doctor profile based on the logged-in username
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DoctorProfile doctorProfile = databaseHelper.getDoctorProfileByUsername(loggedInUsername);

        // Display doctor profile information
        if (doctorProfile != null) {
            usernameTextView.setText("Username: " + doctorProfile.getUsername());
            nameTextView.setText("Name: " + doctorProfile.getName());
            educationTextView.setText("Education: " + doctorProfile.getEducation());

            specializationTextView.setText("Specialization: " + doctorProfile.getSpecialization());
            contactInformationTextView.setText("Contact Information: " + doctorProfile.getContactInformation());
            experienceTextView.setText("Experience: " + doctorProfile.getExperience());

            // Load profile photo (Assuming you have a method to load an image into an ImageView)
            // You might want to use a library like Glide or Picasso for efficient image loading
            loadImageIntoImageView(doctorProfile.getProfilePhoto(), profilePhotoImageView);

        }
    }

    // A sample method to load an image into an ImageView (you may need to adjust this based on your implementation)
    private void loadImageIntoImageView(byte[] profilePhotoData, ImageView imageView) {
        if (profilePhotoData != null && profilePhotoData.length > 0) {
            Bitmap profileBitmap = BitmapFactory.decodeByteArray(profilePhotoData, 0, profilePhotoData.length);

            Glide.with(this)
                    .load(profileBitmap)
                    .placeholder(R.drawable.doct) // Placeholder image while loading (optional)
                    .error(R.drawable.err)     // Image to display in case of an error (optional)
                    .into(imageView);
        } else {
            // Handle the case where profilePhotoData is null or empty
            // For example, you can set a default image or show an error message
            imageView.setImageResource(R.drawable.dpp);
        }
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

