package com.project;

import com.project.DAO.RacunDAO;
import com.project.DAO.TransakcijaDAO;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        RacunDAO racunDAO = new RacunDAO();
        TransakcijaDAO transakcijaDAO = new TransakcijaDAO();
        List<Racun> racuni = racunDAO.getAllRacuni();
        List<Transakcija> transakcije = transakcijaDAO.getAllTransakcije();
        List<String> pregledTransakcija = Arrays.asList("Sve transakcije", "Samo uplatne transakcije", "Samo isplatne transakcije", "Samo transferi");
        Transakcija transakcija;
        Banka banka = new Banka(racuni, transakcije);



//        banka.ucitajRacuneIzDatoteke();
        List<String> usluge = Arrays.asList("Izrada računa", "Brisanje računa", "Pregled stanja računa", "Uplata", "Isplata", "Transfer", "Pregled transakcija", "Izlaz");
        System.out.println("Dobrodošli u Zagrebačku banku");
        System.out.println("---------------------------------------");

        int usluga = 0;
        while (true){
            try {
                System.out.println("Odaberite uslugu koju želite koristiti: ");
                for (int i = 0; i < usluge.size(); i++) {
                    System.out.println((i + 1) + ". " + usluge.get(i));
                }
                while (true) {
                    if (scanner.hasNextInt()) {
                        usluga = scanner.nextInt();
                        scanner.nextLine();
                        if (usluga >= 1 && usluga <= 8) {
                            break;
                        } else {
                            System.out.println("Neispravan odabir! Molimo unesite broj između 1 i 8.");
                            for (int i = 0; i < usluge.size(); i++) {
                                System.out.println((i + 1) + ". " + usluge.get(i));
                            }
                        }
                    } else {
                        System.out.println("Pogrešan unos! Molimo unesite broj.");
                        scanner.nextLine();
                        for (int i = 0; i < usluge.size(); i++) {
                            System.out.println((i + 1) + ". " + usluge.get(i));
                        }
                    }
                }


                if(usluga == 1){
                    System.out.println("Unesite ime i prezime: ");
                    String imeIprezime = scanner.nextLine();
                    banka.kreirajRacun(imeIprezime);
//                    banka.spremiRacuneUDatoteku();
                }
                else if(usluga == 2){
                    System.out.println("Unesite broj računa koji želite obrisati: ");
                    int brojRacuna = scanner.nextInt();
                    scanner.nextLine();  // Očisti ostatak linije
                    banka.obrisiRacun(brojRacuna);
                }
                else if (usluga == 3) {
                    System.out.println("Upišite broj računa: ");
                    int brojRacuna = unosBrojaRacuna();  // Koristite metodu za unos broja računa
                    Racun racun = banka.pronadiRacun(brojRacuna);
                    if(racun != null){
                        racun.prikaziStanje();
                    }
                    else {
                        System.out.println("Ne postoji račun s brojem: " + brojRacuna);
                    }

                }

                else if(usluga == 4){
                    System.out.println("Upišite broj računa na koji želite izvršiti uplatu: ");
                    int brojRacuna = unosBrojaRacuna();  // Koristite metodu za unos broja računa
                    System.out.println("Upišite iznos koji želite uplatiti:");
                    double iznos = unosIznosa();  // Koristite metodu za unos iznosa
                    Racun racun = banka.pronadiRacun(brojRacuna);
                    if(racun != null){
                        racun.uplati(brojRacuna, iznos);
                        transakcija = new Transakcija(brojRacuna, TipTransakcije.UPLATA, iznos);
                        transakcijaDAO.createTransakcija(transakcija);
                        transakcije.add(transakcija);
//                        banka.spremiRacuneUDatoteku();
                    }
                    else {
                        System.out.println("Ne postoji račun s brojem: " + brojRacuna);
                    }
                }

                else if(usluga == 5){
                    System.out.println("Upišite broj računa s kojeg želite izvršiti isplatu:");
                    int brojRacuna = unosBrojaRacuna();  // Koristite metodu za unos broja računa
                    System.out.println("Upišite iznos koji želite isplatiti: ");
                    double iznos = unosIznosa();  // Koristite metodu za unos iznosa
                    Racun racun = banka.pronadiRacun(brojRacuna);
                    if(racun != null){
                        racun.isplati(brojRacuna, iznos);
                        transakcija = new Transakcija(brojRacuna, TipTransakcije.ISPLATA, iznos);
                        transakcijaDAO.createTransakcija(transakcija);
                        transakcije.add(transakcija);
//                        banka.spremiRacuneUDatoteku();
                    }
                    else {
                        System.out.println("Ne postoji račun s brojem: " + brojRacuna);
                    }
                }

                else if(usluga == 6){
                    System.out.println("Upišite broj računa s kojeg želite izvršiti transfer:");
                    int brojRacunaPosiljatelja = unosBrojaRacuna();
                    System.out.println("Upišite broj računa na koji želite izvršiti transfer:");
                    int brojRacunaPrimatelja = unosBrojaRacuna();
                    System.out.println("Upišite iznos koji želite transferirati: ");
                    double iznos = unosIznosa();

                    if (brojRacunaPosiljatelja == brojRacunaPrimatelja) {
                        System.out.println("Greška: Broj računa pošiljatelja ne može biti isti kao broj računa primatelja!");
                        continue;
                    }

                    boolean uspjeh = racunDAO.transferirajNovac(brojRacunaPosiljatelja, brojRacunaPrimatelja, iznos);

                    if (uspjeh) {
                        System.out.println("Transfer uspješan.");
                        transakcija = new Transakcija(brojRacunaPosiljatelja, TipTransakcije.TRANSFER, iznos);
                        transakcijaDAO.createTransakcija(transakcija);
                        transakcije.add(transakcija);
                    } else {
                        System.out.println("Transfer nije uspio.");
                    }
                }

                else if(usluga == 7){
                    System.out.println("Odaberite način pregleda transakcija:");
                    for(int i=0; i<pregledTransakcija.size();i++){
                        System.out.println((i+1) + ". " + pregledTransakcija.get(i));
                    }
                    int odabir = 0;
                    while (true){
                        if(scanner.hasNextInt()){
                            odabir =scanner.nextInt();
                            scanner.nextLine();
                            if(odabir >= 1 && odabir <= 4){
                                break;
                            }else {
                                System.out.println("Pogrešan odabir! Molimo unesite broj između 1 i 4.");
                                for (int i = 0; i < pregledTransakcija.size(); i++) {
                                    System.out.println((i + 1) + ". " + pregledTransakcija.get(i));
                                }
                            }
                        }
                        else {
                            System.out.println("Pogrešan unos! Molimo unesite broj.");
                            scanner.nextLine();
                            for (int i = 0; i < pregledTransakcija.size(); i++) {
                                System.out.println((i + 1) + ". " + pregledTransakcija.get(i));
                            }
                        }
                    }
                    if(odabir == 1){
                        banka.pregledSvihTransakcija();
                    }
                    else if(odabir == 2){
                        banka.pregledUplatnihTransakcija();
                    }
                    else if(odabir == 3){
                        banka.pregledIsplatnihTransakcija();
                    }
                    else if(odabir == 4){
                        banka.pregledTransfera();
                    }

                }
                else {
                    System.out.println("Hvala Vam što ste posjetili Zagrebačku banku!");
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Metoda za unos broja računa
    public static int unosBrojaRacuna() {
        while (!scanner.hasNextInt()) {
            System.out.println("Uneseni podatak nije broj. Molimo unesite broj računa.");
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    // Metoda za unos iznosa
    public static double unosIznosa() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Uneseni podatak nije broj. Molimo unesite broj za iznos.");
            scanner.nextLine();
        }
        return scanner.nextDouble();
    }
}

