package main.viewsAndModels.menus;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Stage {
    private final Button btnStart = new Button("Jouer Pac-Man");

    private final TextField tfNameInput = new TextField();
    private final Text msg = new Text(
            "S'il te plait entrez un nom\n" +
            "avec moins de 10 caractères"
    );

    public StartMenu() {
        this.setScene(new Scene(
                createRoot(),
                Color.MIDNIGHTBLUE
        ));
    }

    private Parent createRoot() {
        VBox root = new VBox(10);
        root.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
        root.setPadding(new Insets(20));

        Text title = new javafx.scene.text.Text("P A C - M A N");
        title.setFill(Color.GOLDENROD);
        title.setFont(Font.font(null, FontWeight.BOLD, 50));
        title.setUnderline(true);

        HBox nameInputLayout = new HBox(7);

        Text txtName = new Text("Nom :");
        txtName.setFill(Color.YELLOW);

        msg.setFill(Color.GREEN);

        nameInputLayout.getChildren().addAll(
                txtName,
                tfNameInput,
                msg
        );

        root.getChildren().addAll(
                new StackPane(title),
                nameInputLayout,
                new StackPane(btnStart)
        );

        return root;
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public void setRedMsg() {
        msg.setFill(Color.RED);
    }

    public String getName() {
        return tfNameInput.getText();
    }
}
