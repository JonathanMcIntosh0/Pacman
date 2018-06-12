package main.viewsAndModels.menus;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.scoreBoard.Score;

import java.io.*;

public class EndMenu extends Stage {
    private Score[] scoreList = null;

    private final File file = new File("files/scoreList.ser");
    private final String fileName = "files/scoreList.ser";

    private final Button btnRestart = new Button("Recommence Jeu");

    public EndMenu(Score playerScore) {
        readFile();
        addScore(playerScore);

        this.setScene(new Scene(createRoot(playerScore)));
        this.initModality(Modality.APPLICATION_MODAL);
    }

    private Parent createRoot(Score playerScore) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Text title = new Text("JEU TERMINÉ");
        title.setFill(Color.DARKORANGE);
        title.setFont(Font.font(null, FontWeight.BOLD, 30));
        title.setUnderline(true);

        root.getChildren().addAll(
                new StackPane(title),
                createPlayerDisplay(playerScore),
                createScoreBoard(),
                new StackPane(btnRestart)
        );

        return root;
    }

    private Node createPlayerDisplay(Score playerScore) {
        HBox layout = new HBox(10);
        layout.getChildren().addAll(
                new Text(String.format("Nom du Joueur : %s", playerScore.getPlayerName())),
                new Text(String.format("Pointage final : %d", playerScore.getPoint()))
        );
        return layout;
    }

    private Node createScoreBoard() {
        VBox content = new VBox(1);
        HBox scores = new HBox(50);
        VBox names = new VBox(5);
        VBox points = new VBox(5);

        Font titleFont = Font.font(null, FontWeight.BOLD, 15);

        Text mainTitle = new Text("10 meilleurs scores :");
        mainTitle.setFont(titleFont);
        mainTitle.setFill(Color.DARKGREEN);
        mainTitle.setUnderline(true);
        mainTitle.setTextAlignment(TextAlignment.CENTER);

        Text nameTitle = new Text("Noms");
        nameTitle.setFont(titleFont);

        Text pointTitle = new Text("Pointage");
        pointTitle.setFont(titleFont);

        content.getChildren().addAll(mainTitle, scores);
        scores.getChildren().addAll(names, points);
        names.getChildren().add(nameTitle);
        points.getChildren().add(pointTitle);

        for (int i = 0; i < 10 && scoreList[i] != null; i++) {
            names.getChildren().add(new Text(scoreList[i].getPlayerName()));
            points.getChildren().add(new Text(String.format("% 10d", scoreList[i].getPoint())));
        }

        for (Score score: scoreList) {
            if (score == null) break;
        }

        ScrollPane spScoreBoard = new ScrollPane(content);
        spScoreBoard.setFitToWidth(true);
        spScoreBoard.setPrefHeight(250);

        return spScoreBoard;
    }

    public Button getBtnRestart() {
        return btnRestart;
    }

    //scoreBoard methods
    private void readFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                scoreList = new Score[10];
                return;
            }
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fis);
            scoreList = (Score[]) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void writeFile() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(scoreList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addScore(Score newScore) {
        Score memScore = newScore;
        Score tempScore;
        for (int i = 0; i < 10; i++) {
            if (scoreList[i] == null) {
                scoreList[i] = memScore;
                break;
            }
            if (scoreList[i].getPoint() < memScore.getPoint()) {
                tempScore = scoreList[i];
                scoreList[i] = memScore;
                memScore = tempScore;
            }
        }

        writeFile();
    }
}
