package com.example.onevote;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private Button reg;
    private EditText editTextFirstName, editTextLastName, editTextPhoneNo, editTextEmail, editTextAadhar, editTextPassword, editTextCpassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        reg = findViewById(R.id.button2);
        editTextFirstName = findViewById(R.id.editTextTextPersonName);
        editTextLastName = findViewById(R.id.editTextTextPersonName2);
        editTextPhoneNo = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextTextPersonName3);
        editTextAadhar = findViewById(R.id.editTextNumber);
        editTextPassword = findViewById(R.id.editTextTextPassword2);
        editTextCpassword = findViewById(R.id.editTextTextPassword3);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(SignUp.this , MainActivity.class);
                reg();
                //startActivity(intent);
                //Toast.makeText(SignUp.this, "Register Successful!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reg(){
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNo.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String aadhar = editTextAadhar.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextCpassword.getText().toString().trim();

        if(firstName.isEmpty()){
            editTextFirstName.setError("First Name is required!");
            editTextFirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            editTextLastName.setError("Last Name is required!");
            editTextLastName.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty()){
            editTextPhoneNo.setError("Phone Number is required!");
            editTextPhoneNo.requestFocus();
            return;
        }
        if(phoneNumber.length() != 10){
            editTextPhoneNo.setError("Please give 10 digit valid number!");
            editTextPhoneNo.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(aadhar.isEmpty()){
            editTextAadhar.setError("Aadhar Number is required!");
            editTextAadhar.requestFocus();
            return;
        }
        if(aadhar.length() != 12){
            editTextAadhar.setError("Aadhar Number should be 12 digit!");
            editTextAadhar.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 8){
            editTextPassword.setError("Minimum length should be 8");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            editTextCpassword.setError("Password didn't match");
            editTextCpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstName, lastName, phoneNumber, email, aadhar);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(SignUp.this , MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(SignUp.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUp.this, "Failed to register! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUp.this, "Failed to register! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}