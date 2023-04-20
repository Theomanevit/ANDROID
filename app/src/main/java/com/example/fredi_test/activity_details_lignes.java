package com.example.fredi_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_details_lignes extends AppCompatActivity {

    Intent myIntent;

    TextView myViewId;
    TextView myViewDate;
    TextView myViewLibelle;
    TextView myViewCout_peage;
    TextView myViewCout_repas;
    TextView myViewHebergement;
    TextView myViewNombre_km;
    TextView myViewCout_km;
    TextView myViewCout_total;
    TextView myViewMotif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_lignes);

        // Récupère les données à afficher
        myIntent = getIntent();
        String myData[] = myIntent.getStringArrayExtra(MyAsyncTask.EXTRA_MESSAGE);

        // Affiche les données dans le layout
        myViewId = (TextView) findViewById(R.id.id);
        myViewId.setText("Ligne de frais n°" + myData[0]);

        myViewDate = (TextView) findViewById(R.id.V_Date);
        myViewDate.setText(myData[1]);

        myViewLibelle = (TextView) findViewById(R.id.V_Trajet);
        myViewLibelle.setText(myData[2]);

        myViewCout_peage = (TextView) findViewById(R.id.V_Coût_péage);
        myViewCout_peage.setText(myData[3]);

        myViewCout_repas = (TextView) findViewById(R.id.V_Coût_repas);
        myViewCout_repas.setText(myData[4]);

        myViewHebergement = (TextView) findViewById(R.id.V_Coût_hébergement);
        myViewHebergement.setText(myData[5]);

        myViewNombre_km = (TextView) findViewById(R.id.V_Nombre_de_Km);
        myViewNombre_km.setText(myData[6]);

        myViewCout_km = (TextView) findViewById(R.id.V_Coût_trajet);
        myViewCout_km.setText(myData[7]);

        myViewCout_total = (TextView) findViewById(R.id.V_Coût_total);
        myViewCout_total.setText(myData[8]);

        myViewMotif = (TextView) findViewById(R.id.V_Motif);
        myViewMotif.setText(myData[9]);

    }
}