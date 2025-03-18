package com.project;

import com.project.DAO.RacunDAO;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


/**
 * Klasa koja predstavlja bankovni račun s osnovnim operacijama
 * poput uplate, isplate i pregleda stanja.
 */
public class Racun {
    Scanner scanner = new Scanner(System.in);
    private int brojRacuna;
    private String imeVlasnika;
    private double stanje;
    private static final RacunDAO racunDAO = new RacunDAO();

    /**
     * Konstruktor koji kreira novi račun s automatski generiranim brojem.
     * @param imeVlasnika Ime vlasnika računa.
     */
    public Racun(String imeVlasnika) {
        this.brojRacuna = generirajBrojRacuna();
        this.imeVlasnika = imeVlasnika;
        this.stanje = 0;
    }


    /**
     * Konstruktor koji inicijalizira račun s definiranim podacima.
     * @param brojRacuna Broj računa.
     * @param imeVlasnika Ime vlasnika računa.
     * @param stanje Početno stanje na računu.
     */
    public Racun(int brojRacuna,String imeVlasnika, double stanje) {
        this.brojRacuna = brojRacuna;
        this.imeVlasnika = imeVlasnika;
        this.stanje = stanje;
    }


    /**
     * Generira nasumičan broj računa.
     * @return Generirani broj računa.
     */
    public static int generirajBrojRacuna(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * Metoda za uplatu sredstava na račun.
     * @param brojRacuna Broj računa na koji se uplaćuje.
     * @param iznos Iznos koji se uplaćuje.
     */
    public void uplati(int brojRacuna, double iznos){
        try {
            if(iznos<=0){
                System.out.println("Minimalan iznos za uplatu je 1€");
                return;
            }
            Racun racun = racunDAO.getRacunByBrojRacun(brojRacuna);
            if(racun == null){
                System.out.println("Račun s brojem " + brojRacuna + " ne postoji.");
                return;
            }
            racun.stanje += iznos;
            racunDAO.updateRacun(brojRacuna, racun.stanje);
            System.out.println("Uspješno ste uplatili iznos od: " + String.format("%.2f", iznos) + " na racun: " + brojRacuna);

        }catch (InputMismatchException ex){
            System.out.println("Greška pri unosu iznosa! Molimo unesite broj.");
            scanner.nextLine();
        }
    }

    /**
     * Metoda za isplatu sredstava s računa.
     * @param brojRacuna Broj računa s kojeg se isplaćuje.
     * @param iznos Iznos koji se isplaćuje.
     */
    public void isplati(int brojRacuna, double iznos){
        try {
            if(iznos<=0){
                System.out.println("Iznos mora biti veći od 0.");
                return;
            }
            if(stanje == 0){
                System.out.println("Vaše stanje računa je: " + stanje + "€, stoga ne mozete podici iznos od " + String.format("%.2f", iznos) + "€");
                return;
            }
            if (iznos>stanje) {
                System.out.println("Nemate dovoljno sredstava. Vaše trenutno stanje: " + String.format("%.2f", stanje) + "€");
                return;
            }

            stanje -= iznos;
            racunDAO.updateRacun(brojRacuna,stanje);
            System.out.println("Uspješno ste podigli iznos od: " + String.format("%.2f", iznos) + "€ s računa broj: " + brojRacuna);
        }catch (InputMismatchException ex){
            System.out.println("Greška pri unosu iznosa! Molimo unesite broj.");
            scanner.nextLine();
        }
    }

    public void transferiraj(Racun racunPrimatelja, double iznos){
        if (this.stanje >= iznos) {
            this.stanje -= iznos;

            racunPrimatelja.stanje += iznos;

            System.out.println("Transfer uspješan! " + iznos + "€ je preneseno.");
        } else {
            System.out.println("Nemate dovoljno sredstava za transfer.");
        }
    }


    /**
     * Prikazuje trenutno stanje računa.
     */
    public void prikaziStanje(){
        RacunDAO racunDAO = new RacunDAO();
        stanje = racunDAO.getStanjeRacuna(brojRacuna);
        if (stanje != -1) {
            System.out.println("Trenutno stanje računa broj " + brojRacuna + " iznosi: " + String.format("%.2f", stanje));
        } else {
            System.out.println("Račun s brojem " + brojRacuna + " nije pronađen.");
        }
    }

    public int getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(int brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public String getImeVlasnika() {
        return imeVlasnika;
    }

    public void setImeVlasnika(String imeVlasnika) {
        this.imeVlasnika = imeVlasnika;
    }

    public double getStanje() {
        return stanje;
    }

    public void setStanje(double stanje) {
        this.stanje = stanje;
    }
}

