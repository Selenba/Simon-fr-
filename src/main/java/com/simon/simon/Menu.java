package com.simon.simon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Menu implements Initializable {

    @FXML Button start, score, exit;
    @FXML Label topScore, rulesText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        start.getStyleClass().add("lostButtons");
        score.getStyleClass().add("lostButtons");
        exit.getStyleClass().add("lostButtons");

        Platform.runLater(() -> {
            try {
                topScore();
                writeRules();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeRules() {
        rulesText.setText("Le jeu du Simon est un jeu de mémoire basé sur des couleurs. " +
                "A chaque tour, une nouvelle couleur est ajoutée à la séquence existante. " +
                "Pour valider le tour, il suffit de taper la séquence dans le bon ordre. " +
                "Par exemple : Rouge => Rouge, Bleu => Rouge, Bleu, Vert.\n" +
                "Bonne chance !");
    }

    @FXML
    private void newGame() throws IOException {
        App.setRoot("Game");
    }

    @FXML
    private void exit(){
        Platform.exit();
    }

    @FXML
    private void goToScores() throws IOException {
        App.setRoot("scores");
    }

    private void topScore() throws FileNotFoundException {
        String path = "C:/Users/"+ System.getProperty("user.name") +"/Documents/Simon/scores.txt";
        File f = new File(path);
        if(!f.exists()){
            return;
        }
        Scanner reader = new Scanner(f);
        LinkedList <Integer> scores = new LinkedList<>();
        while(reader.hasNextLine()){
            scores.add(Integer.valueOf(reader.nextLine()));
        }
        Collections.sort(scores);
        topScore.setText("Meilleur score : " + scores.get(scores.size()-1));
        reader.close();
    }
}
