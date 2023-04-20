package com.example.fredi_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String LOG_TAG = "bat21"; // Tag pour la log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void connexion(View view) {
        // Création de l'intent pour Login
        Intent myIntent = new Intent(this, loginActivity.class);
        // Lancement de l'activité
        startActivity(myIntent);
    }

}