package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Page3 extends AppCompatActivity implements View.OnClickListener
{
    private TextView tvTitre;
    private String nom;
    private String prenom;
    private String age;
    private String frequence;
    private String profession;
    private int rproprete;
    private int rponctualite;
    private int rinformation;
    private int rconfort;
    private int rqualite;
    private int rgenerale;
    private String rer;
    private Button btSuivant;
    private RatingBar rbConfort, rbQualite, rbGenerale;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        this.tvTitre = (TextView) findViewById(R.id.idTitre);
        this.rer = this.getIntent().getStringExtra("rer").toString();
        this.tvTitre.setText("Satifaction sur le " + rer);

        this.nom = this.getIntent().getStringExtra("nom").toString();
        this.prenom = this.getIntent().getStringExtra("prenom").toString();
        this.age = this.getIntent().getStringExtra("age").toString();
        this.frequence = this.getIntent().getStringExtra("frequence").toString();
        this.profession = this.getIntent().getStringExtra("profession").toString();
        this.rproprete = this.getIntent().getIntExtra("rproprete", rproprete);
        this.rponctualite = this.getIntent().getIntExtra("rponctualite", rponctualite);
        this.rinformation = this.getIntent().getIntExtra("rinformation", rinformation);

        this.btSuivant = (Button) findViewById(R.id.idSuivant2);
        this.rbConfort = (RatingBar) findViewById(R.id.idConfort);
        this.rbQualite = (RatingBar) findViewById(R.id.idQualite);
        this.rbGenerale = (RatingBar) findViewById(R.id.idGenerale);

        this.btSuivant.setOnClickListener(this);

    }

    public float getMoyenne()
    {
        float moyenne = 0;
        moyenne += this.rconfort + this.rgenerale + this.rqualite + this.rinformation + this.rponctualite + this.rproprete;
        moyenne /= 6;
        return moyenne;
    }

    public String getSmileyName()
    {
        if (this.getMoyenne()<2) return "sp3.png";
        else if (2<this.getMoyenne() && this.getMoyenne()<3.5) return "sp2.png";
        else return "sp1.png";
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.idSuivant2)
        {
            this.rconfort = (int) this.rbConfort.getRating();
            SNCF.ajouterUneReponse(nom, "confort", rconfort, this.rer);

            this.rqualite = (int) this.rbQualite.getRating();
            SNCF.ajouterUneReponse(nom, "qualite", rqualite, this.rer);

            this.rgenerale = (int) this.rbGenerale.getRating();
            SNCF.ajouterUneReponse(nom, "generale", rgenerale, this.rer);

            Intent unIntent = new Intent(this, Conclusion.class);
            unIntent.putExtra("nom", this.nom);
            unIntent.putExtra("rer", this.rer);
            unIntent.putExtra("prenom", prenom);
            unIntent.putExtra("age", age);
            unIntent.putExtra("frequence", frequence);
            unIntent.putExtra("profession", profession);
            unIntent.putExtra("rproprete", rproprete);
            unIntent.putExtra("rponctualite", rponctualite);
            unIntent.putExtra("rinformation", rinformation);
            unIntent.putExtra("rconfort", rconfort);
            unIntent.putExtra("rqualite", rqualite);
            unIntent.putExtra("rgenerale", rgenerale);

            databaseManager = new DatabaseManager( this );
            databaseManager.insertCandidat(this.nom, this.prenom, this.frequence, this.profession,
                    this.age, this.getMoyenne(), this.getSmileyName(), this.rer) ;
            this.startActivity(unIntent);
        }
    }
}
