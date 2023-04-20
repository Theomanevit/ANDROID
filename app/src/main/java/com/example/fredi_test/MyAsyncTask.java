package com.example.fredi_test;

//
// FREDI
//
// Tâche asynchrone lancée depuis activity_list_lignes_frais
// NOTA : on est obligé de lancer une tâche asynchrone car la connexion Internet peut prendre
// un certain temps, ce qui bloquerait activity_list_lignes_frais

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<String, Void, String> {

    public static final String EXTRA_MESSAGE = "com.Fredu.MESSAGE";

    // http://localhost/projets/top14server/clubs.php?user=jef&password=jefjef
    //http://localhost/projets/top14server-master/clubs.php?user=jef&password=jefjef
    String MyURL = "http://172.17.2.97/projets/FREDI/api/note_json.php";

    //String MyEmail = "test2@gmail.com";
    String MyEmail;

    //String MyPassword = "test2";
    String MyPassword;
    ListView myListView;
    Context myContext;
    ArrayList<Ligne> myLignes = new ArrayList<>();
    ArrayList<String> myArrayList = new ArrayList<>();
    Activity myActivity;

    /**
     * Constructeur
     * @param listView la listView qui va recevoir le contenu
     */
    public MyAsyncTask(ListView listView, Activity activity) {
        super();
        myListView = listView;
        myContext = listView.getContext();
        myActivity = activity;
    }

    /**
     * Quand on lance la tâche asynchrone (.execute() dans activity_list_lignes_frais)
     * @param authentification le login et le mot de passe
     * @return Chaîne JSON
     */
    @Override
    protected String doInBackground(String... authentification) {
        MyEmail = authentification[0];
        MyPassword = authentification[1];

        String url = MyURL + "?email=" + MyEmail + "&password=" + MyPassword;
        Log.d(MainActivity.LOG_TAG, "URL=" + url);
        // Accède à Internet, consomme un service Web en RESTful et renvoie un contenu JSON
        return NetworkUtils.request(url);
    }

    /**
     * Quand la tâche asynchrone est terminée
     * @param jsonString le contenu JSON renvoyé par la méthode doInBackground()
     */
    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);

        // Interprète le contenu JSON pour récupérer le token
        if (jsonString != null) {
            try {
                // Récupère le contenu du fichier JSON
                JSONObject jsonObject = new JSONObject(jsonString);
                String message = jsonObject.getString(":message");
                Log.d(MainActivity.LOG_TAG, ":message=" + message);  // Tests seulement
                // Récupère la ligne des lignes
                if (jsonObject.isNull("lignes")==false) {
                    JSONArray lignesArray = jsonObject.getJSONArray("lignes");
                    // Boucle de lecture des lignes
                    for (int i = 0; i < lignesArray.length(); i++) {
                        JSONObject ligneJsonObject = lignesArray.getJSONObject(i);
                        // Crée un objet métier ligne à partir de l'objet JSONObject
                        Ligne ligne = new Ligne(ligneJsonObject);
                        // Ajoute l'objet métier dans la collection ArrayList<Ligne>
                        myLignes.add(ligne);
                        // Ajoute le libellé du ligne dans la collection ArrayList<String>
                        myArrayList.add(String.valueOf(ligne.id) + "  |  " + ligne.motif + "  |  " + ligne.libelle + "  |  " + ligne.total_ligne);
                        // Affiche un message en bas de ligne
                        TextView textView = (TextView) myActivity.findViewById(R.id.tv_message);
                        textView.setText(String.valueOf(lignesArray.length()) + " ligne(s)");

                    }
                } else {
                    // Pas de ligne ligne à afficher
                    myArrayList.add("Rien à afficher !");
                    Toast.makeText(myContext, "Rien à afficher !", Toast.LENGTH_LONG).show();
                    Log.d(MainActivity.LOG_TAG, "Rien à afficher");
                    // Affiche un message en bas de ligne
                    TextView textView = (TextView) myActivity.findViewById(R.id.tv_message);
                    textView.setText("Rien à afficher");

                }

            } catch (Exception e) {
                Log.d(MainActivity.LOG_TAG, "Erreur lors de la lecture du fichier JSON");
                e.printStackTrace();
            }
        } else {
            Log.d(MainActivity.LOG_TAG, "Erreur : le fichier JSON est vide !");
        }

        // Remplit la listView
        // Crée l'adaptateur
        final ArrayAdapter<String> myAdapter = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1, myArrayList);

        // Associe l'adapteur à la listView
        myListView.setAdapter(myAdapter);

        // Ajoute un gestionnaire d'événement sous la forme d'une classe anonyme
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // Instancie la ligne pointé par le clic sur la listView
                        Ligne myLigne= myLignes.get(position);
                        // Transforme l'objet Club en array (tableau) pour pouvoir fournir les détails à l'activity suivante
                        // NOTA : les intents n'acceptent pas les objets, seulement des strings et des array de strings
                        String myData[] = myLigne.toArray();
                        // Création de l'intent pour DetailsActivity
                        Intent myIntent = new Intent(myContext, activity_details_lignes.class);
                        // Ajoute dans l'intent le tableau contenant les détails du ligne
                        myIntent.putExtra(EXTRA_MESSAGE, myData);
                        // Lancement de l'activité
                        myActivity.startActivity(myIntent);
                        // test
                        String chaine = myAdapter.getItem(position);
                        Log.d(MainActivity.LOG_TAG, "Clic sur" + chaine);
                    }
                }
        );

    }
}