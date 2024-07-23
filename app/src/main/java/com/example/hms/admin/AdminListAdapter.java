package com.example.hms.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hms.Accountant;
import com.example.hms.Admin;
import com.example.hms.DatabaseHelper;
import com.example.hms.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AdminListAdapter extends ArrayAdapter<Admin> {

    private final List<Admin> admins;
    private final DatabaseHelper databaseHelper; // Database helper instance

    // Constructor
    public AdminListAdapter(Context context, List<Admin> admins, DatabaseHelper databaseHelper) {

        super(context, R.layout.admin_list_item, admins);
        this.admins = admins;
        this.databaseHelper = databaseHelper;
    }
    public void deleteItem(int position) {
        Admin deletedItem = getItem(position);

        if (deletedItem != null) {
            // Delete from the database
            if (databaseHelper != null) {
                databaseHelper.deleteAdminUserByUsername(deletedItem.getUsername());
            }

            // Remove from the list
            admins.remove(deletedItem);

            // Notify the adapter that the data set has changed
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.admin_list_item, parent, false);

            holder = new ViewHolder();
            holder.usernameTextView = convertView.findViewById(R.id.usernameTextView);
            holder.passwordTextView = convertView.findViewById(R.id.passwordTextView);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Admin admin = getItem(position);

        // Assuming Accountant class has getUsername() and getPassword() methods
        if (admin != null) {
            holder.usernameTextView.setText("Username: " + admin.getUsername());
            holder.passwordTextView.setText("Password: " + admin.getPassword());

            // Set OnClickListener for the entire item
            convertView.setOnClickListener(view -> {
                // Open the next page with the details of the selected accountant
                openEditPage(admin);
            });
        }
        return convertView;
    }

    private void openEditPage(Admin admin) {
        // Start the Edit_accinfoActivity, passing the Accountant ID or necessary data
        Intent intent = new Intent(getContext(), Edit_admininfo.class);
        intent.putExtra("adminId", admin.getId()); // Pass the Accountant ID or necessary data
        getContext().startActivity(intent);
    }

    static class ViewHolder {
        TextView usernameTextView;
        TextView passwordTextView;
        MaterialButton deleteButton;
    }

}