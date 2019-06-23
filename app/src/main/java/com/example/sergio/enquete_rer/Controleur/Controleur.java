package com.example.sergio.enquete_rer.Controleur;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.sergio.enquete_rer.Candidat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controleur {
    private String adresse = "http://51.68.230.246:8001";
    private Context context;

    public Controleur(Context context){
        this.context = context;
    }

    public void insertData(Candidat candidat, String rer) {
        List<String> keys = new ArrayList<>();

        List<String> values= new ArrayList<>();

        keys.add("prenom");
        values.add(candidat.getPrenom());

        keys.add("nom");
        values.add(candidat.getNom());

        keys.add("frequence");
        values.add(candidat.getFrequence());

        keys.add("tranche");
        values.add(candidat.getAge());

        for (Map.Entry<String,Integer> entry : candidat.getLesReponses().entrySet()) {
            keys.add(entry.getKey()+"Q");
            values.add(String.valueOf(entry.getValue()));
        }

        keys.add("moyenne");
        values.add(String.valueOf(candidat.getMoyenne()));

        keys.add("smiley");
        values.add(candidat.getSmileyName());

        keys.add("rer");
        values.add(rer);

        this.post("http://51.68.230.246:8001",keys,values,"insertDataCallback");
    }

    public void insertDataCallback(String reponse) {
        try {
            JSONObject obj = new JSONObject(reponse);
            if (obj.getString("rep").equals("failed")) {
                JSONArray errorsList = obj.getJSONArray("errors");
                String str = "Insertion échouée : \n\n";

                for (int i = 0;i<errorsList.length();i++) {
                    str += "\t\t"+errorsList.get(i)+"\n";
                }
                this.msg("Erreur",str);
            }
        } catch(JSONException e) {
            this.msg("Erreur JSON 1 : ",e.toString());
        }
    }

    private void post(String file, List<String> keys, List<String> values, String callback) {
        new Post(this.adresse+file,this, callback).execute(keys,values);
    }

    public void msg(String titre, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
