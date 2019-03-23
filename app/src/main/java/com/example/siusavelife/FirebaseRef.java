package com.example.siusavelife;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRef {

    public static FirebaseDatabase database;
    public static FirebaseDatabase GetDb(){
        if (database ==null){
            database =  FirebaseDatabase.getInstance();
        }

        return database;
    }
}
