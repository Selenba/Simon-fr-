package com.simon.simon;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Game implements Initializable {

    @FXML
    Button red, yellow, blue, green, nextColor;
    @FXML Label score;

    LinkedList<Integer> sequence = new LinkedList<>();
    LinkedList<Integer> input = new LinkedList<>();

    private static Random rand= new Random();
    int scoreAmount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                newColor();
                red.getStyleClass().add("buttons");
                red.setId("red");
                yellow.getStyleClass().add("buttons");
                yellow.setId("yellow");
                blue.getStyleClass().add("buttons");
                blue.setId("blue");
                green.getStyleClass().add("buttons");
                green.setId("green");
                nextColor.getStyleClass().add("colorIndicator");
            }
        });
    }

    public static int RNG(){
        return rand.nextInt(4);
    }

    @FXML
    private void newColor() {
        int number = RNG();

        if (input.size() > 0) {
            input.removeIf(i -> i > -1);
        }

        switch (number) {
            case 0:
                nextColor.setStyle("-fx-background-color: #ff2f00");
                break;

            case 1:
                nextColor.setStyle("-fx-background-color: gold");
                break;

            case 2:
                nextColor.setStyle("-fx-background-color: #00ff00");
                break;

            case 3:
                nextColor.setStyle("-fx-background-color: #0057ff");
                break;
        }
        sequence.add(number);
    }

    private void rouletteEffect() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #ff2f00");
                red.setDisable(true);
                yellow.setDisable(true);
                green.setDisable(true);
                blue.setDisable(true);
            }
        }, 100);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: gold");
            }
        }, 200);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #00ff00");
            }
        }, 300);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #0057ff");
            }
        }, 400);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #ff2f00");
            }
        }, 500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: gold");
            }
        }, 600);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #00ff00");
            }
        }, 700);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextColor.setStyle("-fx-background-color: #0057ff");
            }
        }, 800);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                newColor();
                red.setDisable(false);
                yellow.setDisable(false);
                green.setDisable(false);
                blue.setDisable(false);
            }
        }, 1000);
    }

    @FXML
    private void clickedRed() throws IOException {
        buttonClicked("red");
    }

    @FXML
    private void clickedYellow() throws IOException {
        buttonClicked("yellow");
    }

    @FXML
    private void clickedBlue() throws IOException {
        buttonClicked("blue");
    }

    @FXML
    private void clickedGreen() throws IOException {
        buttonClicked("green");
    }

    @FXML
    private void buttonClicked(String color) throws IOException {
        switch (color) {
            case "red":
                validate(0);
                break;

            case "yellow":
                validate(1);
                break;

            case "green":
                validate(2);
                break;

            case "blue":
                validate(3);
                break;
        }
    }

    @FXML
    private void validate(int color) throws IOException {
        input.add(color);
        if (input.get(input.size() - 1) == sequence.get(input.size() - 1)) {
            if (input.size() == sequence.size()) {
                rouletteEffect();
                scoreAmount = scoreAmount + sequence.size();
                score.setText("Score : " + scoreAmount);
            }
        } else {
            lost();
        }
    }

    private void lost() throws IOException {
        saveScore();
        App.setRoot("Lost");
    }

    private void saveScore() throws IOException {
        String path = "C:/Users/"+ System.getProperty("user.name") +"/Documents/Simon";
        File f = new File(path + "/scores.txt");
        if(!f.exists()){
            Files.createDirectories(Paths.get(path));
            f.createNewFile();
            FileWriter writer = new FileWriter(f, true);
            writer.write(String.valueOf(scoreAmount));
            writer.close();
            return;
        }
        FileWriter writer = new FileWriter(f, true);
        writer.append("\n"+scoreAmount);
        writer.close();
    }
}
