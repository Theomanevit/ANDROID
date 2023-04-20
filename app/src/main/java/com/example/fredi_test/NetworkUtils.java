package com.example.fredi_test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    /**
     * Lance une requête REST et récupère une réponse JSON
     * @param url L'URL du service web
     * @return la réponse JSON
     */
    static String request(String url) {

        // Log.d(MainActivity.LOG_TAG,url);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = null;
        try {
            URL requestURL = new URL(url);
            // Ouvre une connexion
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
            /* Since it's JSON, adding a newline isn't necessary (it won't affect
            parsing) but it does make debugging a *lot* easier if you print out the
            completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.d(MainActivity.LOG_TAG,"Rien à charger, le buffer est vide");
                return  "Erreur : rien à afficher !";
            }
            jsonString = buffer.toString();
        } catch (IOException ex) {
            Log.d(MainActivity.LOG_TAG,"Erreur lors de la connexion au réseau");
            ex.printStackTrace();
            return "Erreur : " + ex.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Log.d(MainActivity.LOG_TAG,"Erreur lors de la fermeture du reader");
                    ex.printStackTrace();
                    return "Erreur : " + ex.getMessage();
                }
            }
        }
        //Log.d(MainActivity.LOG_TAG, jsonString);  // tests seulement
        return jsonString;
    }
}
