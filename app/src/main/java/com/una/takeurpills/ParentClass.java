package com.una.takeurpills;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;

/**
 * Created by BryJohSeb on 4/20/2017.
 */

public class ParentClass extends AppCompatActivity {
    static FirebaseAuth mAuth;
    static JSONArray testjarray = new JSONArray();
    static int modo = 0;

    static FirebaseDatabase database;
    static DatabaseReference myRef;
}
