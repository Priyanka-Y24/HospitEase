package com.example.hms;
public class Appointment {
    private final String patientName;
    private final String gender;
    private final String age;
    private final String appointmentDate;
    private final String appointmentTime;
    private final String doctorName;
    private final String disease;
    private final String address;

    public Appointment(String patientName, String gender, String age, String appointmentDate, String appointmentTime, String doctorName, String disease, String address) {
        this.patientName = patientName;
        this.gender = gender;
        this.age = age;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
        this.disease = disease;
        this.address = address;
    }

    // Add getters for each field

    public String getPatientName() {
        return patientName;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDisease() {
        return disease;
    }

    public String getAddress() {
        return address;
    }
}
