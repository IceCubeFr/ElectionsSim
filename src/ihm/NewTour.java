package ihm;

import java.util.ArrayList;

import dev.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NewTour extends Application {
    public ArrayList<Voix> tour;
    private boolean canAbstention;
    private boolean canVoteWhite;
    private boolean askSecretCode;

    public NewTour(boolean canAbs, boolean canWhite, boolean askCode) {
        this.canAbstention = canAbs;
        this.canVoteWhite = canWhite;
        this.askSecretCode = askCode;
        this.tour = new ArrayList<Voix>();
    }

    public static void askSecretCode(Runnable suite) {
        Stage stg = new Stage();
        VBox container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        Label title = new Label("Entrez le code secret pour continuer");
        title.setFont(Start.taille20);
        PasswordField passwrd = new PasswordField();
        passwrd.setPromptText("Code secret");
        Label error = new Label();
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        Button annuler = new Button("Annuler");
        HBox.setHgrow(annuler, Priority.ALWAYS);
        Start.setButtonBlueStyle(annuler);
        annuler.setOnAction(e -> {
            stg.close();
        });
        Button valider = new Button("Valider");
        Start.setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setOnAction(e -> secretHandler(e, stg, suite, error, passwrd));
        passwrd.setOnAction(e -> secretHandler(e, stg, suite, error, passwrd));
        buttons.getChildren().addAll(annuler, valider);
        container.getChildren().addAll(title, passwrd, error, buttons);
        Scene sc = new Scene(container);
        stg.setScene(sc);
        stg.setTitle("Saisie du code secret");
        stg.show();
        sc.getWindow().setWidth(600);
        sc.getWindow().setHeight(300);
    }

    public static void secretHandler(ActionEvent e, Stage stg, Runnable suite, Label error, PasswordField pf) {
        if(Start.secretCode.equals(pf.getText())) {
            stg.close();
            suite.run();
        } else {
            error.setText("Le code ne correspond pas. Réessayez.");
            String originalStyle = "-fx-text-fill: black; -fx-background-color: transparent;";

            Timeline flash = new Timeline(
                new KeyFrame(Duration.ZERO, ev -> error.setStyle("-fx-text-fill: white; -fx-background-color: red;")),
                new KeyFrame(Duration.millis(150), ev -> error.setStyle(originalStyle))
            );
            flash.setCycleCount(1);
            flash.play();
        }
    }

    @Override
    public void start(Stage stg) {
        VBox main = new VBox();
        Label title = new Label("Nouveau tour");
        title.setFont(Start.taille24);
        Label desc = new Label("Choisissez parmis les candidats");
        ListView<String> cands = new ListView<String>();
        cands.getItems().addAll(Start.elec.getCandidats());
        Label desc2 = new Label("Ou parmis ces options si disponibles");
        HBox tools = new HBox();
        tools.setSpacing(20);
        tools.setPadding(new Insets(20));
        Button abs = new Button("Abtention");
        abs.setOnAction(e -> {
            cands.getSelectionModel().clearSelection();
            Voix v = new Voix("");
            v.makeAbstention();
            doubleCheck(v);
        });
        abs.setDisable(!canAbstention);
        abs.setMaxWidth(200);
        abs.setMinWidth(200);
        HBox.setHgrow(abs, Priority.ALWAYS);
        Start.setButtonBlueStyle(abs);
        Button blanc = new Button("Vote blanc");
        blanc.setOnAction(e -> {
            cands.getSelectionModel().clearSelection();
            Voix v = new Voix("");
            v.makeWhite();
            doubleCheck(v);
        });
        HBox.setHgrow(blanc, Priority.ALWAYS);
        blanc.setMaxWidth(200);
        blanc.setMinWidth(200);
        blanc.setDisable(!this.canVoteWhite);
        Start.setButtonBlueStyle(blanc);
        tools.getChildren().addAll(abs, blanc);
        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);
        HBox bottom = new HBox();
        HBox.setHgrow(bottom, Priority.ALWAYS);
        Button valider = new Button("Valider");
        valider.setOnAction(e -> {
            String selectedItem = cands.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                cands.getSelectionModel().clearSelection();
                Voix v = new Voix(selectedItem);
                doubleCheck(v);
            }
        });
        Start.setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setMaxWidth(200);
        valider.setMinWidth(200);
        Button end = new Button("Finir le tour");
        end.setOnAction(e -> {
            askSecretCode(() -> stg.close());
        });
        Start.setButtonBlueStyle(end);
        HBox.setHgrow(end, Priority.ALWAYS);
        end.setMaxWidth(200);
        end.setMinWidth(200);
        bottom.setSpacing(20);
        bottom.setPadding(new Insets(20));
        bottom.getChildren().addAll(valider, end);
        bottom.setStyle("-fx-background-color: " + Start.SECONDARY_BG + "; -fx-background-radius: 40px;");
        main.getChildren().addAll(title, desc, cands, desc2, tools, space, bottom);
        main.setPadding(new Insets(25));
        Scene sc = new Scene(main);
        stg.setTitle("Nouveau tour");
        stg.setScene(sc);
        stg.show();
        sc.getWindow().setHeight(720);
        sc.getWindow().setWidth(1080);
    }
    
    public void doubleCheck(Voix v) {
        Stage stg = new Stage();
        VBox suppressing = new VBox();
        suppressing.setAlignment(Pos.CENTER);
        suppressing.setSpacing(20);
        Label texte = new Label("Êtes vous sur de vouloir valider votre vote ?");
        texte.setFont(Start.taille20);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button annuler = new Button("Annuler");
        HBox.setHgrow(annuler, Priority.ALWAYS);
        Start.setButtonBlueStyle(annuler);
        annuler.setOnAction(e -> {
            stg.close();
        });
        Button valider = new Button("Valider");
        Start.setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setOnAction(e -> {
            if(this.askSecretCode) {
                askSecretCode(() -> tour.add(v));
            } else {
                tour.add(v);
            }
            stg.close();
        });
        buttons.getChildren().addAll(annuler, valider);
        buttons.setSpacing(20);
        suppressing.getChildren().addAll(texte, buttons);
        Scene sc = new Scene(suppressing, 800, 300);
        stg.setResizable(false);
        stg.setScene(sc);
        stg.setTitle("Validation");
        stg.show();
    }
}
