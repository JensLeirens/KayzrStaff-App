package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 7/09/2017.
 */

public class Tournament {
    private int id;
    private String naam;
    private String naamKort;
    private String dag;
    private String datum;
    private String uur;
    private String Moderator;

    public Tournament(int id, String naam, String naamKort, String dag, String datum, String uur, String moderator) {
        this.id = id;
        this.naam = naam;
        this.naamKort = naamKort;
        this.dag = dag;
        this.datum = datum;
        this.uur = uur;
        Moderator = moderator;
    }

    public Tournament() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNaamKort() {
        return naamKort;
    }

    public void setNaamKort(String naamKort) {
        this.naamKort = naamKort;
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUur() {
        return uur;
    }

    public void setUur(String uur) {
        this.uur = uur;
    }

    public String getModerator() {
        return Moderator;
    }

    public void setModerator(String moderator) {
        Moderator = moderator;
    }
}
