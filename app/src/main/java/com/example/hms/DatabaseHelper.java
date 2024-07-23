package com.example.hms;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Pair;

import com.example.hms.admin.AddAccInfoActivity;
import com.example.hms.admin.AddAdminInfoActivity;
import com.example.hms.admin.AddDoctorInfoActivity;
import com.example.hms.admin.AddReceptionInfoActivity;
import com.example.hms.admin.Edit_accinfo;
import com.example.hms.admin.Edit_doctinfo;
import com.example.hms.admin.Edit_recepinfo;
import com.example.hms.admin.listAccAdminActivity;
import com.example.hms.admin.listAdminAdminActivity;
import com.example.hms.admin.AdminLoginActivity;
import com.example.hms.admin.Edit_admininfo;
import com.example.hms.admin.listDoctAdminActivity;
import com.example.hms.admin.listRecepAdminActivity;
import com.example.hms.billing.AccountLoginActivity;
import com.example.hms.billing.BillActivity;
import com.example.hms.billing.PrepareBill;
import com.example.hms.billing.ViewBill;
import com.example.hms.doctor.AddDiagnosis;
import com.example.hms.doctor.DoctorLoginActivity;
import com.example.hms.doctor.PatientlistDiagnosis;
import com.example.hms.doctor.CheckAppointmentActivity;
import com.example.hms.doctor.DoctorProfileActivity;
import com.example.hms.doctor.EditDoctorProfileActivity;
import com.example.hms.reception.PatientRegActivity;
import com.example.hms.reception.AppointmentSchedulingActivity;
import com.example.hms.reception.ReceptionLoginActivity;
import com.example.hms.reception.RegisteredPatientListActivity;
import com.example.hms.reception.ViewAppointmentsActivity;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HospitalManagementDB";
    private static final int DATABASE_VERSION = 2; // Increment the version number

    public static final String ADMIN_TABLE_NAME = "admin";
    public static final String ACCOUNTANT_TABLE_NAME = "acc";
    public static final String DOCTOR_TABLE_NAME = "doct";
    public static final String RECEPTIONIST_TABLE_NAME = "receptionist";
    public static final String PATIENT_TABLE_NAME = "patient";
    public static final String APPOINTMENT_TABLE_NAME = "appointment";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Additional columns for the patient table
    public static final String PATIENT_COLUMN_NAME = "name";
    public static final String PATIENT_COLUMN_AGE = "age";
    public static final String PATIENT_COLUMN_GENDER = "gender";
    public static final String PATIENT_COLUMN_CONTACT = "contact";
    public static final String PATIENT_COLUMN_EMAIL = "email";
    public static final String PATIENT_COLUMN_ADDRESS = "address";

    // Columns for the appointment table
    public static final String APPOINTMENT_COLUMN_NAME = "patient_name";
    public static final String APPOINTMENT_COLUMN_GENDER = "gender";
    public static final String APPOINTMENT_COLUMN_AGE = "age";
    public static final String APPOINTMENT_COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String APPOINTMENT_COLUMN_APPOINTMENT_TIME = "appointment_time";
    public static final String APPOINTMENT_COLUMN_DOCTOR = "doctor";
    public static final String APPOINTMENT_COLUMN_DISEASE = "disease";
    public static final String APPOINTMENT_COLUMN_ADDRESS = "address";
    private static final String ASSESSMENT_PLAN_TABLE_NAME = "AssessmentPlanTable";
    private static final String COLUMN_PATIENT_NAME = "patient_name";
    private static final String COLUMN_ASSESSMENT = "assessment";
    private static final String COLUMN_PLAN1 = "treatment_plan";
    public static final String TABLE_NAME = "patient_billing";
    public static final String COLUMN_ID_BILL = "_id";
    public static final String COLUMN_PATIENT_NAME_BILL = "patient_name";
    public static final String COLUMN_DOCTOR_VISITED = "doctor_visited";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static final String COLUMN_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_VISIT_DATE = "visit_date";
    private static final String TABLE_DOCTOR_PROFILE = "doctor_profile";

    // Column names for doctor_profile table
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAME = "name";
    private static final String KEY_EDUCATION = "education";
    private static final String KEY_SPECIALIZATION = "specialization";
    private static final String KEY_CONTACT_INFORMATION = "contact_information";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_PROFILE_PHOTO = "profile_photo";
    // Create table query for doctor_profile
    private static final String CREATE_TABLE_DOCTOR_PROFILE = "CREATE TABLE " + TABLE_DOCTOR_PROFILE + "("
            + KEY_USERNAME + " TEXT PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_EDUCATION + " TEXT,"
            + KEY_SPECIALIZATION + " TEXT,"
            + KEY_CONTACT_INFORMATION + " TEXT,"
            + KEY_EXPERIENCE + " TEXT,"
            + KEY_PROFILE_PHOTO + " BLOB "
            + ")";
    String createAssessmentPlanTable = "CREATE TABLE " + ASSESSMENT_PLAN_TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PATIENT_NAME + " TEXT, "
            + COLUMN_ASSESSMENT + " TEXT, "
            + COLUMN_PLAN1 + " TEXT);";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID_BILL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PATIENT_NAME_BILL + " TEXT, " +
                    COLUMN_DOCTOR_VISITED + " TEXT, " +
                    COLUMN_TOTAL_AMOUNT + " TEXT, " +
                    COLUMN_PAYMENT_METHOD + " TEXT, " +
                    COLUMN_VISIT_DATE + " TEXT);";
    private static final String ADMIN_TABLE_CREATE =
            "CREATE TABLE " + ADMIN_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";
    private static final String ACCOUNTANT_TABLE_CREATE =
            "CREATE TABLE " + ACCOUNTANT_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";
    private static final String DOCTOR_TABLE_CREATE =
            "CREATE TABLE " + DOCTOR_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";
    private static final String RECEPTIONIST_TABLE_CREATE =
            "CREATE TABLE " + RECEPTIONIST_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";

    // SQL statement to create the patient table
    // SQL statement to create the patient table
    private static final String PATIENT_TABLE_CREATE =
            "CREATE TABLE " + PATIENT_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PATIENT_COLUMN_NAME + " TEXT, " +
                    PATIENT_COLUMN_AGE + " TEXT, " +
                    PATIENT_COLUMN_GENDER + " TEXT, " +
                    PATIENT_COLUMN_CONTACT + " TEXT, " +
                    PATIENT_COLUMN_EMAIL + " TEXT, " +
                    PATIENT_COLUMN_ADDRESS + " TEXT " +
                    ");";

    // SQL statement to create the appointment table
    private static final String APPOINTMENT_TABLE_CREATE =
            "CREATE TABLE " + APPOINTMENT_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    APPOINTMENT_COLUMN_NAME + " TEXT, " +
                    APPOINTMENT_COLUMN_GENDER + " TEXT, " +
                    APPOINTMENT_COLUMN_AGE + " TEXT, " +
                    APPOINTMENT_COLUMN_APPOINTMENT_DATE + " TEXT, " +
                    APPOINTMENT_COLUMN_APPOINTMENT_TIME + " TEXT, " +
                    APPOINTMENT_COLUMN_DOCTOR + " TEXT, " +
                    APPOINTMENT_COLUMN_DISEASE + " TEXT, " +
                    APPOINTMENT_COLUMN_ADDRESS + " TEXT " +
                    ");";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase().execSQL(CREATE_TABLE_DOCTOR_PROFILE);
    }
    public DatabaseHelper(AdminLoginActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(PatientRegActivity patientRegActivity) {
        super(patientRegActivity, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AppointmentSchedulingActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(listAdminAdminActivity addAdminAdminActivity) {
        super(addAdminAdminActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(ReceptionLoginActivity receptionLoginActivity) {
        super(receptionLoginActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Edit_admininfo editAdmininfo) {
        super(editAdmininfo,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AddAdminInfoActivity addAdminInfoActivity) {
        super(addAdminInfoActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AddAccInfoActivity addAccInfoActivity) {
        super(addAccInfoActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AddDoctorInfoActivity addDoctorInfoActivity) {
        super(addDoctorInfoActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Edit_accinfo editAccinfo) {
        super(editAccinfo,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Edit_recepinfo editRecepinfo) {
        super(editRecepinfo,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Edit_doctinfo editDoctinfo) {
        super(editDoctinfo,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(listAccAdminActivity listAccAdminActivity) {
        super(listAccAdminActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AddReceptionInfoActivity addReceptionInfoActivity) {
        super(addReceptionInfoActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(listDoctAdminActivity listDoctAdminActivity) {
        super(listDoctAdminActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(listRecepAdminActivity listRecepAdminActivity) {
        super(listRecepAdminActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(ViewAppointmentsActivity viewAppointmentsActivity) {
        super(viewAppointmentsActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(DoctorProfileActivity doctorProfileActivity) {
        super(doctorProfileActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(EditDoctorProfileActivity editDoctorProfileActivity) {
        super(editDoctorProfileActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(RegisteredPatientListActivity registeredPatientListActivity) {
        super(registeredPatientListActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(CheckAppointmentActivity checkAppointmentActivity) {
        super(checkAppointmentActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(PatientlistDiagnosis addDiagnosis) {
        super(addDiagnosis,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AddDiagnosis addDiagnosis) {
        super(addDiagnosis,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(AccountLoginActivity accountLoginActivity) {
        super(accountLoginActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(PrepareBill prepareBill) {
        super(prepareBill,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(ViewBill viewBill) {
        super(viewBill,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(BillActivity billActivity) {
        super(billActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(DoctorLoginActivity doctorLoginActivity) {
        super(doctorLoginActivity,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ADMIN_TABLE_CREATE);
        db.execSQL(DOCTOR_TABLE_CREATE);
        db.execSQL(ACCOUNTANT_TABLE_CREATE);
        db.execSQL(RECEPTIONIST_TABLE_CREATE);

        db.execSQL(createAssessmentPlanTable);
        // Create the patient table
        db.execSQL(PATIENT_TABLE_CREATE);
        db.execSQL(CREATE_TABLE_QUERY);
        // Create the appointment table
        db.execSQL(APPOINTMENT_TABLE_CREATE);
        db.execSQL(CREATE_TABLE_DOCTOR_PROFILE);
        insertDefaultAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertData(String patientName, String doctorVisited, String totalAmount, String paymentMethod, String visitDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_NAME, patientName);
        values.put(COLUMN_DOCTOR_VISITED, doctorVisited);
        values.put(COLUMN_TOTAL_AMOUNT, totalAmount);
        values.put(COLUMN_PAYMENT_METHOD, paymentMethod);
        values.put(COLUMN_VISIT_DATE, visitDate);

        // Insert the data into the table
        return db.insert(TABLE_NAME, null, values);
    }


    // Method to insert patient details into the database
    public long insertPatient(String name, String age, String gender, String contact, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATIENT_COLUMN_NAME, name);
        values.put(PATIENT_COLUMN_AGE, age);
        values.put(PATIENT_COLUMN_GENDER, gender);
        values.put(PATIENT_COLUMN_CONTACT, contact);
        values.put(PATIENT_COLUMN_EMAIL, email);
        values.put(PATIENT_COLUMN_ADDRESS, address);

        long newRowId = db.insert(PATIENT_TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    // Method to insert appointment details into the database
    public long insertAppointment(String fullName, String gender, String age,
                                  String appointmentDate, String appointmentTime,
                                  String doctor, String disease, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(APPOINTMENT_COLUMN_NAME, fullName);
        values.put(APPOINTMENT_COLUMN_GENDER, gender);
        values.put(APPOINTMENT_COLUMN_AGE, age);
        values.put(APPOINTMENT_COLUMN_APPOINTMENT_DATE, appointmentDate);
        values.put(APPOINTMENT_COLUMN_APPOINTMENT_TIME, appointmentTime);
        values.put(APPOINTMENT_COLUMN_DOCTOR, doctor);
        values.put(APPOINTMENT_COLUMN_DISEASE, disease);
        values.put(APPOINTMENT_COLUMN_ADDRESS, address);

        // Insert row
        long newRowId = db.insert(APPOINTMENT_TABLE_NAME, null, values);

        // Close the database connection
        db.close();

        return newRowId;
    }

    private void insertDefaultAdmin(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "password");

        db.insert(ADMIN_TABLE_NAME, null, values);
    }

    public boolean loginAdmin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection to specify which columns to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_PASSWORD
        };

        // Define a selection to specify the WHERE clause
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Perform the query on the admin table
        Cursor cursor = db.query(
                ADMIN_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean success = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return success;
    }

    public boolean loginAcc(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection to specify which columns to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_PASSWORD
        };

        // Define a selection to specify the WHERE clause
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Perform the query on the admin table
        Cursor cursor = db.query(
                ACCOUNTANT_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean success = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return success;
    }
    public boolean loginReceptionist(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection to specify which columns to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_PASSWORD
        };

        // Define a selection to specify the WHERE clause
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Perform the query on the receptionist table
        Cursor cursor = db.query(
                RECEPTIONIST_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean success = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return success;
    }

        public Admin getAdminById(long adminId) {
            SQLiteDatabase db = this.getReadableDatabase();

            Admin admin = null;

            // Query the database to retrieve the admin record based on adminId
            Cursor cursor = db.query(
                    ADMIN_TABLE_NAME,
                    new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD},
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(adminId)},
                    null,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                // Extract data from the cursor and create an Admin object
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                admin = new Admin(id, username, password);

                cursor.close();
            }

            db.close();

            return admin;
        }

        public void updateAdmin ( int adminId, String newUsername, String newPassword){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, newUsername);
            values.put(COLUMN_PASSWORD, newPassword);

            // Update the admin record based on the adminId
            db.update(ADMIN_TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(adminId)});

            db.close();
        }

        public long insertAdmin (String newUsername, String newPassword){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, newUsername);
            values.put(COLUMN_PASSWORD, newPassword);

            // Insert the new admin record
            long result = db.insert(ADMIN_TABLE_NAME, null, values);

            db.close();

            return result;
        }

    public Accountant getAccById(long accId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Accountant accountant = null;

        // Query the database to retrieve the admin record based on adminId
        Cursor cursor = db.query(
                ACCOUNTANT_TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(accId)},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create an Admin object
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            accountant = new Accountant(id, username, password);

            cursor.close();
        }

        db.close();

        return accountant;
    }

    public void updateAcc ( int accId, String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Update the admin record based on the adminId
        db.update(ACCOUNTANT_TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(accId)});

        db.close();
    }

    public long insertAcc (String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Insert the new admin record
        long result = db.insert(ACCOUNTANT_TABLE_NAME, null, values);

        db.close();

        return result;
    }
    public Doctor getDoctById(long doctId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Doctor doctor = null;

        // Query the database to retrieve the admin record based on adminId
        Cursor cursor = db.query(
                DOCTOR_TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(doctId)},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create an Admin object
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            doctor = new Doctor(id, username, password);

            cursor.close();
        }

        db.close();

        return doctor;
    }

    public void updateDoct ( int doctId, String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Update the admin record based on the adminId
        db.update(DOCTOR_TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(doctId)});

        db.close();
    }

    public long insertDoct (String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Insert the new admin record
        long result = db.insert(DOCTOR_TABLE_NAME, null, values);

        db.close();

        return result;
    }
    public Receptionist getRecepById(long recepID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Receptionist receptionist = null;

        // Query the database to retrieve the admin record based on adminId
        Cursor cursor = db.query(
                RECEPTIONIST_TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(recepID)},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create an Admin object
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            receptionist = new Receptionist(id, username, password);

            cursor.close();
        }

        db.close();

        return receptionist;
    }

    public void updateRecep ( int recepID, String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Update the admin record based on the adminId
        db.update(RECEPTIONIST_TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(recepID)});

        db.close();
    }

    public long insertRecep (String newUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_PASSWORD, newPassword);

        // Insert the new admin record
        long result = db.insert(RECEPTIONIST_TABLE_NAME, null, values);

        db.close();

        return result;
    }

    public Cursor getDoctorNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Specify the columns you want to retrieve, in this case, just the "name" column
        String[] projection = { KEY_NAME };

        // Specify the table name

        // Query the database

        // Make sure to close the database when done with the cursor
        // db.close(); // Note: Uncomment this line if you are done using the database

        return db.query(
                TABLE_DOCTOR_PROFILE,          // The table to query
                projection,         // The array of columns to return (pass null to get all)
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );
    }


    public Cursor getAppointmentDetails() {
        // Get a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to be retrieved
        String[] projection = {
                APPOINTMENT_COLUMN_NAME ,
                        APPOINTMENT_COLUMN_GENDER ,
                        APPOINTMENT_COLUMN_AGE ,
                        APPOINTMENT_COLUMN_APPOINTMENT_DATE ,
                        APPOINTMENT_COLUMN_APPOINTMENT_TIME ,
                        APPOINTMENT_COLUMN_DOCTOR ,
                        APPOINTMENT_COLUMN_DISEASE ,
                        APPOINTMENT_COLUMN_ADDRESS
        };

        // Query the appointment schedule table

        // Note: Make sure to close the cursor when you're done with it
        // You can close it in the calling code when you're done processing the data

        return db.query(
                APPOINTMENT_TABLE_NAME,     // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null                   // The sort order
        );
    }
    @SuppressLint("Range")
    public String login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Specify the columns you want to retrieve, in this case, just the "username" column
        String[] projection = { COLUMN_USERNAME };

        // Specify the table name

        // Query the database
        Cursor cursor = db.query(
                DOCTOR_TABLE_NAME,          // The table to query
                projection,         // The array of columns to return (pass null to get all)
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",  // The columns for the WHERE clause
                new String[]{username, password},  // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                null                // The sort order
        );

        String loggedInUsername = null;

        if (cursor != null && cursor.moveToFirst()) {
            loggedInUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            cursor.close();
        }

        db.close();

        return loggedInUsername;
    }


   

    // Add a method to get doctor profile by username if needed
    @SuppressLint("Range")
    public DoctorProfile getDoctorProfileByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTOR_PROFILE, null, KEY_USERNAME + "=?", new String[]{username}, null, null, null);

        DoctorProfile doctorProfile = null;

        if (cursor != null && cursor.moveToFirst()) {
            doctorProfile = new DoctorProfile();
            doctorProfile.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            doctorProfile.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            doctorProfile.setEducation(cursor.getString(cursor.getColumnIndex(KEY_EDUCATION)));
            doctorProfile.setSpecialization(cursor.getString(cursor.getColumnIndex(KEY_SPECIALIZATION)));
            doctorProfile.setContactInformation(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_INFORMATION)));
            doctorProfile.setExperience(cursor.getString(cursor.getColumnIndex(KEY_EXPERIENCE)));

            // Retrieve profile photo as a byte array (BLOB)
            byte[] profilePhotoData = cursor.getBlob(cursor.getColumnIndex(KEY_PROFILE_PHOTO));
            doctorProfile.setProfilePhoto(profilePhotoData);

            cursor.close();
        }

        return doctorProfile;
    }

    public ArrayList<String> getAllPatients() {
        ArrayList<String> patientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PATIENT_TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(PATIENT_COLUMN_NAME));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(PATIENT_COLUMN_AGE));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(PATIENT_COLUMN_GENDER));
                // Add more columns as needed

                // Create a string representation of the patient
                String patientInfo = "Name: " + name + "\nAge: " + age + "\nGender: " + gender;
                // Add the string to the list
                patientsList.add(patientInfo);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return patientsList;
    }

    public ArrayList<String> getDoctorAppointments(String doctorName) {
        ArrayList<String> appointmentsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                APPOINTMENT_COLUMN_NAME,
                APPOINTMENT_COLUMN_APPOINTMENT_DATE,
                APPOINTMENT_COLUMN_APPOINTMENT_TIME
                // Add more columns as needed
        };

        // Define the selection criteria
        String selection = APPOINTMENT_COLUMN_DOCTOR + "=?";
        String[] selectionArgs = {doctorName};

        // Query the database
        Cursor cursor = db.query(
                APPOINTMENT_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if the cursor contains data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract appointment data from the cursor
                @SuppressLint("Range") String patientName = cursor.getString(cursor.getColumnIndex(APPOINTMENT_COLUMN_NAME));
                @SuppressLint("Range") String appointmentDate = cursor.getString(cursor.getColumnIndex(APPOINTMENT_COLUMN_APPOINTMENT_DATE));
                @SuppressLint("Range") String appointmentTime = cursor.getString(cursor.getColumnIndex(APPOINTMENT_COLUMN_APPOINTMENT_TIME));

                // Create a string representation of the appointment
                String appointmentInfo = "Patient: " + patientName + "\nDate: " + appointmentDate + "\nTime: " + appointmentTime;
                // Add the string to the list
                appointmentsList.add(appointmentInfo);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database
        db.close();

        return appointmentsList;
    }


    public boolean saveAssessmentAndPlan(String patientName, String assessment, String plan) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the patient's assessment and plan already exist
        if (getAssessmentAndPlan(patientName) != null) {
            // Update the existing assessment and plan
            ContentValues values = new ContentValues();
            values.put(COLUMN_ASSESSMENT, assessment);
            values.put(COLUMN_PLAN1, plan);

            int rowsAffected = db.update(ASSESSMENT_PLAN_TABLE_NAME, values, COLUMN_PATIENT_NAME + "=?", new String[]{patientName});
            db.close();

            return rowsAffected > 0;
        } else {
            // Insert a new record for assessment and plan
            ContentValues values = new ContentValues();
            values.put(COLUMN_PATIENT_NAME, patientName);
            values.put(COLUMN_ASSESSMENT, assessment);
            values.put(COLUMN_PLAN1, plan);

            long newRowId = db.insert(ASSESSMENT_PLAN_TABLE_NAME, null, values);
            db.close();

            return newRowId != -1;
        }
    }

    public Pair<String, String> getAssessmentAndPlan(String patientName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ASSESSMENT, COLUMN_PLAN1};  // Use COLUMN_PLAN1 instead of "treatment_plan"
        String selection = COLUMN_PATIENT_NAME + "=?";
        String[] selectionArgs = {patientName};

        @SuppressLint("Recycle") Cursor cursor = db.query(
                ASSESSMENT_PLAN_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Pair<String, String> assessmentAndPlan = null;
            if (cursor != null && cursor.moveToFirst()) {
                // Extract data and create a Pair object
                @SuppressLint("Range") String assessment = cursor.getString(cursor.getColumnIndex(COLUMN_ASSESSMENT));
                @SuppressLint("Range") String plan = cursor.getString(cursor.getColumnIndex(COLUMN_PLAN1));
                assessmentAndPlan = new Pair<>(assessment, plan);
            }


        return assessmentAndPlan;
    }

    @SuppressLint("Range")
    public String getAssessment(String patientName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ASSESSMENT_PLAN_TABLE_NAME, new String[]{COLUMN_ASSESSMENT},
                COLUMN_PATIENT_NAME + "=?", new String[]{patientName}, null, null, null);

        String assessment = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Extract assessment from the cursor
            assessment = cursor.getString(cursor.getColumnIndex(COLUMN_ASSESSMENT));
            cursor.close();
        }

        db.close();

        return assessment;
    }

    @SuppressLint("Range")
    public String getPlan(String patientName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ASSESSMENT_PLAN_TABLE_NAME, new String[]{COLUMN_PLAN1},
                COLUMN_PATIENT_NAME + "=?", new String[]{patientName}, null, null, null);

        String plan = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Extract plan from the cursor
            plan = cursor.getString(cursor.getColumnIndex(COLUMN_PLAN1));
            cursor.close();
        }

        db.close();

        return plan;
    }

    public Cursor getPatientNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve (in this case, only patient names)
        String[] projection = {COLUMN_PATIENT_NAME_BILL};

        // Query the database to get patient names
        // The query method returns a Cursor that can be used to iterate over the result set
        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }

    public Cursor getBillRecords(String selectedPatientName, String selectedDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID_BILL,
                COLUMN_PATIENT_NAME_BILL,
                COLUMN_DOCTOR_VISITED,
                COLUMN_TOTAL_AMOUNT,
                COLUMN_PAYMENT_METHOD,
                COLUMN_VISIT_DATE
                // Add more columns if needed
        };

        // Define the selection criteria
        String selection = COLUMN_PATIENT_NAME_BILL + " = ? AND " + COLUMN_VISIT_DATE + " = ?";
        String[] selectionArgs = {selectedPatientName, selectedDate};

        // Query the database to get bill records
        // The query method returns a Cursor that can be used to iterate over the result set
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    public void deleteAccUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause
        String selection = COLUMN_USERNAME + " = ?";

        // Specify the arguments
        String[] selectionArgs = { username };

        // Delete the user from the table
        db.delete(ACCOUNTANT_TABLE_NAME, selection, selectionArgs);

        // Close the database
        db.close();
    }
    public void deleteAdminUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause
        String selection = COLUMN_USERNAME + " = ?";

        // Specify the arguments
        String[] selectionArgs = { username };

        // Delete the user from the table
        db.delete(ADMIN_TABLE_NAME, selection, selectionArgs);

        // Close the database
        db.close();
    }
    public void deleteDoctorUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause
        String selection = COLUMN_USERNAME + " = ?";

        // Specify the arguments
        String[] selectionArgs = { username };

        // Delete the user from the table
        db.delete(DOCTOR_TABLE_NAME, selection, selectionArgs);

        // Close the database
        db.close();
    }
    public void deleteRecepUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause
        String selection = COLUMN_USERNAME + " = ?";

        // Specify the arguments
        String[] selectionArgs = { username };

        // Delete the user from the table
        db.delete(RECEPTIONIST_TABLE_NAME, selection, selectionArgs);

        // Close the database
        db.close();
    }

    public void deleteAppointment(Appointment clickedAppointment) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the where clause to identify the appointment to delete
        String whereClause = APPOINTMENT_COLUMN_NAME + "=? AND " +
                APPOINTMENT_COLUMN_APPOINTMENT_DATE + "=? AND " +
                APPOINTMENT_COLUMN_APPOINTMENT_TIME + "=?";

        // Define the values for the where clause
        String[] whereArgs = {
                clickedAppointment.getPatientName(),
                clickedAppointment.getAppointmentDate(),
                clickedAppointment.getAppointmentTime()
        };

        // Perform the deletion
        db.delete(APPOINTMENT_TABLE_NAME, whereClause, whereArgs);

        // Close the database
        db.close();
    }

    public boolean insertDoctorProfile(String username, String name, String education, String specialization, String contactInformation, String experience, byte[] profilePhoto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_NAME, name);
        values.put(KEY_EDUCATION, education);
        values.put(KEY_SPECIALIZATION, specialization);
        values.put(KEY_CONTACT_INFORMATION, contactInformation);
        values.put(KEY_EXPERIENCE, experience);
        values.put(KEY_PROFILE_PHOTO, profilePhoto);

        long rowId = db.insert(TABLE_DOCTOR_PROFILE, null, values);
        db.close();

        return rowId != -1; // Return true if the row was inserted successfully
    }

    public DoctorProfile getDoctorProfile(String loggedInUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        DoctorProfile doctorProfile = null;

        // Define the columns you want to retrieve
        String[] projection = {
                KEY_USERNAME,
                KEY_NAME,
                KEY_EDUCATION,
                KEY_SPECIALIZATION,
                KEY_CONTACT_INFORMATION,
                KEY_EXPERIENCE,
                KEY_PROFILE_PHOTO
        };

        // Define the selection criteria
        String selection = KEY_USERNAME + "=?";
        String[] selectionArgs = {loggedInUsername};

        // Query the database
        Cursor cursor = db.query(
                TABLE_DOCTOR_PROFILE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if the cursor contains data
        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            @SuppressLint("Range") String education = cursor.getString(cursor.getColumnIndex(KEY_EDUCATION));
            @SuppressLint("Range") String specialization = cursor.getString(cursor.getColumnIndex(KEY_SPECIALIZATION));
            @SuppressLint("Range") String contactInformation = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_INFORMATION));
            @SuppressLint("Range") String experience = cursor.getString(cursor.getColumnIndex(KEY_EXPERIENCE));
            @SuppressLint("Range") byte[] profilePhoto = cursor.getBlob(cursor.getColumnIndex(KEY_PROFILE_PHOTO));

            // Create a DoctorProfile object with the retrieved data
            doctorProfile = new DoctorProfile(username, name, education, specialization, contactInformation, experience, profilePhoto);

            // Close the cursor
            cursor.close();
        }

        // Close the database
        db.close();

        return doctorProfile;
    }


    public boolean updateDoctorProfile(String loggedInUsername, String updatedName, String updatedEducation, String updatedSpecialization, String updatedContactInformation, String updatedExperience, Bitmap selectedImage) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Convert the selected image (Bitmap) to a byte array
        byte[] profilePhotoData = null;
        if (selectedImage != null) {
            profilePhotoData = EditDoctorProfileActivity.BitmapUtils.bitmapToByteArray(selectedImage);
        }

        // Check if the doctor's profile already exists
        if (getDoctorProfileByUsername(loggedInUsername) != null) {
            // Update the existing profile
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, updatedName);
            values.put(KEY_EDUCATION, updatedEducation);
            values.put(KEY_SPECIALIZATION, updatedSpecialization);
            values.put(KEY_CONTACT_INFORMATION, updatedContactInformation);
            values.put(KEY_EXPERIENCE, updatedExperience);
            values.put(KEY_PROFILE_PHOTO, profilePhotoData);

            int rowsAffected = db.update(TABLE_DOCTOR_PROFILE, values, KEY_USERNAME + "=?", new String[]{loggedInUsername});
            db.close();

            return rowsAffected > 0;
        }

        return false;
    }

    public void deleteDoctorProfileByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to identify the row with the specified username
        String whereClause = DatabaseHelper.KEY_USERNAME + " = ?";
        String[] whereArgs = {username};

        // Perform the deletion
        db.delete(DatabaseHelper.TABLE_DOCTOR_PROFILE, whereClause, whereArgs);

        // Close the database
        db.close();
    }


    // Add any other methods for database operations as needed

}
