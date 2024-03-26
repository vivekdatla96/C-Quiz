package com.example.c_quiz;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, etDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        // Initialize EditTexts
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);

        Button registerButton = findViewById(R.id.registerButton);
        Button selectDateButton = findViewById(R.id.selectDateButton);

        // Date of Birth Picker
        selectDateButton.setOnClickListener(view -> showDatePickerDialog(etDateOfBirth));

        // Registration Button Click
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        // Retrieve user input
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String dob = etDateOfBirth.getText().toString();

        // Perform input validation
        if (!validateInput(firstName, lastName, email, password, dob)) {
            return; // Stop the registration process if validation fails
        }

        // Calculate age based on Date of Birth
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateOfBirth = sdf.parse(dob);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dateOfBirth);

            Calendar minDobCalendar = Calendar.getInstance();
            minDobCalendar.add(Calendar.YEAR, -16); // Minimum age is 16

            if (dobCalendar.after(minDobCalendar)) {
                Toast.makeText(this, "You must be at least 16 years old to register", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing Date of Birth", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store user information (using SharedPreferences for simplicity)
        SharedPreferences sharedPreferences = getSharedPreferences("CQuizPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("dob", dob);
        editor.apply();

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

        // Navigate to LoginActivity
        Intent intent = new Intent(RegistrationActivity.this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateInput(String firstName, String lastName, String email, String password, String dob) {

        // First Name Validation
        if (firstName.isEmpty() || firstName.length() < 3 || firstName.length() > 30) {
            Toast.makeText(this, "First name must be between 3 and 30 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Last Name Validation
        if (lastName.isEmpty()) {
            Toast.makeText(this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Email Validation
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Date of Birth Validation
        if (!isValidDateOfBirth(dob)) {
            Toast.makeText(this, "Date of Birth must be in the format MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Password Validation
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidDateOfBirth(String dob) {
        Pattern dobPattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        return dobPattern.matcher(dob).matches();
    }

    private void showDatePickerDialog(EditText etDateOfBirth) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearSelected, monthOfYear, dayOfMonth) -> {
                    // Update your etDateOfBirth EditText with the selected date
                    String formattedDate = String.format( "%02d/%02d/%d", monthOfYear + 1, dayOfMonth, yearSelected);
                    etDateOfBirth.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

}





