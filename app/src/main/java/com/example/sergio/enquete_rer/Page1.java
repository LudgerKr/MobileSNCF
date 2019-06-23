package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Page1 extends AppCompatActivity implements View.OnClickListener
{
    private TextView tvTitre;
    private String rer;
    private Button btParticiper;
    private Spinner spAge, spFrequence, spProfession;
    private EditText txtNom, txtPrenom;
    private float moyenne;
    private String smiley;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        this.tvTitre = (TextView) findViewById(R.id.idTitre);
        this.rer = this.getIntent().getStringExtra("rer").toString();
        this.tvTitre.setText("Satifaction sur le " + rer);

        this.btParticiper = (Button) findViewById(R.id.idParticiper);
        this.spAge = (Spinner) findViewById(R.id.idAge);
        this.spFrequence = (Spinner) findViewById(R.id.idFrequence);
        this.spProfession = (Spinner) findViewById(R.id.idProfession);
        this.txtNom = (EditText) findViewById(R.id.idNom);
        this.txtPrenom = (EditText) findViewById(R.id.idPrenom);

        this.btParticiper.setOnClickListener(this);

        //remplir spinner age
        ArrayList<String> lesAges = new ArrayList<String>();
        lesAges.add("0 - 18 ans");
        lesAges.add("18 - 35 ans");
        lesAges.add("35 - 65 ans");
        lesAges.add("65 - 65 + ans");
        ArrayAdapter unAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesAges);
        this.spAge.setAdapter(unAdapter);

        //remplir spinner frequence
        ArrayList<String> lesFrequences = new ArrayList<String>();
        lesFrequences.add("Quotidienne");
        lesFrequences.add("Hebdomadaire");
        lesFrequences.add("Mensuelle");
        lesFrequences.add("Annuelle");
        ArrayAdapter unAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesFrequences);
        this.spFrequence.setAdapter(unAdapter2);

        //remplir spinner profession
        ArrayList<String> lesProfessions = new ArrayList<String>();
        lesProfessions.add("Etudiant");
        lesProfessions.add("Fonctionnaire");
        lesProfessions.add("Salarié");
        lesProfessions.add("Autre");
        ArrayAdapter unAdapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lesProfessions);
        this.spProfession.setAdapter(unAdapter3);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.idParticiper)
        {
            String nom = this.txtNom.getText().toString();
            String prenom = this.txtPrenom.getText().toString();
            String age = this.spAge.getSelectedItem().toString();
            String frequence = this.spFrequence.getSelectedItem().toString();
            String profession = this.spProfession.getSelectedItem().toString();

            //enregistrement du candidat
            Candidat unCandidat = new Candidat(nom, prenom, age, frequence, profession, moyenne, smiley, rer);
            SNCF.ajouterCandidat(unCandidat, this.rer);

            //passage à la première page de l'enquete
            Intent unIntent = new Intent(this, Page2.class);
            unIntent.putExtra("rer", this.rer);
            unIntent.putExtra("nom", nom);
            unIntent.putExtra("prenom", prenom);
            unIntent.putExtra("age", age);
            unIntent.putExtra("frequence", frequence);
            unIntent.putExtra("profession", profession);
            this.startActivity(unIntent);

        }
    }
}
