package com.example.sergio.enquete_rer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageButton ibRerA, ibRerB, ibRerC, ibRerD, ibRerE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ibRerA = (ImageButton) findViewById(R.id.idrera);
        this.ibRerB = (ImageButton) findViewById(R.id.idrerb);
        this.ibRerC = (ImageButton) findViewById(R.id.idrerc);
        this.ibRerD = (ImageButton) findViewById(R.id.idrerd);
        this.ibRerE = (ImageButton) findViewById(R.id.idrere);

        this.ibRerA.setOnClickListener(this);
        this.ibRerB.setOnClickListener(this);
        this.ibRerC.setOnClickListener(this);
        this.ibRerD.setOnClickListener(this);
        this.ibRerE.setOnClickListener(this);

        SNCF.remplirSNCF();
    }

    @Override
    public void onClick(View view)
    {
        Intent unIntent = new Intent(this,Page1.class);
        String rer = "";
        switch (view.getId())
        {
            case R.id.idrera : rer = "RER A"; break;
            case R.id.idrerb : rer = "RER B"; break;
            case R.id.idrerc : rer = "RER C"; break;
            case R.id.idrerd : rer = "RER D"; break;
            case R.id.idrere : rer = "RER E"; break;
        }
        unIntent.putExtra("rer", rer);
        this.startActivity(unIntent);
    }
}
