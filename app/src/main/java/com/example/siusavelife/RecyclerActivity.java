package com.example.siusavelife;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    User user;
    ProgressBar progressBar5;

    //a list to store all the products
    List <Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        user = new User();
        productList = new ArrayList<>();
        progressBar5 = findViewById(R.id.progressBar5);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String city = getIntent().getStringExtra("city");
        String blood = getIntent().getStringExtra("blood");

        progressBar5.setVisibility(View.VISIBLE);


            //firebase

            FirebaseRef.GetDb().getReference(city).child("Users").child(blood).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        user = ds.getValue(User.class);

                        String name = user.getName();
                        String phone = user.getPhone();
                        String email = user.getEmail();
                        String blood = user.getBlood();
                        String city = user.getCity();
                        String address = user.getAddress();
                        String status = user.getStatus();


                        productList.add(new Product(1, name,phone,email,blood,city,address,status));
                        ProductAdapter adapter = new ProductAdapter(RecyclerActivity.this, productList);

                        recyclerView.setAdapter(adapter);
                        progressBar5.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
