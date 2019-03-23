package com.example.siusavelife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    Button aPlus,aMinus,oPlus,oMinus,bPlus,bMinus,abPlus,abMinus,totalUserDisplay;
    LinearLayout feedHome,requestBloodHome,requestBloodListHome,organizationHome,myAccountHome,ambulanceHome;
    Spinner spinnerHomeCity;
    String[] citySpinnerHome = {"Select your City..","Sylhet", "Barishal", "Chittagong", "Dhaka", "Khulna", "Mymensings", "Rajshahi", "Rangpur"};
    String city;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Blood Group list

        aPlus = findViewById(R.id.aPlus);
        aMinus = findViewById(R.id.aMinus);
        oPlus = findViewById(R.id.oPlus);
        oMinus = findViewById(R.id.oMinus);
        bPlus = findViewById(R.id.bPlus);
        bMinus = findViewById(R.id.bMinus);
        abPlus = findViewById(R.id.abPlus);
        abMinus = findViewById(R.id.abMinus);

        spinnerHomeCity = findViewById(R.id.spinnerHomeCity);
        spinnerHomeCity.setOnItemSelectedListener(this);
        totalUserDisplay = findViewById(R.id.totalUserDisplay);

        //Activity

        feedHome = findViewById(R.id.feedHome);
        requestBloodHome = findViewById(R.id.requestBloodHome);
        requestBloodListHome = findViewById(R.id.requestBloodListHome);
        organizationHome = findViewById(R.id.organizationHome);
        myAccountHome = findViewById(R.id.myAccountHome);
        ambulanceHome = findViewById(R.id.ambulanceHome);


        // permission

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }

        ArrayAdapter b = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, citySpinnerHome);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHomeCity.setAdapter(b);

        // final String cityHome = spinnerHomeCity.getSelectedItem().toString().trim();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//default

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //my Work

        myAccountHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(profile);
            }
        });
        requestBloodHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RequestActivity.class);
                startActivity(intent);
            }
        });

        requestBloodListHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RequestListActivity.class);
                startActivity(intent);
            }
        });

        aPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","A+");
                    startActivity(intent);
                }

            }
        });
        aMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","A-");
                    startActivity(intent);
                }

            }
        });
        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","B+");
                    startActivity(intent);
                }

            }
        });
        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","B-");
                    startActivity(intent);
                }

            }
        });
        //o
        oPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","O+");
                    startActivity(intent);
                }

            }
        });
        oMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","O-");
                    startActivity(intent);
                }

            }
        });

        abPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","AB+");
                    startActivity(intent);
                }

            }
        });
        abMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.equals("Select your City..")){
                    Toast.makeText(HomeActivity.this, "please Select Your City.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(HomeActivity.this,RecyclerActivity.class);
                    intent.putExtra("city",city);
                    intent.putExtra("blood","AB-");
                    startActivity(intent);
                }

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRef.GetDb().getReference("totaluser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               final long totalUserCount = dataSnapshot.getChildrenCount();

                totalUserDisplay.setText("Total User : "+totalUserCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_likeFacebook) {
            return true;
        }
        if (id == R.id.action_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Just Try \n\n Member:\n\n\n 1. Md Asraf Uzzaman\n 2.Mizanul Islam\n 3. Amit Lal Das";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"SIU Save Life");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

            return true;
        }
        if (id == R.id.action_exit) {
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_admin) {
            // Handle the camera action
        } else if (id == R.id.nav_coordinator) {

        } else if (id == R.id.nav_donor) {

        } else if (id == R.id.nav_privacy_policy) {

        } else if (id == R.id.nav_Organization) {

        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_rate_us
        ) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}