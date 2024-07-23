package com.example.hms.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hms.DatabaseHelper;
import com.example.hms.Doctor;
import com.example.hms.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DoctorListAdapter extends ArrayAdapter<Doctor> {

    private final List<Doctor> doctors;
    private final DatabaseHelper databaseHelper; // Database helper instance

    // Constructor
    public DoctorListAdapter(Context context, List<Doctor> doctors, DatabaseHelper databaseHelper) {

        super(context, R.layout.doct_list_item, doctors);
        this.doctors = doctors;
        this.databaseHelper = databaseHelper;
    }
    public void deleteItem(int position) {
        Doctor deletedItem = getItem(position);

        if (deletedItem != null) {
            // Delete from the database
            if (databaseHelper != null) {
                databaseHelper.deleteDoctorUserByUsername(deletedItem.getUsername());
                databaseHelper.deleteDoctorProfileByUsername(deletedItem.getUsername());
            }

            // Remove from the list
            doctors.remove(deletedItem);

            // Notify the adapter that the data set has changed
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doct_list_item, parent, false);

            holder = new ViewHolder();
            holder.usernameTextView = convertView.findViewById(R.id.usernameTextView);
            holder.passwordTextView = convertView.findViewById(R.id.passwordTextView);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Doctor doctor = getItem(position);

        // Assuming Accountant class has getUsername() and getPassword() methods
        if (doctor != null) {
            holder.usernameTextView.setText("Username: " + doctor.getUsername());
            holder.passwordTextView.setText("Password: " + doctor.getPassword());

            // Set OnClickListener for the entire item
            convertView.setOnClickListener(view -> {
                // Open the next page with the details of the selected accountant
                openEditPage(doctor);
            });
        }
        return convertView;
    }

    private void openEditPage(Doctor doctor) {
        // Start the Edit_accinfoActivity, passing the Accountant ID or necessary data
        Intent intent = new Intent(getContext(), Edit_doctinfo.class);
        intent.putExtra("doctorId", doctor.getId()); // Pass the Accountant ID or necessary data
        getContext().startActivity(intent);
    }

    static class ViewHolder {
        TextView usernameTextView;
        TextView passwordTextView;
        MaterialButton deleteButton;
    }

}