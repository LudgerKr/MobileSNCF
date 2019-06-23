package com.example.sergio.enquete_rer;

import java.util.ArrayList;
import java.util.HashMap;

public class SNCF
{
    private static HashMap<String, Enquete> lesRers = new HashMap<String, Enquete>();

    public static void remplirSNCF()
    {
        if( ! SNCF.lesRers.containsKey("RER A")) SNCF.lesRers.put("RER A", new Enquete());
        if( ! SNCF.lesRers.containsKey("RER B")) SNCF.lesRers.put("RER B", new Enquete());
        if( ! SNCF.lesRers.containsKey("RER C")) SNCF.lesRers.put("RER C", new Enquete());
        if( ! SNCF.lesRers.containsKey("RER D")) SNCF.lesRers.put("RER D", new Enquete());
        if( ! SNCF.lesRers.containsKey("RER E")) SNCF.lesRers.put("RER E", new Enquete());
    }

    public static void ajouterCandidat(Candidat unCandidat, String unRER)
    {
        SNCF.lesRers.get(unRER).ajouterCandidat(unCandidat);
    }

    public static void ajouterUneReponse(String nom, String question, int score, String unRER)
    {
        SNCF.lesRers.get(unRER).ajouterUneReponse(nom, question, score);
    }

    public static HashMap<ArrayList<String>, ArrayList<Integer>> getResultat(String unRER)
    {
        HashMap<ArrayList<String>, ArrayList<Integer>> res = new HashMap<ArrayList<String>, ArrayList<Integer>>();
        ArrayList<String> lesCandidats = SNCF.lesRers.get(unRER).listerCandidats();
        ArrayList<Integer> lesSmileys = SNCF.lesRers.get(unRER).listerSmiley();
        res.put(lesCandidats, lesSmileys);

        return res;
    }

    public static float getMoyenne()
    {
        float moy = 0;
        int nbCandidats = 0;
        //parcourt des enquetes
        for (Enquete uneEnquete : SNCF.lesRers.values())
        {
            for (Candidat unCandidat : uneEnquete.getLesCandidats().values())
            {
                moy = moy + unCandidat.getMoyenne();
            }
            nbCandidats += uneEnquete.getLesCandidats().size();
        }
        return (moy / nbCandidats);
    }

    public static ArrayList<String> listerCandidats (String rer)
    {
        return SNCF.lesRers.get(rer).listerCandidats();
    }

    public static ArrayList<Integer> listerSmiley (String rer)
    {
        return SNCF.lesRers.get(rer).listerSmiley();
    }

}