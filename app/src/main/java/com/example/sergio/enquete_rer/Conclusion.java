package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Conclusion extends AppCompatActivity implements View.OnClickListener{

    private String nom, rer;
    private String prenom;
    private String age;
    private String frequence;
    private String profession;
    private Button btRetour;
    private Button btInfo;
    private Button btSupp;
    private ListView lvListe;
    private int rproprete;
    private int rponctualite;
    private int rinformation;
    private int rconfort;
    private int rqualite;
    private int rgenerale;
    private DatabaseManager databaseManager;
    private TextView moyenneView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion);

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

        this.btRetour = (Button)findViewById(R.id.idRetour);
        this.btSupp = (Button)findViewById(R.id.idSupp);
        this.btInfo = (Button)findViewById(R.id.idInfo);
        this.lvListe = (ListView)findViewById(R.id.idListeInfo);
        this.moyenneView = (TextView) findViewById( R.id.moyenneView);


        this.btRetour.setOnClickListener(this);
        this.btSupp.setOnClickListener(this);
        this.btInfo.setOnClickListener(this);

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
        if (R.id.idRetour ==v.getId())
        {
            Intent unIntent = new Intent(this,MainActivity.class);

            this.startActivity(unIntent);
        }
        if (R.id.idInfo ==v.getId())
        {
            databaseManager = new DatabaseManager( this );
            List<Candidat> moyenne = databaseManager.readTop10();
            for ( Candidat candidat : moyenne ) {
                moyenneView.append(candidat.toString() +"\n\n");
            }

            databaseManager.close();
        }
        if (R.id.idSupp ==v.getId())
        {
            Intent unIntent = getIntent();
            databaseManager.deleteCandidat(this.nom);
            this.startActivity(unIntent);
        }
    }
}
