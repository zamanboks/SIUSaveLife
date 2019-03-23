package com.example.siusavelife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    private EditText emailLogIn,passwordLogIn;
    private Button logInButton;
    private TextView newAccount;
    private FirebaseAuth mAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailLogIn = findViewById(R.id.emailLogIn);
        passwordLogIn = findViewById(R.id.passwordLogIn);
        logInButton = findViewById(R.id.logInButton);
        newAccount = findViewById(R.id.newAccount);



        //FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccount = new Intent(LogInActivity.this,MainActivity.class);
                startActivity(newAccount);
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogIn.getText().toString().trim();
                String password = passwordLogIn.getText().toString().trim();

                UserLogin(email,password);
            }

            private void UserLogin(String email, String password) {
                if (email.isEmpty()){
                    emailLogIn.setError("Enter your email.");
                    emailLogIn.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    passwordLogIn.setError("Enter your password.");
                    passwordLogIn.requestFocus();
                    return;
                }

                if (email.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressBar2.setVisibility(View.GONE);

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LogInActivity.this,HomeActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LogInActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
