package com.project;

import java.time.LocalDateTime;

public class Transakcija {
    private int brojRacuna;
    private TipTransakcije tipTransakcije;
    private double iznosTransakcije;
    private LocalDateTime datum;

    public Transakcija(int brojRacuna, TipTransakcije tipTransakcije, double iznosTransakcije) {
        this.brojRacuna = brojRacuna;
        this.tipTransakcije = tipTransakcije;
        this.iznosTransakcije = iznosTransakcije;
        this.datum = LocalDateTime.now();
    }

    public int getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(int brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public TipTransakcije getTipTransakcije() {
        return tipTransakcije;
    }

    public void setTipTransakcije(TipTransakcije tipTransakcije) {
        this.tipTransakcije = tipTransakcije;
    }

    public double getIznosTransakcije() {
        return iznosTransakcije;
    }

    public void setIznosTransakcije(double iznosTransakcije) {
        this.iznosTransakcije = iznosTransakcije;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "--------------------------------------" + "\nTip transakcije: "
                + getTipTransakcije() + "\nIznos transakcije: " + getIznosTransakcije() + "\nDatum transakcije: " + getDatum();
    }
}
