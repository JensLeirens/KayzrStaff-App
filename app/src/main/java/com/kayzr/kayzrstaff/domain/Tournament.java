package com.kayzr.kayzrstaff.domain;

import java.util.List;

public class Tournament {
    private String id;
    private String naam;
    private String naamkort;
    private String dag;
    private String datum;
    private String uur;
    private boolean cancelled;
    private List<User> moderators;

    public Tournament(String id, String naam, String naamkort, String dag, String datum, String uur, List<User> moderators) {
        this.id = id;
        this.naam = naam;
        this.naamkort = naamkort;
        this.dag = dag;
        this.datum = datum;
        this.uur = uur;
        this.moderators = moderators;
    }

    public Tournament() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<User> getModerators() {
        return moderators;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
