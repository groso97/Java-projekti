package com.example.kalkulator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.logging.Logger;


public class CalculatorController {

    private static final Logger logger = Logger.getLogger(CalculatorController.class.getName());

    @FXML
    private TextArea textAreaUnos;
    private double trenutniRezultat = 0;
    private String trenutnaOperacija = "";

    @FXML
    private Button buttonZnakPlus;
    @FXML
    private Button buttonZnakMinus;
    @FXML
    private Button buttonZnakMnozenja;
    @FXML
    private Button buttonZnakDijeljenja;
    @FXML
    private Button buttonZnakJednakosti;

    @FXML
    public void pritisakDijeljenje() {
        double novo;
        try {
            novo = Double.parseDouble(textAreaUnos.getText());
            if (trenutnaOperacija.isEmpty()) {
                trenutniRezultat = novo;
            } else {
                if (novo == 0) {
                    textAreaUnos.setText("Cannot divide by zero");
                    return;
                }
                if (trenutnaOperacija.equals("÷")) {
                    trenutniRezultat /= novo;
                } else if (trenutnaOperacija.equals("X")) {
                    trenutniRezultat *= novo;
                } else if (trenutnaOperacija.equals("+")) {
                    trenutniRezultat += novo;
                } else if (trenutnaOperacija.equals("-")) {
                    trenutniRezultat -= novo;
                }
            }
        } catch (NumberFormatException ex) {
            logger.severe("Greška: " + ex.getMessage());
        }

        trenutnaOperacija = "÷";
        textAreaUnos.clear();
    }

    @FXML
    public void pritisakPuta(){
        try {
            double novo = Double.parseDouble(textAreaUnos.getText());
            if (!trenutnaOperacija.isEmpty()) {
                if (trenutnaOperacija.equals("X")) {
                    trenutniRezultat *= novo;
                } else if (trenutnaOperacija.equals("+")) {
                    trenutniRezultat += novo;
                } else if (trenutnaOperacija.equals("-")) {
                    trenutniRezultat -= novo;
                }
            } else {
                trenutniRezultat = novo;
            }
        }
        catch (NumberFormatException e){
            logger.severe("Greška: " + e.getMessage());
        }

        trenutnaOperacija = "X";
        textAreaUnos.clear();
    }




    @FXML
    public void pritisakPlus() {
        try {
            double novo = Double.parseDouble(textAreaUnos.getText());
            if (!trenutnaOperacija.isEmpty()) {
                if (trenutnaOperacija.equals("+")) {
                    trenutniRezultat += novo;
                } else if (trenutnaOperacija.equals("-")) {
                    trenutniRezultat -= novo;
                }
            } else {
                trenutniRezultat = novo;
            }
        } catch (NumberFormatException e) {
            logger.severe("Greška: " + e.getMessage());
        }

        trenutnaOperacija = "+";
        textAreaUnos.clear();
    }

    @FXML
    public void pritisakMinus() {
        try {
            double novo = Double.parseDouble(textAreaUnos.getText());
            if (!trenutnaOperacija.isEmpty()) {
                if (trenutnaOperacija.equals("+")) {
                    trenutniRezultat += novo;
                } else if (trenutnaOperacija.equals("-")) {
                    trenutniRezultat -= novo;
                }
            } else {
                trenutniRezultat = novo;
            }
        } catch (NumberFormatException e) {
            logger.severe("Greška: " + e.getMessage());
        }

        trenutnaOperacija = "-";
        textAreaUnos.clear();
    }

    @FXML
    public void pritisakJednako() {
        double trenutniUnos;
        if (trenutnaOperacija.equals("+")) {
            try {
                trenutniUnos = Double.parseDouble(textAreaUnos.getText());
            } catch (NumberFormatException e) {
                trenutniUnos = 0;
            }
            trenutniRezultat += trenutniUnos;
        } else if (trenutnaOperacija.equals("-")) {
            try {
                trenutniUnos = Double.parseDouble(textAreaUnos.getText());
            } catch (NumberFormatException e) {
                trenutniUnos = 0;
            }
            trenutniRezultat -= trenutniUnos;
        } else if (trenutnaOperacija.equals("X")) {
            try {
                trenutniUnos = Double.parseDouble(textAreaUnos.getText());
            } catch (NumberFormatException e) {
                trenutniUnos = 0;
            }
            trenutniRezultat *= trenutniUnos;
        } else if (trenutnaOperacija.equals("÷")) {
            try {
                trenutniUnos = Double.parseDouble(textAreaUnos.getText());
                if (trenutniUnos == 0) {
                    textAreaUnos.setText("Cannot divide by zero");
                    buttonZnakPlus.setDisable(true);
                    buttonZnakMinus.setDisable(true);
                    buttonZnakMnozenja.setDisable(true);
                    buttonZnakDijeljenja.setDisable(true);
                    buttonZnakJednakosti.setDisable(true);
                    return;
                }
            } catch (NumberFormatException e) {
                trenutniUnos = 0;
            }
            trenutniRezultat /= trenutniUnos;
        }

        textAreaUnos.setText(String.valueOf(trenutniRezultat));

        // Omogući sve gumbe za operacije
        // Na primjer:
        buttonZnakPlus.setDisable(false);
        buttonZnakMinus.setDisable(false);
        buttonZnakMnozenja.setDisable(false);
        buttonZnakDijeljenja.setDisable(false);
        buttonZnakJednakosti.setDisable(false);

        trenutnaOperacija = "";
    }


    @FXML
    public void pritisakBackspace() {
        String unos = textAreaUnos.getText().trim();

        if (!unos.isEmpty()) {
            // Pronađemo posljednji znak koji nije razmak ili broj
            int i = unos.length() - 1;
            while (i >= 0 && (Character.isDigit(unos.charAt(i)) || unos.charAt(i) == ' ')) {
                i--;
            }

            // Izrežemo tekst prije tog znaka
            if (i >= 0) {
                unos = unos.substring(0, i + 1);
            } else {
                // Ako nema znaka koji nije razmak ili broj, izbrišemo sve
                unos = "";
            }

            textAreaUnos.setText(unos);
        }
    }

    @FXML
    public void pritisakClearEntry() {
        String unos = textAreaUnos.getText().trim();

        if (!unos.isEmpty()) {
            // Pronađemo posljednji znak koji nije razmak ili broj
            int i = unos.length() - 1;
            while (i >= 0 && (Character.isDigit(unos.charAt(i)) || unos.charAt(i) == ' ')) {
                i--;
            }

            // Izrežemo tekst prije tog znaka
            if (i >= 0) {
                unos = unos.substring(0, i + 1);
            } else {
                // Ako nema znaka koji nije razmak ili broj, izbrišemo sve
                unos = "";
            }

            textAreaUnos.setText(unos);
        }
    }




    @FXML
    public void pritisakClear(){
        textAreaUnos.clear();
    }





    @FXML
    public void pritisakNula(){
        textAreaUnos.appendText("0");

    }
    @FXML
    public void pritisakJedan(){
        textAreaUnos.appendText("1");
    }
    @FXML
    public void pritisakDva(){
        textAreaUnos.appendText("2");
    }
    @FXML
    public void pritisakTri(){
        textAreaUnos.appendText("3");
    }
    @FXML
    public void pritisakCetiri(){
        textAreaUnos.appendText("4");
    }
    @FXML
    public void pritisakPet(){
        textAreaUnos.appendText("5");
    }
    @FXML
    public void pritisakSest(){
        textAreaUnos.appendText("6");
    }
    @FXML
    public void pritisakSedam(){
        textAreaUnos.appendText("7");
    }

    @FXML
    public void pritisakOsam(){
        textAreaUnos.appendText("8");
    }
    @FXML
    public void pritisakDevet(){
        textAreaUnos.appendText("9");
    }
}