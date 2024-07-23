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
import com.example.hms.DatabaseHelper;
import com.example.hms.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AccListAdapter extends ArrayAdapter<Accountant> {
    private final List<Accountant> accountants;
    private final DatabaseHelper databaseHelper; // Database helper instance

    // Constructor
    public AccListAdapter(Context context, List<Accountant> accountants, DatabaseHelper databaseHelper) {

        super(context, R.layout.acc_list_item, accountants);
        this.accountants = accountants;
        this.databaseHelper = databaseHelper;
    }

    public void deleteItem(int position) {
        Accountant deletedItem = getItem(position);

        if (deletedItem != null) {
            // Delete from the database
            if (databaseHelper != null) {
                databaseHelper.deleteAccUserByUsername(deletedItem.getUsername());
            }

            // Remove from the list
            accountants.remove(deletedItem);

            // Notify the adapter that the data set has changed
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.acc_list_item, parent, false);

            holder = new ViewHolder();
            holder.usernameTextView = convertView.findViewById(R.id.usernameTextView);
            holder.passwordTextView = convertView.findViewById(R.id.passwordTextView);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Accountant accountant = getItem(position);

        // Assuming Accountant class has getUsername() and getPassword() methods
        if (accountant != null) {
            holder.usernameTextView.setText("Username: " + accountant.getUsername());
            holder.passwordTextView.setText("Password: " + accountant.getPassword());

            // Set OnClickListener for the entire item
            convertView.setOnClickListener(view -> {
                // Open the next page with the details of the selected accountant
                openEditPage(accountant);
            });
        }
        return convertView;
    }

    private void openEditPage(Accountant accountant) {
        // Start the Edit_accinfoActivity, passing the Accountant ID or necessary data
        Intent intent = new Intent(getContext(), Edit_accinfo.class);
        intent.putExtra("accId", accountant.getId()); // Pass the Accountant ID or necessary data
        getContext().startActivity(intent);
    }

    static class ViewHolder {
        TextView usernameTextView;
        TextView passwordTextView;
        MaterialButton deleteButton;
    }

}
