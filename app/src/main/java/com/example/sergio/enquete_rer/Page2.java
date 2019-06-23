package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Page2 extends AppCompatActivity implements View.OnClickListener
{

    private TextView tvTitre;
    private String nom;
    private String prenom;
    private String age;
    private String frequence;
    private String profession;
    private String rer;
    private Button btSuivant;
    private RatingBar rbProprete, rbPonctualite, rbInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        this.tvTitre = (TextView) findViewById(R.id.idTitre);
        this.rer = this.getIntent().getStringExtra("rer").toString();
        this.tvTitre.setText("Satifaction sur le " + rer);

        this.nom = this.getIntent().getStringExtra("nom").toString();
        this.prenom = this.getIntent().getStringExtra("prenom").toString();
        this.age = this.getIntent().getStringExtra("age").toString();
        this.frequence = this.getIntent().getStringExtra("frequence").toString();
        this.profession = this.getIntent().getStringExtra("profession").toString();


        this.btSuivant = (Button) findViewById(R.id.idSuivant);
        this.rbProprete = (RatingBar) findViewById(R.id.idProprete);
        this.rbPonctualite = (RatingBar) findViewById(R.id.idPoctualite);
        this.rbInformation = (RatingBar) findViewById(R.id.idInformation);

        this.btSuivant.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.idSuivant)
        {
            int rproprete = (int) this.rbProprete.getRating();
            SNCF.ajouterUneReponse(nom, "proprete", rproprete, this.rer);

            int rponctualite = (int) this.rbPonctualite.getRating();
            SNCF.ajouterUneReponse(nom, "ponctualite", rponctualite, this.rer);

            int rinformation = (int) this.rbInformation.getRating();
            SNCF.ajouterUneReponse(nom, "information", rinformation, this.rer);

            Intent unIntent = new Intent(this, Page3.class);
            unIntent.putExtra("rer", this.rer);
            unIntent.putExtra("nom", nom);
            unIntent.putExtra("prenom", prenom);
            unIntent.putExtra("age", age);
            unIntent.putExtra("frequence", frequence);
            unIntent.putExtra("profession", profession);
            unIntent.putExtra("rproprete", rproprete);
            unIntent.putExtra("rponctualite", rponctualite);
            unIntent.putExtra("rinformation", rinformation);
            this.startActivity(unIntent);
        }
    }
}
