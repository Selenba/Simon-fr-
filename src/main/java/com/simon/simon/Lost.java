package com.simon.simon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Lost implements Initializable {

    @FXML ListView list;
    @FXML Label yourScore, scoreMoyen;
    @FXML Button replay, exit;
    private int score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        replay.getStyleClass().add("lostButtons");
        exit.getStyleClass().add("lostButtons");
        try {
            loadScores();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadScores() throws FileNotFoundException {
        LinkedList <Integer> scores = new LinkedList<>();
        String path = "C:/Users/"+ System.getProperty("user.name") +"/Documents/Simon/scores.txt";
        File f = new File(path);
        Scanner reader = new Scanner(f);
        while(reader.hasNextLine()){
            scores.add(Integer.valueOf(reader.nextLine()));
        }
        reader.close();
        score = scores.get(scores.size()-1);
        Platform.runLater(() -> {
            yourScore.setText("Score final : "+score);
            scoreMoyen.setText("Score moyen : "+moyenne(scores));
        });
        Collections.sort(scores);

        for(int i = scores.size()-1 ; i >= 0 ; i--){
            list.getItems().add(scores.get(i));
        }
    }

    private float moyenne(LinkedList<Integer> scores) {
        int total = 0;
        for(int i : scores){
            total += i;
        }
        return total/scores.size();
    }

    @FXML
    private void replay() throws IOException {
        App.setRoot("Game");
    }

    @FXML
    private void exit(){
        Platform.exit();
    }
}
