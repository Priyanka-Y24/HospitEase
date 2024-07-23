package com.example.hms;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class DoctorProfile {
    private String username;
    private String name;
    private String education;
    private String specialization;
    private String contactInformation;
    private String experience;
    private byte[] profilePhoto;

    // Constructor
    public DoctorProfile() {
        // Default constructor
    }

    public DoctorProfile(String username, String name, String education, String specialization,
                         String contactInformation, String experience, byte[] profilePhoto) {
        this.username = username;
        this.name = name;
        this.education = education;
        this.specialization = specialization;
        this.contactInformation = contactInformation;
        this.experience = experience;
        this.profilePhoto = profilePhoto;
    }
    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEducation() {
        return education;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getExperience() {
        return experience;
    }



    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @NonNull
    @Override
    public String toString() {
        return "DoctorProfile{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", education='" + education + '\'' +
                ", specialization='" + specialization + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                ", experience='" + experience + '\'' +
                ", profilePhoto=" + Arrays.toString(profilePhoto) +
                '}';
    }
}
