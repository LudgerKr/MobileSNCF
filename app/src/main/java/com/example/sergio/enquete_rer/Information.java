package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Information extends AppCompatActivity implements View.OnClickListener{

    private String nom, prenom, age, frequence, profession, rer;
    private Button btRetour;
    private ListView lvListe;
    private int rproprete;
    private int rponctualite;
    private int rinformation;
    private int rconfort;
    private int rqualite;
    private int rgenerale;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        this.nom = this.getIntent().getStringExtra("nom").toString();
        this.rer = this.getIntent().getStringExtra("rer").toString();
        this.prenom = this.getIntent().getStringExtra("prenom").toString();
        this.age = this.getIntent().getStringExtra("age").toString();
        this.frequence = this.getIntent().getStringExtra("frequence").toString();
        this.profession = this.getIntent().getStringExtra("profession").toString();
        this.rproprete = this.getIntent().getIntExtra("rproprete", rproprete);
        this.rponctualite = this.getIntent().getIntExtra("rponctualite", rponctualite);
        this.rinformation = this.getIntent().getIntExtra("rinformation", rinformation);
        this.rconfort = this.getIntent().getIntExtra("rconfort", rconfort);
        this.rqualite = this.getIntent().getIntExtra("rqualite", rqualite);
        this.rgenerale = this.getIntent().getIntExtra("rgenerale", rgenerale);

        this.btRetour = (Button)findViewById(R.id.idRetour3);
        this.lvListe = (ListView)findViewById(R.id.idListeInfo);

        this.btRetour.setOnClickListener(this);

        ArrayList<String> laListeC = SNCF.listerCandidats(this.rer);
        ArrayList<Integer> laListeI = SNCF.listerSmiley(this.rer);

        int tabI[] = new int[laListeI.size()];
        int i = 0;
        for (Integer element : laListeI)
        {
            tabI[i++] = element;
        }
        i = 0;
        String tabC [] = new String[laListeC.size()];
        for (String element : laListeC)
        {
            tabC[i++] = element;
        }

        Adapter_liste unAdaptateur = new Adapter_liste(this,tabC,tabI);

        this.lvListe.setAdapter(unAdaptateur);
    }

    @Override
    public void onClick(View v) {
        if (R.id.idRetour3 ==v.getId())
        {
            Intent unIntent = new Intent(this,MainActivity.class);

            this.startActivity(unIntent);
        }
    }
}
