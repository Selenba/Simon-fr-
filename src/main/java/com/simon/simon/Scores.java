package com.simon.simon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Scores implements Initializable {

    @FXML Button play, menu, reset;
    @FXML ListView list;
    @FXML Label scoreMoyen;

    String path = "C:/Users/"+ System.getProperty("user.name") +"/Documents/Simon/scores.txt";

    LinkedList<Integer> scores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        play.getStyleClass().add("lostButtons");
        menu.getStyleClass().add("lostButtons");
        reset.getStyleClass().add("lostButtons");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    loadScores();
                    scoreMoyen();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void menu() throws IOException {
        App.setRoot("Menu");
    }

    @FXML
    private void play() throws IOException {
        App.setRoot("Game");
    }

    private void loadScores() throws FileNotFoundException {
        File f = new File(path);
        if(!f.exists()){
            scores = new LinkedList<>();
            scores.add(0);
            return;
        }
        Scanner reader = new Scanner(f);
        scores = new LinkedList<>();
        while(reader.hasNextLine()){
            scores.add(Integer.valueOf(reader.nextLine()));
        }
        Collections.sort(scores);
        for(int i = scores.size()-1 ; i >= 0 ; i--){
            list.getItems().add(scores.get(i));
        }
        reader.close();
    }

    @FXML
    private void reset() throws IOException {
        int topScore;
        File f = new File(path);
        if(!f.exists()){
            return;
        }
        topScore = scores.get(scores.size()-1);
        FileWriter writer = new FileWriter(f);
        writer.write(String.valueOf(topScore));
        writer.close();
        App.setRoot("Scores");
    }

    private void scoreMoyen(){
        scoreMoyen.setText("Score moyen : " + moyenne());
    }

    private float moyenne(){
        int total = 0;
        for(int i : scores){
            total += i;
        }
        return total/scores.size();
    }
}
