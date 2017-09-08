package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 7/09/2017.
 */

public class Tournament {
    private int id;
    private String naam;
    private String naamkort;
    private String dag;
    private String datum;
    private String uur;
    private String moderator;

    public Tournament(int id, String naam, String naamkort, String dag, String datum, String uur, String moderator) {
        this.id = id;
        this.naam = naam;
        this.naamkort = naamkort;
        this.dag = dag;
        this.datum = datum;
        this.uur = uur;
        this.moderator = moderator;
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

    public String getNaamkort() {
        return naamkort;
    }

    public void setNaamkort(String naamkort) {
        this.naamkort = naamkort;
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
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }
}
