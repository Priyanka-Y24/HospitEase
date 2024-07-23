package com.example.hms.reception;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hms.Appointment;
import com.example.hms.R;

import java.util.List;

public class AppointmentsAdapter extends ArrayAdapter<Appointment> {

    private final int resourceId;

    public AppointmentsAdapter(Context context, int resource, List<Appointment> appointments) {
        super(context, resource, appointments);
        this.resourceId = resource;
    }

    @NonNull
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Appointment appointment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }

        TextView patientNameTextView = convertView.findViewById(R.id.patientNameTextView);
        TextView appointmentDateTextView = convertView.findViewById(R.id.appointmentDateTextView);
        TextView genderTextView = convertView.findViewById(R.id.genderTextView);
        TextView ageTextView = convertView.findViewById(R.id.ageTextView);
        TextView appointmentTimeTextView = convertView.findViewById(R.id.appointmentTimeTextView);
        TextView doctorNameTextView = convertView.findViewById(R.id.doctorNameTextView);
        TextView diseaseTextView = convertView.findViewById(R.id.diseaseTextView);
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);

        // Bind data to TextViews
        assert appointment != null;
        patientNameTextView.setText("Patient Name: " + appointment.getPatientName());
        appointmentDateTextView.setText("Appointment Date: " + appointment.getAppointmentDate());
        genderTextView.setText("Gender: " + appointment.getGender());
        ageTextView.setText("Age: " + appointment.getAge());
        appointmentTimeTextView.setText("Appointment Time: " + appointment.getAppointmentTime());
        doctorNameTextView.setText("Doctor: " + appointment.getDoctorName());
        diseaseTextView.setText("Disease: " + appointment.getDisease());
        addressTextView.setText("Address: " + appointment.getAddress());

        return convertView;
    }
}
