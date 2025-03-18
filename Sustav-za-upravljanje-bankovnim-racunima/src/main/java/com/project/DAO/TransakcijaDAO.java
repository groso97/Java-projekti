package com.project.DAO;

import com.project.DatabaseConnection;
import com.project.TipTransakcije;
import com.project.Transakcija;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransakcijaDAO {

    public void createTransakcija(Transakcija transakcija){
        String sql = "INSERT INTO transakcije (broj_racuna, tip_transakcije, iznos, datum) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,transakcija.getBrojRacuna());
            statement.setString(2, transakcija.getTipTransakcije().toString());
            statement.setDouble(3, transakcija.getIznosTransakcije());
            statement.setTimestamp(4, Timestamp.valueOf(transakcija.getDatum()));
            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Transakcija je uspješno dodana u bazu.");
            }
            else {
                System.out.println("Transakcija nije dodana u bazu.");
            }

        }catch (SQLException ex){
            System.err.println("Greška prilikom kreiranja transakcije: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Transakcija> getAllTransakcije() {
        List<Transakcija> transakcije = new ArrayList<>();
        String sql = "SELECT * FROM transakcije";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int brojRacunaDb = rs.getInt("broj_racuna");
                String tipString = rs.getString("tip_transakcije");
                TipTransakcije tip = TipTransakcije.valueOf(tipString);
                double iznos = rs.getDouble("iznos");
                Timestamp timestamp = rs.getTimestamp("datum");

                Transakcija transakcija = new Transakcija(brojRacunaDb, tip, iznos);
                transakcija.setDatum(timestamp.toLocalDateTime());
                transakcije.add(transakcija);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transakcije;
    }
}
