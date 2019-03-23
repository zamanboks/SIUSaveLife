package com.example.siusavelife;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

;import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity {

    private EditText nameSignUp, passwordSignUp, emailSignUp, donateLocationSignUp, numberSignUp;
    private Spinner citySignUp, bloodSignUp;

    Button signUpButton;
    TextView loginSignUp;

    private FirebaseAuth mAuth;
    private ProgressBar progressbar;

    User user;

    String[] bloodSpinner = {"A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};
    String[] citySpinner = {"Sylhet", "Barishal", "Chittagong", "Dhaka", "Khulna", "Mymensings", "Rajshahi", "Rangpur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        user = new User();

        nameSignUp = findViewById(R.id.nameSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        donateLocationSignUp = findViewById(R.id.donateLocationSignUp);

        citySignUp = findViewById(R.id.citySignUp);
        bloodSignUp = findViewById(R.id.bloodSignUp);
        signUpButton = findViewById(R.id.signUpButton);
        loginSignUp = findViewById(R.id.loginSignUp);
        numberSignUp = findViewById(R.id.numberSignUp);
        progressbar = findViewById(R.id.progressbar);


        ArrayAdapter b = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodSpinner);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSignUp.setAdapter(b);

        ArrayAdapter c = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, citySpinner);
        c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySignUp.setAdapter(c);

        loginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(login);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameSignUp.getText().toString().trim();
                String email = emailSignUp.getText().toString().trim();
                String password = passwordSignUp.getText().toString().trim();
                String phone = numberSignUp.getText().toString().trim();
                String address = donateLocationSignUp.getText().toString().trim();
                String blood = bloodSignUp.getSelectedItem().toString().trim();
                String city = citySignUp.getSelectedItem().toString().trim();

                UserRegister(name, email, password, phone, address, blood, city);
            }
        });
    }

    private void UserRegister(final String name, String email, final String password, final String phone, final String address, final String blood, final String city) {

        progressbar.setVisibility(View.VISIBLE);

        if (name.isEmpty()){
            progressbar.setVisibility(View.GONE);
            nameSignUp.setError("Name is required.");
            nameSignUp.requestFocus();
            return;
        }
        if (email.isEmpty()){
            progressbar.setVisibility(View.GONE);
            emailSignUp.setError("Email is required.");
            emailSignUp.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            progressbar.setVisibility(View.GONE);
            emailSignUp.setError("Please Enter a valid Email.");
            emailSignUp.requestFocus();
            return;
        }
        if (password.isEmpty()){
            progressbar.setVisibility(View.GONE);
            passwordSignUp.setError("Passwprd is required.");
            passwordSignUp.requestFocus();
            return;
        }
        if (password.length()<6){
            progressbar.setVisibility(View.GONE);
            passwordSignUp.setError("Minimum length of password should be 6");
            passwordSignUp.requestFocus();
            return;
        }
        if (phone.isEmpty()){

            progressbar.setVisibility(View.GONE);
            numberSignUp.setError("Phone Number is required.");
            numberSignUp.requestFocus();
            return;
        }
        if (address.isEmpty()){
            progressbar.setVisibility(View.GONE);
            donateLocationSignUp.setError("Address is required.");
            donateLocationSignUp.requestFocus();
            return;
        }

        Toast.makeText(this, ""+city+blood, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful()){

                    StoreUserInfo(task.getResult().getUser().getUid(), task.getResult().getUser().getEmail());

                }else {
                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                }
            }

            public void StoreUserInfo(String uid, String email) {

                WeakHashMap map = new WeakHashMap();

                map.put("name",name);
                map.put("phone",phone);
                map.put("email",email);
                map.put("blood",blood);
                map.put("city",city);
                map.put("address",address);
                map.put("password",password);
                map.put("status","Ready For Donate.");


                FirebaseRef.GetDb().getReference("totaluser").child(uid).setValue(map);

                FirebaseRef.GetDb().getReference(city).child("Users").child(blood).child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MainActivity.this, "Register Successful", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this,LogInActivity.class);
                        startActivity(intent);
                    }
                });
            }

        });
    }
    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){

            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);


        }
    }
}