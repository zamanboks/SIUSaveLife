package com.example.siusavelife;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    Fullyear fullyear;
    ProgressBar progressbarProfile;
    User user;

    String ready;
    private static final int REQUEST_CALL = 1;
    TextView lastDonateProfile,dayleftProfile,nameProfile,bloodProfile,cityProfile,addressProfile,statusProfile,numberProfile,logOutProfile;
    ImageView imageProfile;
    Switch switchProfile;

    LinearLayout clickProfile;
    Calendar calendar;
    DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("totaluser").child(currentUserId);
        final DatabaseReference myyear = database.getReference("fullyear").child(currentUserId);


        user = new User();
        fullyear = new Fullyear();
        progressbarProfile = findViewById(R.id.progressbarProfile);

        nameProfile = findViewById(R.id.nameProfile);
        bloodProfile = findViewById(R.id.bloodProfile);
        cityProfile = findViewById(R.id.cityProfile);
        addressProfile = findViewById(R.id.addressProfile);
        statusProfile = findViewById(R.id.statusProfile);
        numberProfile = findViewById(R.id.numberProfile);

        imageProfile = findViewById(R.id.imageProfile);
        switchProfile = findViewById(R.id.switchProfile);
        logOutProfile = findViewById(R.id.logOutProfile);

        lastDonateProfile = findViewById(R.id.lastDonateProfile);
        dayleftProfile = findViewById(R.id.dayleftProfile);


        logOutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(ProfileActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });

        progressbarProfile.setVisibility(View.VISIBLE);

lastDonateProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        calendar = Calendar.getInstance();
        final int d2 = calendar.get(Calendar.DAY_OF_MONTH);
        final int m2 = calendar.get(Calendar.MONTH)-1;
        final int y2 = calendar.get(Calendar.YEAR);

        pickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int y1, final int m11, final int d1) {
                lastDonateProfile.setText(d1 + "/" + (m11+1) + "/" + y1);

                int m1 = m11-1;

                HashMap map = new HashMap();

                map.put("day",d1);
                map.put("month",m11);
                map.put("year",y1);

                myRef.child("fullyear").setValue(map);



            }
        },d2,m2,y2);
        pickerDialog.show();
    }
});



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    user = dataSnapshot.getValue(User.class);

                    String name = user.getName();
                    String phone = user.getPhone();
                    String email = user.getEmail();
                    String blood = user.getBlood();
                    String city = user.getCity();
                    String address = user.getAddress();
                    String password = user.getAddress();
                    String status = user.getStatus();

                    nameProfile.setText(name);
                    bloodProfile.setText(blood);
                    cityProfile.setText("City: "+city);
                    addressProfile.setText("Donate Location: "+address);
                    numberProfile.setText(""+phone);
                    statusProfile.setText("Status : "+status);

                    DataLoad(name,phone,email,blood,city,address,password);
                    progressbarProfile.setVisibility(View.GONE);

                }
            }

            private void DataLoad(final String name, final String phone, String email, final String blood, final String city, final String address, String password) {

                numberProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePhoneCall();
                    }
                        private void makePhoneCall() {
                            String number = phone.toString();

                            //with permission

                            if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(ProfileActivity.this,
                                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
                            }else{
                                String dial = "tel:" + number;
                                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
                            }


/*
//without permission
                            String dial = "tel:" + number;
                            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
 */
                        }

                });

                switchProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                            myRef.child("status").setValue("Can't Donate Now.");
                            FirebaseRef.GetDb().getReference(city).child("Users").child(blood).child(currentUserId).child("status").setValue("Can't Donate Now.");
                            statusProfile.setTextColor(Color.parseColor("#FFFF0000"));

                        }else{

                            myRef.child("status").setValue("Ready For Donate.");
                            FirebaseRef.GetDb().getReference(city).child("Users").child(blood).child(currentUserId).child("status").setValue("Ready For Donate.");
                            statusProfile.setTextColor(Color.parseColor("#FF03A50B"));
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        myRef.child("fullyear").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot myYear: dataSnapshot.getChildren())
                {
                    fullyear = dataSnapshot.getValue(Fullyear.class);

                    int aday = fullyear.getDay();
                    int amonth = fullyear.getMonth();
                    int ayear = fullyear.getYear();

                    lastDonateProfile.setText("Last Donate Date:\n"+aday+"/"+(amonth+1)+"/"+ayear);

                    int d1 = fullyear.getDay();
                    int m1 = fullyear.getMonth()-1;
                    int y1 = fullyear.getYear();

                    calendar = Calendar.getInstance();
                    final int d2 = calendar.get(Calendar.DAY_OF_MONTH);
                    final int m2 = calendar.get(Calendar.MONTH)-1;
                    final int y2 = calendar.get(Calendar.YEAR);

                    // date logic part

                    Date dat1 = new Date(y1, m1, d1);
                    Date dat2 = new Date(y2, m2, d2);
                    Date dat3 = new Date(1999, m2, d2);
                    Date dat4 = new Date(1999, m1, d1);

                    int year = y2 - y1;

                    if (m1 > m2) {
                        --year;
                    }else if (m1==m2 && d1>d2) {
                        --year;
                    }

                    long day = Math.round((dat3.getTime()-dat4.getTime())/(86400000));
                    int pl=0;
                    if(day<0) {
                        day+=365;
                        if(m1<2&&(m2>1&&(y2==y1||y2==y1+1))&&y1/4==Math.round(y1/4)&&y1!=2000)
                            pl=1;
                    }
                    if(m1<2&&m2>1&&y2==y1&&y1/4==Math.round(y1/4)&&y1!=2000)
                        pl=1;
                    if(m2>1&&y2>y1&&y2/4==Math.round(y2/4)&&y2!=2000)
                        pl=1;
                    day+=pl;

                    long diff = Math.round(Math.abs((dat2.getTime()-dat1.getTime())/(86400000)));

                    dayleftProfile.setText(diff+" Day Running..");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
        }
    }
 */



/*
        clickProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        lastDonateProfile.setText(mDay + "/" + (mMonth+1) + "/" + mYear);

                        HashMap map = new HashMap();
                        map.put("day",mDay);
                        map.put("month",mMonth);
                        map.put("year",mYear);


                        myyear.child("fullyear").setValue(map);

                    }
                },day,month,year);
                pickerDialog.show();
            }
        });
 */