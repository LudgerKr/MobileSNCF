package com.example.sergio.enquete_rer;

import java.util.HashMap;

public class Candidat {
    private int idCandidat;
    private String nom, prenom, age, frequence, profession, smiley, rer;
    private float moyenne;
    private HashMap<String, Integer> lesReponses;

    public Candidat(String nom, String prenom, String age, String frequence, String profession,
                    float moyenne, String smiley, String rer) {
        this.setIdCandidat(idCandidat);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setAge(age);
        this.setFrequence(frequence);
        this.setProfession(profession);
        this.moyenne = moyenne;
        this.setSmiley(smiley);
        this.setRer(rer);
        this.lesReponses = new HashMap<String, Integer>();
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public void ajouterReponse(String question, int score) {
        this.lesReponses.put(question, score);
    }

    public float getMoyenne() {
        this.moyenne = 0;
        for (Integer score : this.lesReponses.values()) {
            this.moyenne += score;
        }
        this.moyenne /= this.lesReponses.size();
        return this.moyenne;
    }

    public int getSmiley() {
        if (this.getMoyenne() < 2.0) { return 3; }
        else if (2 <= this.getMoyenne() && this.getMoyenne() <= 3.5) { return 2; }
        else if (this.getMoyenne() > 3.5) { return 1; }
        else return 1;
    }

    public String getSmileyName() {
        if (getMoyenne() < 2.0 || getMoyenne() == 0.0) { return "sp1.png"; }
        else if (2 <= getMoyenne() && getMoyenne()<= 3.5) { return "sp2.png"; }
        else if (getMoyenne() > 3.5) { return "sp3.png"; }
        else { return "sp1.png"; }
    }

    public void setRer(String rer) {
        this.rer = rer;
    }

    public String getRer() {
        return rer;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public void setSmiley(String smiley) {
        this.smiley = smiley;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public HashMap<String, Integer> getLesReponses() {
        return lesReponses;
    }

    public void setLesReponses(HashMap<String, Integer> lesReponses) {
        this.lesReponses = lesReponses;
    }


    @Override
    public String toString() {
        return getNom()+ "  " +getPrenom() + " à la moyenne de " + moyenne + "/5.0 " + getRer() +"\n"
                + "Fréquence : " + age + "\n" + " Profession : " + frequence + "\n" +
                "Agé de : " + profession + "\n" + "Smiley " + smiley;
    }
}