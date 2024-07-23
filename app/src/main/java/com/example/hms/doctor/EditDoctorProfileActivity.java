package com.example.hms.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.DatabaseHelper;
import com.example.hms.DoctorProfile;
import com.example.hms.MainActivity;
import com.example.hms.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditDoctorProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PREF_NAME = "HMS_Preferences";
    private static final String KEY_USER_LOGGED_IN = "userLoggedIn";
    private EditText nameEditText;
    private EditText educationEditText;
    private EditText specializationEditText;
    private EditText contactInformationEditText;
    private EditText experienceEditText;
    private ImageView profilePhotoImageView;

    private String loggedInUsername;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_doctor_profile);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        educationEditText = findViewById(R.id.educationEditText);
        specializationEditText = findViewById(R.id.specializationEditText);
        contactInformationEditText = findViewById(R.id.contactInformationEditText);
        experienceEditText = findViewById(R.id.experienceEditText);
        profilePhotoImageView = findViewById(R.id.profilePhotoImageView);
        Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        // Get logged-in username from Intent
        loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        // Set click listener for choosing an image
        profilePhotoImageView.setOnClickListener(v -> openImageChooser());

        // Set click listener for the Update Profile button
        btnUpdateProfile.setOnClickListener(v -> updateDoctorProfile());

        // Fetch and display editable doctor profile data
        displayEditableDoctorProfileData();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //noinspection deprecation
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                // Set the selected image to the ImageView
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePhotoImageView.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayEditableDoctorProfileData() {
        // Fetch and display editable doctor profile data
        // You can use your DatabaseHelper to get data based on loggedInUsername
        // For demonstration purposes, I'll assume you have a getDoctorProfile method
        // in your DatabaseHelper class
        //noinspection resource
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DoctorProfile doctorProfile = databaseHelper.getDoctorProfile(loggedInUsername);

        if (doctorProfile != null) {
            nameEditText.setText(doctorProfile.getName());
            educationEditText.setText(doctorProfile.getEducation());
            specializationEditText.setText(doctorProfile.getSpecialization());
            contactInformationEditText.setText(doctorProfile.getContactInformation());
            experienceEditText.setText(doctorProfile.getExperience());
            // Load profile photo
            // You may use a library like Glide or Picasso here
        }
    }
    public static class BitmapUtils {
        public static byte[] bitmapToByteArray(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
    }

    private void updateDoctorProfile() {
        // Get updated data from the UI
        String updatedName = nameEditText.getText().toString();
        String updatedEducation = educationEditText.getText().toString();
        String updatedSpecialization = specializationEditText.getText().toString();
        String updatedContactInformation = contactInformationEditText.getText().toString();
        String updatedExperience = experienceEditText.getText().toString();

        // Update the doctor profile in the database
        //noinspection resource
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean updateSuccessful = databaseHelper.updateDoctorProfile(loggedInUsername, updatedName, updatedEducation,
                updatedSpecialization, updatedContactInformation, updatedExperience, selectedImage);

        if (updateSuccessful) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
            // You may finish the activity or navigate to another screen
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
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
