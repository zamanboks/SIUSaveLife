package com.example.siusavelife;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {


    Request request;
    ProgressBar progressBar5Request;

    //a list to store all the products
    List<Request> productListRequest;

    //the recyclerview
    RecyclerView recyclerViewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        request = new Request();
        productListRequest = new ArrayList<>();

        progressBar5Request = findViewById(R.id.progressBar5Request);
        recyclerViewRequest = findViewById(R.id.recyclerViewRequest);
        recyclerViewRequest.setHasFixedSize(true);
        recyclerViewRequest.setLayoutManager(new LinearLayoutManager(this));

        progressBar5Request.setVisibility(View.VISIBLE);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("request");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    request = ds.getValue(Request.class);

                    String fullname = request.getFullname().toString();
                    String number = request.getNumber().toString();
                    String date = request.getDate().toString();
                    String hospital = request.getHospital().toString();
                    String whyBlood = request.getWhy().toString();
                    String city = request.getCity().toString();
                    String blood = request.getBlood().toString();
                    String bag = request.getBag().toString();

                    productListRequest.add(new Request(1, fullname,number,date,hospital,whyBlood,city,blood,bag));
                    RequestAdapter adapter = new RequestAdapter(RequestListActivity.this, productListRequest);

                    recyclerViewRequest.setAdapter(adapter);
                    progressBar5Request.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RequestListActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
