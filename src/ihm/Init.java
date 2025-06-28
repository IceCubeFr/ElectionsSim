package ihm;

import java.util.ArrayList;

import dev.Elections;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Init extends Application {

    public void charger(VBox vb) {

    }

    public void initCands(ListView<HBox> lv, ArrayList<String> cands, Button validation) {
        lv.getItems().removeAll(lv.getItems());
        validation.setDisable(cands.size() < 2);
        for(String s : cands) {
            HBox hb = new HBox();
            Label content = new Label(s);
            Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            hb.setSpacing(20);
            hb.setUserData(s);
            hb.getChildren().addAll(content, space);
            hb.setAlignment(Pos.CENTER_LEFT);
            lv.getItems().add(hb);
        }
    }

    public void askEditCandidat(String oldName, ListView<HBox> lv, ArrayList<String> cands, Button validation) {
        Stage stg = new Stage();
        VBox editing = new VBox();
        editing.setAlignment(Pos.CENTER);
        editing.setSpacing(20);
        Label texte = new Label("Quel est le nouveau nom à donner ?");
        texte.setFont(Start.taille20);
        TextField tf = new TextField();
        tf.setOnAction(e -> {
            if(!"".equals(tf.getText())) {
                cands.set(cands.indexOf(oldName), tf.getText());
                initCands(lv, cands, validation);
                stg.close();
            }
        });
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
            if(!"".equals(tf.getText())) {
                cands.set(cands.indexOf(oldName), tf.getText());
                initCands(lv, cands, validation);
                stg.close();
            }
        });
        buttons.getChildren().addAll(annuler, valider);
        buttons.setSpacing(20);
        editing.getChildren().addAll(texte, tf, buttons);
        Scene sc = new Scene(editing, 800, 300);
        stg.setResizable(false);
        stg.setScene(sc);
        stg.setTitle(oldName);
        stg.show();
    }

    public void doubleCheckBeforeDeletion(String cand, ListView<HBox> cands, ArrayList<String> candsList, Button validation) {
        Stage stg = new Stage();
        VBox suppressing = new VBox();
        suppressing.setAlignment(Pos.CENTER);
        suppressing.setSpacing(20);
        Label texte = new Label("Êtes vous sur de vouloir supprimer " + cand + " ?");
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
            candsList.remove(cand);
            initCands(cands, candsList, validation);
            stg.close();
        });
        buttons.getChildren().addAll(annuler, valider);
        buttons.setSpacing(20);
        suppressing.getChildren().addAll(texte, buttons);
        Scene sc = new Scene(suppressing, 800, 300);
        stg.setResizable(false);
        stg.setScene(sc);
        stg.setTitle(cand);
        stg.show();
    }

    public void nouveau(VBox vb, Stage stg) {
        vb.getChildren().removeAll(vb.getChildren());

        Button valider = new Button("Lancer les élections");
        Start.setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setMaxWidth(Double.MAX_VALUE);
        valider.setDisable(true);
        // Le bouton valider sera tout en bas, mais pour permettre une activation et désactivation en fonction du nombre de candidats, c'est mieux de le mettre au début. Bisous

        Label title = new Label("Nouvelles élections");
        title.setFont(Start.taille24);

        ListView<HBox> cands = new ListView<HBox>();
        ArrayList<String> candsList = new ArrayList<String>();
        initCands(cands, candsList, valider);
        Button edit = new Button("Modifier");
        Start.setButtonBlueStyle(edit);
        edit.setOnAction(e -> {
            HBox selectedItem = cands.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                String cand = (String)(selectedItem.getUserData());
                askEditCandidat(cand, cands, candsList, valider);
            }
        });
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            HBox selectedItem = cands.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                String cand = (String)(selectedItem.getUserData());
                doubleCheckBeforeDeletion(cand, cands, candsList, valider);
            }
        });
        Start.setButtonBlueStyle(delete);
        cands.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(oldVal != null) {
                oldVal.getChildren().removeAll(edit, delete);
            }
            if(newVal != null) {
                newVal.getChildren().addAll(edit, delete);
            }
        });

        HBox entry = new HBox();
        TextField tf = new TextField();
        tf.setPromptText("Entrez le nom d'un candidat");
        Button add = new Button("Ajouter le candidat");
        Start.setButtonBlueStyle(add);
        tf.setOnAction(e -> {
            if(!"".equals(tf.getText())) {candsList.add(tf.getText());}
            tf.setText("");
            initCands(cands, candsList, valider);
        });
        add.setOnAction(e -> {
            if(!"".equals(tf.getText())) {candsList.add(tf.getText());}
            candsList.add(tf.getText());
            tf.setText("");
            initCands(cands, candsList, valider);
        });
        entry.getChildren().addAll(tf, add);
        entry.setSpacing(20);
        HBox.setHgrow(tf, Priority.ALWAYS);

        PasswordField secretCode = new PasswordField();
        secretCode.setPromptText("Entrez ici le code secret");

        valider.setOnAction(e -> {
            String codeSecret = secretCode.getText();
            Elections elec = new Elections(candsList, codeSecret);
            Start.elec = elec;
            Start st = new Start();
            st.start(new Stage());
            stg.close();
        });

        vb.getChildren().addAll(cands, entry, secretCode, valider);
    }

    public void start(Stage stg) {
        VBox main = new VBox();

        Label title = new Label("ElectionsSim");
        title.setFont(Start.taille24);

        Button charger = new Button("Charger");
        charger.setDisable(true);
        charger.setStyle(Start.BUTTON_STYLE_INACTIVE);
        charger.setOnMouseEntered(e -> {charger.setStyle(Start.BUTTON_STYLE_ACTIVE);});
        charger.setOnMouseExited(e -> {charger.setStyle(Start.BUTTON_STYLE_INACTIVE);});
        charger.setMinWidth(300);
        charger.setMaxWidth(300);
        charger.setMinHeight(100);
        charger.setMaxHeight(100);
        charger.setOnAction(e -> {

        });

        Button nouveau = new Button("Nouveau");
        nouveau.setStyle(Start.BUTTON_STYLE_INACTIVE);
        nouveau.setOnMouseEntered(e -> {nouveau.setStyle(Start.BUTTON_STYLE_ACTIVE);});
        nouveau.setOnMouseExited(e -> {nouveau.setStyle(Start.BUTTON_STYLE_INACTIVE);});
        nouveau.setMinWidth(300);
        nouveau.setMaxWidth(300);
        nouveau.setMinHeight(100);
        nouveau.setMaxHeight(100);
        nouveau.setOnAction(e -> {
            nouveau(main, stg);
        });

        main.setPadding(new Insets(30));
        main.setAlignment(Pos.CENTER);

        main.getChildren().addAll(title, charger, nouveau);
        main.setSpacing(20);

        Scene sc = new Scene(main, 700, 500);
        stg.setScene(sc);
        stg.setTitle("Bienvenue sur ElectionsSim");
        stg.show();
        sc.getWindow().setWidth(700);
        sc.getWindow().setHeight(500);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
