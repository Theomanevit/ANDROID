package com.example.fredi_test;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Ligne {

    public int id;
    public String date;
    public String libelle;
    public String cout_peage;
    public String cout_repas;
    public String cout_hebergement;
    public String nb_km;
    public int cout_km;
    public String total_ligne;
    public String motif;

    /**
     * Constructeur
     * Construit un objet à partir d'un JSONObject
     * @param jsonObject
     */
    public Ligne(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            date = jsonObject.getString("date");
            libelle = jsonObject.getString("libelle");
            cout_peage = jsonObject.getString("cout_peage");
            cout_repas = jsonObject.getString("cout_repas");
            cout_hebergement = jsonObject.getString("cout_hebergement");
            nb_km = jsonObject.getString("nb_km");
            cout_km = jsonObject.getInt("cout_km");
            total_ligne = jsonObject.getString("total_ligne");
            motif = jsonObject.getString("motif");
        } catch (JSONException e) {
            Log.d(MainActivity.LOG_TAG,"Erreur lors de la conversion de l'objet JSON en objet Club");
            e.printStackTrace();
        }
    }

    /**
     * Convertit l'objet courant en array
     * @return le tableau
     */
    public String[] toArray() {
        String data[] = {
                Integer.toString(id),
                date,
                libelle,
                cout_peage,
                cout_repas,
                cout_hebergement,
                nb_km,
                Integer.toString(cout_km),
                total_ligne,
                motif
        };
        return data;
    }

}
