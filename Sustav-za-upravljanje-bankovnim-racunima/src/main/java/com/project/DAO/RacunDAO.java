package com.project.DAO;

import com.project.DatabaseConnection;
import com.project.Racun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RacunDAO {

    public void createRacun(Racun racun) {
        String sql = "INSERT INTO racuni (broj_racuna,ime_vlasnika,stanje) VALUES (?,?,?)";

        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            int brojRacuna = Racun.generirajBrojRacuna();
            double stanje = 0.0;

            statement.setInt(1,brojRacuna);
            statement.setString(2,racun.getImeVlasnika());
            statement.setDouble(3,stanje);
            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Uspješno ste kreirali račun s brojem: " + racun.getBrojRacuna());
                racun.setBrojRacuna(brojRacuna);
                racun.setStanje(stanje);
            }else {
                System.out.println("Nije kreiran račun!");
            }

        }catch (SQLException ex){
            System.err.println("Greška prilikom kreiranja računa: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Racun> getAllRacuni(){
        List<Racun> racuni = new ArrayList<>();
        String sql = "SELECT * FROM racuni";

        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Racun racun = new Racun(
                        rs.getInt("broj_racuna"),
                        rs.getString("ime_vlasnika"),
                        rs.getDouble("stanje")
                );
                racuni.add(racun);
            }


        }catch (SQLException ex){
            System.err.println("Greška prilikom dohvaćanja svih računa: " + ex.getMessage());
            ex.printStackTrace();
        }
        return racuni;
    }

    public Racun getRacunByBrojRacun(int brojRacuna){
        String sql = "SELECT * FROM racuni WHERE broj_racuna = ?";
        Racun racun = null;

        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, brojRacuna );
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                racun = new Racun(
                        rs.getInt("broj_racuna"),
                        rs.getString("ime_vlasnika"),
                        rs.getDouble("stanje")
                );
            }

        }catch (SQLException ex){
            System.err.println("Greška prilikom dohvaćanja računa: " + ex.getMessage());
            ex.printStackTrace();
        }
        return racun;
    }

    public boolean deleteRacun(int brojRacuna){
        String sql = "DELETE FROM racuni WHERE broj_racuna = ?";
        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,brojRacuna);
            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Uspješno ste obrisali račun s brojem: " + brojRacuna);
                return true;
            }
            System.out.println("Račun s brojem " + brojRacuna + " ne postoji ili nije obrisan.");

        }catch (SQLException ex){
            System.err.println("Greška prilikom brisanja računa: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean transferirajNovac(int brojRacunaPosiljatelja, int brojRacunaPrimatelja, double iznos){
        try(Connection connection = DatabaseConnection.connect()) {
            Racun racunPosiljatelja = getRacunByBrojRacun(brojRacunaPosiljatelja);
            Racun racunPrimatelja = getRacunByBrojRacun(brojRacunaPrimatelja);

            if (racunPosiljatelja == null || racunPrimatelja == null) {
                System.out.println("Jedan od računa ne postoji.");
                return false;
            }

            if (racunPosiljatelja.getStanje() < iznos) {
                System.out.println("Nema dovoljno sredstava na računu pošiljatelja.");
                return false;
            }

            updateRacun(brojRacunaPosiljatelja, racunPosiljatelja.getStanje() - iznos);
            updateRacun(brojRacunaPrimatelja, racunPrimatelja.getStanje() + iznos);

            return true;


        }catch (SQLException ex){
            System.err.println("Greška prilikom transfera sredstava: " + ex.getMessage());
            ex.printStackTrace();

        }
        return false;
    }

    public void updateRacun(int brojRacuna, double novoStanje){
        String sql = "UPDATE racuni SET stanje = ? WHERE broj_racuna = ?";
        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, novoStanje);
            statement.setInt(2, brojRacuna);
            int affectedRows = statement.executeUpdate();

            if(affectedRows>0){
                System.out.println("Uspješno ste ažurirali račun s brojem: " + brojRacuna);
            }else {
                System.out.println("Račun s brojem " + brojRacuna + " ne postoji ili nije ažuriran.");
            }

        }catch (SQLException ex){
            System.err.println("Greška prilikom ažuriranja računa: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public double getStanjeRacuna(int brojRacuna){
        String sql = "SELECT stanje FROM racuni WHERE broj_racuna = ?";
        double stanje = 0.0;

        try(Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,brojRacuna);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                stanje = rs.getDouble("stanje");
            }else {
                System.out.println("Račun s brojem " + brojRacuna + " nije pronađen.");
            }

        }catch (SQLException ex){
            System.err.println("Greška prilikom dohvaćanja stanja računa: " + ex.getMessage());
            ex.printStackTrace();
        }
        return stanje;
    }
}
