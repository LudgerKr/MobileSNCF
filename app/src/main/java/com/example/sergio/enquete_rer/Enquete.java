package com.example.sergio.enquete_rer;

import com.example.sergio.enquete_rer.Controleur.Controleur;

import java.util.ArrayList;
import java.util.HashMap;

public class Enquete
{
    private  HashMap<String, Candidat> lesCandidats;

    //constructeur enquete
    public Enquete ()
    {
        this.lesCandidats = new HashMap<String, Candidat>();
    }

    public void ajouterCandidat (Candidat unCandidat)
    {
        this.lesCandidats.put(unCandidat.getNom(), unCandidat);
    }

    public Candidat getUnCandidat(String nom)
    {
        return this.lesCandidats.get(nom);
    }

    // Class conclusion, la page ou la fonction va récup all result de l'enquete
    public void insertData(String nom, String rer, Conclusion conclusion) {
        Controleur controleur = new Controleur(conclusion);
        controleur.insertData(this.lesCandidats.get(nom), rer);
    }

    public float getMoyenneUnCandidat(String nom)
    {
        return this.lesCandidats.get(nom).getMoyenne();
    }
    public int getSmileyUnCandidat(String nom)
    {
        return this.lesCandidats.get(nom).getSmiley();
    }
    public void ajouterUneReponse (String nom, String question, int score)
    {
        this.lesCandidats.get(nom).ajouterReponse(question,score);
    }
    public ArrayList<String> listerCandidats ()
    {
        ArrayList<String> laListe = new ArrayList<String>();
        float moy= 0;
        for(String nom : this.lesCandidats.keySet())
        {
            moy = this.getMoyenneUnCandidat(nom);
            laListe.add("Candidat : "+ nom + " | Moyenne : "+moy+ "/5.0");
        }
        return laListe;
    }
    public ArrayList<Integer> listerSmiley()
    {
        int id = 0;
        ArrayList<Integer> laListe = new ArrayList<Integer>();
        for (String nom : this.lesCandidats.keySet())
        {
            switch (this.getSmileyUnCandidat(nom))
            {
                case 1 : laListe.add(R.drawable.s1); break;
                case 2 : laListe.add(R.drawable.s2); break;
                case 3 : laListe.add(R.drawable.s3); break;
            }
        }
        return laListe;
    }

    public HashMap<String, Candidat> getLesCandidats() {
        return lesCandidats;
    }

    public void setLesCandidats(HashMap<String, Candidat> lesCandidats) {
        this.lesCandidats = lesCandidats;
    }
}
