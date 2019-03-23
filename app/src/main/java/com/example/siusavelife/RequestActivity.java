package com.example.siusavelife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.WeakHashMap;

public class RequestActivity extends AppCompatActivity{

    Button submitRequest;
    EditText fullnameRequest,numberRequest,dateRequest,hospitalRequest,whyBloodRequest;
    Spinner spinnerDistrict,spinnerBloodGroup,spinnerAmountOfBlood;
    ProgressBar progressBarRequest;

    String[] citySpinnerHome = {"Select your City..","Sylhet", "Barishal", "Chittagong", "Dhaka", "Khulna", "Mymensings", "Rajshahi", "Rangpur"};


    String[] bloodSpinner = {"A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};


    String[] bagSpinner = {"1 bag","2 bag","3 bag","4 bag"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        submitRequest = findViewById(R.id.submitRequest);
        progressBarRequest = findViewById(R.id.progressBarRequest);

        fullnameRequest = findViewById(R.id.fullnameRequest);
        numberRequest = findViewById(R.id.numberRequest);
        dateRequest = findViewById(R.id.dateRequest);
        hospitalRequest = findViewById(R.id.hospitalRequest);
        whyBloodRequest = findViewById(R.id.whyBloodRequest);

        //district
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        ArrayAdapter b = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, citySpinnerHome);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(b);

        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        ArrayAdapter c = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodSpinner);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(c);


        spinnerAmountOfBlood = findViewById(R.id.spinnerAmountOfBlood);
        ArrayAdapter d = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bagSpinner);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmountOfBlood.setAdapter(d);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("request");


        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarRequest.setVisibility(View.VISIBLE);

                String fullname = fullnameRequest.getText().toString().trim();
                String number = numberRequest.getText().toString().trim();
                String date = dateRequest.getText().toString().trim();
                String hospital = hospitalRequest.getText().toString().trim();
                String whyBlood = whyBloodRequest.getText().toString().trim();
                String city = spinnerDistrict.getSelectedItem().toString().trim();
                String blood = spinnerBloodGroup.getSelectedItem().toString().trim();
                String bag = spinnerAmountOfBlood.getSelectedItem().toString().trim();

                ProcessData(fullname,number,date,hospital,whyBlood,city,blood,bag);
            }

            private void ProcessData(String fullname, String number, String date, String hospital, String whyBlood, String city, String blood, String bag) {


                if (fullname.isEmpty()){
                    fullnameRequest.requestFocus();
                    progressBarRequest.setVisibility(View.GONE);
                    return;
                }

                if (number.isEmpty()){
                    numberRequest.requestFocus();
                    progressBarRequest.setVisibility(View.GONE);
                    return;
                }
                if (hospital.isEmpty()){
                    hospitalRequest.requestFocus();
                    progressBarRequest.setVisibility(View.GONE);
                    return;
                }
                if (whyBlood.isEmpty()){
                    whyBloodRequest.requestFocus();
                    progressBarRequest.setVisibility(View.GONE);
                    return;
                }


                WeakHashMap map = new WeakHashMap();

                map.put("fullname",fullname);
                map.put("number",number);
                map.put("date",date);
                map.put("hospital",hospital);
                map.put("why",whyBlood);
                map.put("city",city);
                map.put("blood",blood);
                map.put("bag",bag);

                myRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBarRequest.setVisibility(View.GONE);
                        Toast.makeText(RequestActivity.this, "Request Completed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
