package com.project;

import com.project.DAO.RacunDAO;
import com.project.DAO.TransakcijaDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa koja predstavlja bankovni sustav.
 * Omogućuje upravljanje računima i transakcijama.
 */
public class Banka {
    public Scanner scanner = new Scanner(System.in);
    List<Racun> racuni = new ArrayList<>();
    List<Transakcija> transakcije= new ArrayList<>();

    private static final TransakcijaDAO transakcijaDAO = new TransakcijaDAO();



    /**
     * Konstruktor klase Banka.
     * @param racuni Lista računa u banci.
     * @param transakcije Lista transakcija u banci.
     */
    public Banka(List<Racun> racuni, List<Transakcija> transakcije) {
        this.racuni = racuni;
        this.transakcije = transakcije;
    }

//    public void spremiRacuneUDatoteku(){
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\gorangutan\\Desktop\\2025 Java\\Sustav-za-upravljanje-bankovnim-racunima\\src\\main\\java\\com\\project\\racuni.txt"))){
//            for(Racun racun : racuni){
//                writer.write(racun.getBrojRacuna() + "," + racun.getImeVlasnika() + "," + racun.getStanje());
//                writer.newLine();
//            }
//        }catch (IOException ex){
//            System.out.println("Greška pri spremanju računa: " + ex.getMessage());
//        }
//    }

//    public void ucitajRacuneIzDatoteke(){
//        File file = new File("C:\\Users\\gorangutan\\Desktop\\2025 Java\\Sustav-za-upravljanje-bankovnim-racunima\\src\\main\\java\\com\\project\\racuni.txt");
//
//        if(!file.exists()){
//            System.out.println("Datoteka s računima ne postoji.");
//            return;
//        }
//        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String linija;
//            boolean imaPodataka = false;
//
//            while ((linija = reader.readLine()) !=null){
//                String[] podaci = linija.split(",");
//                if(podaci.length != 3){
//                    System.out.println("Upozorenje: Preskočen neispravan red u datoteci.");
//                    continue;
//                }
//                int brojRacuna = Integer.parseInt(podaci[0]);
//                String imeIprezime = podaci[1];
//                double stanje = Double.parseDouble(podaci[2]);
//
//                Racun racun = new Racun(brojRacuna, imeIprezime, stanje);
//                racuni.add(racun);
//                imaPodataka = true;
//            }
//            if (!imaPodataka) {
//                System.out.println("Datoteka je prazna, nema računa za učitati.");
//            }
//
//        }
//        catch (IOException ex){
//            System.out.println("Greška pri učitavanju računa: " + ex.getMessage());
//        }
//        catch (NumberFormatException ex){
//            System.out.println("Greška: Datoteka sadrži neispravan broj!");
//        }
//    }


    /**
     * Prikazuje sve transakcije za određeni račun.
     */
    public void pregledSvihTransakcija(){
        System.out.println("Upišite broj računa za koji želite vidjeti provedene transakcije:");
        int brojRacuna;
        try{
            brojRacuna = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException ex){
            System.out.println("Molimo unesite ispravan broj računa!");
            scanner.nextLine();
            return;
        }

        List<Transakcija> transakcije = transakcijaDAO.getAllTransakcije();

        boolean postoji = false;
        for(Transakcija transakcija : transakcije){
            if(transakcija.getBrojRacuna() == brojRacuna){
                System.out.println(transakcija);
                postoji = true;
            }
        }
        if(!postoji){
            System.out.println("Nema transakcija za ovaj račun.");
        }
    }

    /**
     * Prikazuje sve uplatne transakcije za određeni račun.
     */
    public void pregledUplatnihTransakcija(){
        System.out.println("Upišite broj računa za koji želite vidjeti uplatne transakcije:");
        int brojRacuna;
        try{
            brojRacuna = scanner.nextInt();
            scanner.nextLine();
        }catch (InputMismatchException ex){
            System.out.println("Molimo unesite ispravan broj računa!");
            scanner.nextLine();
            return;
        }

        List<Transakcija> transakcije = transakcijaDAO.getAllTransakcije();

        boolean postoji = false;
        for(Transakcija transakcija : transakcije){
            if(transakcija.getBrojRacuna() == brojRacuna && transakcija.getTipTransakcije() == TipTransakcije.UPLATA){
                System.out.println(transakcija);
                postoji = true;
            }
        }
        if(!postoji){
            System.out.println("Nema uplatnih transakcija za ovaj račun.");
        }
    }

    /**
     * Prikazuje sve isplatne transakcije za određeni račun.
     */
    public void pregledIsplatnihTransakcija(){
        System.out.println("Upišite broj računa za koji želite vidjeti isplatne transakcije:");
        int brojRacuna;

        try {
            brojRacuna = scanner.nextInt();
            scanner.nextLine();
        }catch (InputMismatchException ex){
            System.out.println("Molimo unesite ispravan broj računa!");
            scanner.nextLine();
            return;
        }
        List<Transakcija> transakcije = transakcijaDAO.getAllTransakcije();

        boolean postoji = false;
        for(Transakcija transakcija : transakcije){
            if(transakcija.getBrojRacuna() == brojRacuna && transakcija.getTipTransakcije() == TipTransakcije.ISPLATA){
                System.out.println(transakcija);
                postoji = true;
            }
        }
        if(!postoji){
            System.out.println("Nema isplatnih transakcija za ovaj račun.");
        }
    }


    /**
     * Prikazuje sve transfere s jednog na drugi račun.
     */
    public void pregledTransfera(){
        System.out.println("Upišite broj računa za koji želite vidjeti isplatne transakcije:");
        int brojRacuna;

        try {
            brojRacuna = scanner.nextInt();
            scanner.nextLine();
        }catch (InputMismatchException ex){
            System.out.println("Molimo unesite ispravan broj računa!");
            scanner.nextLine();
            return;
        }

        List<Transakcija> transakcije = transakcijaDAO.getAllTransakcije();

        boolean postoji = false;
        for(Transakcija transakcija : transakcije){
            if(transakcija.getBrojRacuna() == brojRacuna && transakcija.getTipTransakcije() == TipTransakcije.TRANSFER){
                System.out.println(transakcija);
                postoji = true;
            }
        }
        if(!postoji){
            System.out.println("Nema transfera za ovaj račun.");
        }
    }

    /**
     * Pronalazi racun po broju racuna vlasnika.
     * @param brojRacuna oznacava broj racuna vlasnika.
     */
    public Racun pronadiRacun(int brojRacuna){
        for(Racun racun : racuni){
            if(racun.getBrojRacuna() == brojRacuna){
                return racun;
            }
        }
        return null;
    }

    /**
     * Kreira novi račun.
     * @param imeIprezime Ime i prezime vlasnika računa.
     */
    public void kreirajRacun(String imeIprezime){
        RacunDAO racunDAO = new RacunDAO();
        if(imeIprezime == null || imeIprezime.trim().isEmpty()){
            System.out.println("Ime vlasnika ne može biti prazno!");
            return;
        }

        for(Racun racun : racuni){
            if(racun.getImeVlasnika().equals(imeIprezime)){
                System.out.println("Ne možete kreirati novi račun jer već imate jedan.");
                return;
            }
        }
        Racun noviRacun= new Racun(imeIprezime);
        racunDAO.createRacun(noviRacun);
        racuni.add(noviRacun);
    }



    public void obrisiRacun(int brojRacuna){
        RacunDAO racunDAO = new RacunDAO();
        Racun racunZaBrisanje = null;

        // Pronađi račun po broju računa
        for(Racun racun : racuni){
            if(racun.getBrojRacuna() == brojRacuna){
                racunZaBrisanje = racun;
                break;
            }
        }

        if(racunZaBrisanje == null){
            System.out.println("Račun s brojem " + brojRacuna + " ne postoji.");
            return;
        }

        // Provjeri račun u bazi
        Racun racunIzBaze = racunDAO.getRacunByBrojRacun(racunZaBrisanje.getBrojRacuna());
        if (racunIzBaze == null) {
            System.out.println("Račun ne postoji u bazi podataka.");
            return;
        }

        // Pokušaj obrisati račun
        boolean uspjehBrisanja = racunDAO.deleteRacun(racunZaBrisanje.getBrojRacuna());

        if(uspjehBrisanja){
            racuni.remove(racunZaBrisanje); // Uklanjanje računa iz liste
        }
    }



    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    public List<Transakcija> getTransakcije() {
        return transakcije;
    }

    public void setTransakcije(List<Transakcija> transakcije) {
        this.transakcije = transakcije;
    }
}
