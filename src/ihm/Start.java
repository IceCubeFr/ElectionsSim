package ihm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import dev.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Start extends Application {
    public static final String PRIMARY_COLOR = "#002D6C";
    public static final String SECONDARY_COLOR = "#E3001B";
    public static final String PRIMARY_BG = "#FFFFFF";
    public static final String SECONDARY_BG = "#EEEEEE";
    public static final String BUTTON_STYLE_INACTIVE = "-fx-background-color: transparent; -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 4px; -fx-border-radius: 40px; -fx-background-radius: 40px;";
    public static final String BUTTON_STYLE_ACTIVE = "-fx-background-color: " + PRIMARY_COLOR + "; -fx-border-color: " + SECONDARY_COLOR + "; -fx-border-width: 4px; -fx-border-radius: 40px; -fx-text-fill: white; -fx-background-radius: 40px;";
    public static final Font taille24 = new Font(24);
    public static final Font taille20 = new Font(20);
    public static final Font taille18 = new Font(18);
    public static Button selected = null;
    public static Button[] buttonMenu = null;
    private static Label desc;
    private static VBox main;
    public static ArrayList<String> candidats = new ArrayList<String>();
    public static Elections elec ;
    private static String pastelGreen = "#A8E6A3";
    private static String pastelRed = "#F5A9A9";

    public static void update(int index) {
        main.getChildren().removeAll(main.getChildren());
        main.setAlignment(Pos.TOP_LEFT);
        switch (index) {
            case 1:
                accueil();
                break;
            case 2:
                scenarios();
                break;
            case 3:
                newTour();
                break;
            case 4:
                settings();
                break;
        }
    }

    public static void accueil() {
        Label title = new Label("Résultats du tour actuel : ");
        title.setStyle("-fx-text-fill: " + PRIMARY_COLOR + "; -fx-font-weight: bold;");
        title.setFont(taille24);
        Scenario sc = elec.getScenarios().isEmpty() ? new Scenario() : elec.getScenarios().getFirst();
        Label inscrits = new Label("Inscrits : " + sc.nbVoix());
        inscrits.setFont(taille18);
        Label exprimes = new Label("Exprimés : " + sc.exprimes());
        exprimes.setFont(taille18);
        Label blanc = new Label("Blancs : " + sc.nbBlanc());
        blanc.setFont(taille18);
        Label abs = new Label("Taux d'abstention : " + sc.partAbstention() + "%");
        abs.setFont(taille18);
        Label results = new Label("Résultats : ");
        results.setFont(taille18);
        results.setStyle("-fx-text-fill: "+ PRIMARY_COLOR + "; -fx-font-weight: bold;");
        main.setSpacing(25);
        main.getChildren().addAll(title, inscrits, exprimes, blanc, abs, results);
        Map<String, Integer> resultats = sc.getResults();
        for(Entry<String, Integer> res : resultats.entrySet()) {
            HBox cand = new HBox();
            Label name = new Label(res.getKey());
            name.setFont(taille18);
            Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            Label part = new Label(res.getValue() + " voix soit " + res.getValue() * 1000 / (sc.exprimes() - sc.nbBlanc()) / 10.0 + "%");
            part.setFont(taille18);
            ProgressBar pb = new ProgressBar(res.getValue() * 100 / (sc.exprimes() - sc.nbBlanc()) / 100.0);
            pb.setPrefWidth(300);
            pb.setStyle("-fx-accent: " + PRIMARY_COLOR + "; -fx-background-radius: 40px; -fx-border-radius: 40px;");
            cand.getChildren().addAll(name, space, part, pb);
            cand.setSpacing(10);
            main.getChildren().add(cand);
        }
        Button sauvegarder = new Button("Sauvegarder");
        sauvegarder.setMaxWidth(Double.MAX_VALUE);
        setButtonBlueStyle(sauvegarder);
        HBox.setHgrow(sauvegarder, Priority.ALWAYS);
        main.getChildren().add(sauvegarder);
        sauvegarder.setOnAction(e -> {
            LocalDateTime now = LocalDateTime.now();
            String name = now.toLocalDate().toString() + "_" + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
            Label notif = new Label("Sauvegardé sous le nom de " + name);
            DataExporter.exporter(elec, name);
            main.getChildren().add(notif);
        });
    }

    public static void updateListView(ListView<Label> lv) {
        lv.getItems().removeAll(lv.getItems());
        for(int i = 1; i < elec.getScenarios().size(); i++) {
            Label sc = new Label("Scénario " + i);
            sc.setUserData(elec.getScenarios().get(i));
            lv.getItems().add(sc);
        }
    }

    public static void comparer(Scenario s) {
        accueil();
        Label title = new Label("Résultats du scénario aléatoire : ");
        title.setStyle("-fx-text-fill: " + PRIMARY_COLOR + "; -fx-font-weight: bold;");
        title.setFont(taille24);
        Label inscrits = new Label("Inscrits : " + s.nbVoix());
        inscrits.setFont(taille18);
        Label exprimes = new Label("Exprimés : " + s.exprimes());
        exprimes.setFont(taille18);
        Label blanc = new Label("Blancs : " + s.nbBlanc());
        blanc.setFont(taille18);
        Label abs = new Label("Taux d'abstention : " + s.partAbstention() + "%");
        abs.setFont(taille18);
        Label results = new Label("Résultats : ");
        results.setFont(taille18);
        results.setStyle("-fx-font-weight: bold;");
        main.setSpacing(25);
        main.getChildren().addAll(title, inscrits, exprimes, blanc, abs, results);
        Map<String, Integer> resultats = s.getResults();
        for(Entry<String, Integer> res : resultats.entrySet()) {
            HBox cand = new HBox();
            Label name = new Label(res.getKey());
            name.setFont(taille18);
            Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            Label part = new Label(res.getValue() * 1000 / (s.exprimes() - s.nbBlanc()) / 10.0 + "%");
            part.setFont(taille18);
            ProgressBar pb = new ProgressBar(res.getValue() * 100 / (s.exprimes() - s.nbBlanc()) / 100.0);
            pb.setPrefWidth(300);
            pb.setStyle("-fx-accent: " + PRIMARY_COLOR + "; -fx-background-radius: 40px; -fx-border-radius: 40px;");
            cand.getChildren().addAll(name, space, part, pb);
            cand.setSpacing(10);
            main.getChildren().add(cand);
        }
    }

    public static void setButtonBlueStyle(Button b) {
        b.setStyle(
            "-fx-background-color: " + PRIMARY_COLOR + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-color: transparent;"
        );

        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color: rgba(0, 45, 108, 0.8), rgba(255, 255, 255, 0.2);" +
            "-fx-background-insets: 0, 0;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-color: transparent;"
        ));

        b.setOnMouseExited(e -> b.setStyle(
            "-fx-background-color: " + PRIMARY_COLOR + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-radius: 8px;" +
            "-fx-border-color: transparent;"
        ));
    }

    public static void scenarios() {
        Button generer = new Button("Générer un scénario aléatoire");
        generer.setMaxWidth(Double.MAX_VALUE);
        setButtonBlueStyle(generer);
        Label ou = new Label("ou");
        ou.setAlignment(Pos.CENTER);
        ou.setFont(taille24);
        Label desc = new Label("Choisir un scénario déjà généré");
        ListView<Label> lv = new ListView<Label>();
        lv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double clic
                Label selectedItem = lv.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    main.getChildren().removeAll(main.getChildren());
                    comparer((Scenario)selectedItem.getUserData());
                }
            }
        });
        Button select = new Button("Sélectionner le scénario");
        select.setMaxWidth(Double.MAX_VALUE);
        setButtonBlueStyle(select);
        select.setOnAction(e -> {
            Label selectedItem = lv.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                main.getChildren().removeAll(main.getChildren());
                comparer((Scenario)selectedItem.getUserData());
            }
        });

        updateListView(lv);
        generer.setOnAction(e -> {
            elec.getScenarios().add(elec.startNewRandomScenario());
            updateListView(lv);
        });
        main.getChildren().addAll(generer, ou, desc, lv, select);
    }

    public static void newTour() {
        Label text = new Label("Le lancement d'un nouveau tour écrasera le tour actuel (visible à l'accueil).\nPensez à sauvegarder à l'accueil avant de lancer le nouveau tour.\nSi vous êtes prêts : ");
        text.setAlignment(Pos.CENTER);
        text.setFont(taille18);
        Button lancer = new Button("Lancer le nouveau tour");
        lancer.setMaxWidth(Double.MAX_VALUE);
        setButtonBlueStyle(lancer);
        lancer.setOnAction(e -> {
            Stage stg = new Stage();
            NewTour nt = new NewTour(elec.getSettings().isCanAbstention(), elec.getSettings().isCanVoteWhite(), elec.getSettings().isAskSecretCode());
            nt.start(stg);
            stg.setOnHidden(ev -> {
                Scenario s = new Scenario();
                s.addAllVoix(nt.tour);
                if(elec.getScenarios().isEmpty()) {elec.getScenarios().add(s);}
                else {elec.getScenarios().set(0, s);}
            });
        });
        Region separator = new Region();
        separator.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(separator, Priority.ALWAYS);
        separator.setStyle("-fx-background-color: " + pastelRed + "; -fx-background-radius: 40px;");
        separator.setPadding(new Insets(1,0,1,0));
        Label injectText = new Label("Injectez des voix pour des candidats sans refaire de vote");
        injectText.setAlignment(Pos.CENTER);
        injectText.setFont(taille18);
        Button injectButton = new Button("Injecter des voix");
        injectButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(injectButton, Priority.ALWAYS);
        setButtonBlueStyle(injectButton);
        injectButton.setOnAction(e -> {
            Scenario sc = elec.getScenarios().isEmpty() ? new Scenario() : elec.getScenarios().getFirst();
            VirtualVotes vv = new VirtualVotes(elec.getCandidats(), sc);
            Stage stg = new Stage();
            vv.start(stg);
        });
        Spinner<Integer> absInjec = new Spinner<Integer>();
        setSpinnerStyle(absInjec);
        absInjec.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        Button validAbsInjec = new Button("Injecter des abstentions");
        Region separator2 = new Region();
        separator2.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(separator2, Priority.ALWAYS);
        separator2.setStyle("-fx-background-color: " + pastelRed + "; -fx-background-radius: 40px;");
        separator2.setPadding(new Insets(1,0,1,0));
        setButtonBlueStyle(validAbsInjec);
        validAbsInjec.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(validAbsInjec, Priority.ALWAYS);
        validAbsInjec.setOnAction(e -> {
            for(int i = 0; i < absInjec.getValue(); i++) {
                Voix v = new Voix("", true, false);
                elec.getScenarios().getFirst().addVoix(v);
                update(1);
            }
        });
        main.getChildren().addAll(text, lancer, separator, injectText, injectButton, separator2, absInjec, validAbsInjec);
        main.setAlignment(Pos.CENTER);
    }

    public static void updateSpinners(Spinner<Integer> spiMin, Spinner<Integer> spiMax, boolean updateMin) {
        int minVal = elec.getSettings().getMinRandomAbstention();
        spiMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, spiMax.getValue() - 1 < minVal && !updateMin ? spiMax.getValue() - 1 : minVal));
        int maxVal = elec.getSettings().getMaxRandomAbstention();
        spiMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, spiMin.getValue() + 1 > maxVal ? spiMin.getValue() + 1 : maxVal));
    }

    public static void setButtonSettings(Button b, boolean value) {
        b.setMaxWidth(200);
        b.setMinWidth(200);
        if(value) {
            b.setText("Oui");
            b.setStyle(
                "-fx-background-color: " + pastelGreen + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            );

            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: rgba(168, 230, 163, 1.0), rgba(255, 255, 255, 0.2);" +  // deux couches : fond + filtre
                "-fx-background-insets: 0, 0;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            ));

            b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: " + pastelGreen + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            ));
        } else {
            b.setText("Non");
            b.setStyle(
                "-fx-background-color: " + pastelRed + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            );
            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: rgba(245, 169, 169, 1.0), rgba(255, 255, 255, 0.2);" +  // fond + filtre blanc
                "-fx-background-insets: 0, 0;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            ));
            b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: " + pastelRed + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-color: transparent;"
            ));
        }
    }

    public static void setSpinnerStyle(Spinner<Integer> spinner) {
        spinner.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: black;" +
            "-fx-border-radius: 6px;" +
            "-fx-background-radius: 6px;" +
            "-fx-border-width: 1px;" +
            "-fx-padding: 3px;" +
            "-fx-font-size: 14px;"
        );

        spinner.getEditor().setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-width: 0;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 2px 6px;" +
            "-fx-text-fill: black;"
        );
    }

    public static void settings() {
        // Bornes aléatoires abstention

        Label nbAbs = new Label("Bornes aléatoires du nombre d'abstentionnistes : ");
        nbAbs.setFont(taille18);
        HBox bornesAbs = new HBox();
        Label min = new Label("Min : ");
        Spinner<Integer> spiMin = new Spinner<Integer>();
        setSpinnerStyle(spiMin);
        Label max = new Label("Max : ");
        Spinner<Integer> spiMax = new Spinner<Integer>();
        setSpinnerStyle(spiMax);
        spiMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, elec.getSettings().getMaxRandomAbstention()));
        updateSpinners(spiMin, spiMax, false);
        spiMin.valueProperty().addListener((obs, oldValue, newValue) -> {
            elec.getSettings().setMinRandomAbstention(newValue);
            updateSpinners(spiMin, spiMax, true);
            elec.getSettings().setMaxRandomAbstention(spiMax.getValue());
        });
        spiMax.valueProperty().addListener((obs, oldValue, newValue) -> {
            elec.getSettings().setMaxRandomAbstention(newValue);
            updateSpinners(spiMin, spiMax, false);
            elec.getSettings().setMinRandomAbstention(spiMin.getValue());
        });
        bornesAbs.setSpacing(10);
        bornesAbs.setAlignment(Pos.CENTER_LEFT);
        bornesAbs.getChildren().addAll(min, spiMin, max, spiMax);

        // Bouton possibilité abstention
        HBox abs = new HBox();
        Label absLabel = new Label("Les personnes peuvent s'abstenir");
        absLabel.setFont(taille18);
        Region absRegion = new Region();
        HBox.setHgrow(absRegion, Priority.ALWAYS);
        Button absButton = new Button();
        setButtonSettings(absButton, elec.getSettings().isCanAbstention());
        absButton.setOnAction(e -> {
            elec.getSettings().setCanAbstention(!elec.getSettings().isCanAbstention());
            setButtonSettings(absButton, elec.getSettings().isCanAbstention());
        });
        abs.getChildren().addAll(absLabel, absRegion, absButton);

        // Bouton possibilité vote blanc
        HBox blanc = new HBox();
        Label blancLabel = new Label("Les personnes peuvent voter blanc");
        blancLabel.setFont(taille18);
        Region blancRegion = new Region();
        HBox.setHgrow(blancRegion, Priority.ALWAYS);
        Button blancButton = new Button();
        setButtonSettings(blancButton, elec.getSettings().isCanVoteWhite());
        blancButton.setOnAction(e -> {
            elec.getSettings().setCanVoteWhite(!elec.getSettings().isCanVoteWhite());
            setButtonSettings(blancButton, elec.getSettings().isCanVoteWhite());
        });
        blanc.getChildren().addAll(blancLabel, blancRegion, blancButton);

        // Bouton demande code secret
        HBox secret = new HBox();
        Label secretLabel = new Label("Demander le code secret à chaque vote");
        secretLabel.setFont(taille18);
        Region secretRegion = new Region();
        HBox.setHgrow(secretRegion, Priority.ALWAYS);
        Button secretButton = new Button();
        setButtonSettings(secretButton, elec.getSettings().isAskSecretCode());
        secretButton.setOnAction(e -> {
            elec.getSettings().setAskSecretCode(!elec.getSettings().isAskSecretCode());
            setButtonSettings(secretButton, elec.getSettings().isAskSecretCode());
        });
        secret.getChildren().addAll(secretLabel, secretRegion, secretButton);

        // Liste des candidats
        VBox candidats = new VBox();
        candidats.setSpacing(20);
        Label candLabel = new Label("Modifier les candidats");
        candLabel.setFont(taille18);
        ListView<String> candidatList = new ListView<String>();
        candidatList.setMaxHeight(200);
        initCandidatList(candidatList);
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        Button edit = new Button("Modifier");
        setButtonBlueStyle(edit);
        HBox.setHgrow(edit, Priority.ALWAYS);
        edit.setOnAction(e -> {
            String selectedItem = candidatList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                askEditCandidat(selectedItem, candidatList);
            }
        });
        Button delete = new Button("Supprimer");
        setButtonBlueStyle(delete);
        delete.setOnAction(e -> {
            String selectedItem = candidatList.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                doubleCheckBeforeDeletion(selectedItem, candidatList);
            }
        });
        HBox.setHgrow(delete, Priority.ALWAYS);
        Button create = new Button("Ajouter");
        create.setOnAction(e -> {
            askNewCandidat(candidatList);
        });
        setButtonBlueStyle(create);
        HBox.setHgrow(create, Priority.ALWAYS);
        buttons.setMaxWidth(Double.MAX_VALUE);
        edit.setMaxWidth(Double.MAX_VALUE);
        delete.setMaxWidth(Double.MAX_VALUE);
        create.setMaxWidth(Double.MAX_VALUE);
        buttons.getChildren().addAll(edit, delete, create);
        candidats.getChildren().addAll(candLabel, candidatList, buttons);

        // Pied de page
        Label desc = new Label("Toutes les modifications sont sauvegardées automatiquement. \nLes modifications des candidats s'appliqueront au prochain tour et ne sont pas rétroactives.");
        desc.setStyle("-fx-font-style: italic;");

        // Ajout des objets
        main.getChildren().addAll(nbAbs, bornesAbs, abs, blanc, secret, candidats, desc);
    }

    public static void askNewCandidat(ListView<String> cands) {
        Stage stg = new Stage();
        VBox editing = new VBox();
        editing.setAlignment(Pos.CENTER);
        editing.setSpacing(20);
        Label texte = new Label("Quel est le nom du nouveau candidat ?");
        texte.setFont(taille20);
        TextField tf = new TextField();
        tf.setOnAction(e -> {
            elec.getCandidats().add(tf.getText());
            cands.getItems().removeAll(cands.getItems());
            initCandidatList(cands);
            stg.close();
        });
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button annuler = new Button("Annuler");
        HBox.setHgrow(annuler, Priority.ALWAYS);
        setButtonBlueStyle(annuler);
        annuler.setOnAction(e -> {
            stg.close();
        });
        Button valider = new Button("Valider");
        setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setOnAction(e -> {
            elec.getCandidats().add(tf.getText());
            cands.getItems().removeAll(cands.getItems());
            initCandidatList(cands);
            stg.close();
        });
        buttons.getChildren().addAll(annuler, valider);
        buttons.setSpacing(20);
        editing.getChildren().addAll(texte, tf, buttons);
        Scene sc = new Scene(editing, 800, 300);
        stg.setResizable(false);
        stg.setScene(sc);
        stg.setTitle("Nouveau candidat");
        stg.show();
    }

    public static void doubleCheckBeforeDeletion(String cand, ListView<String> cands) {
        Stage stg = new Stage();
        VBox suppressing = new VBox();
        suppressing.setAlignment(Pos.CENTER);
        suppressing.setSpacing(20);
        Label texte = new Label("Êtes vous sur de vouloir supprimer " + cand + " ?");
        texte.setFont(taille20);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button annuler = new Button("Annuler");
        HBox.setHgrow(annuler, Priority.ALWAYS);
        setButtonBlueStyle(annuler);
        annuler.setOnAction(e -> {
            stg.close();
        });
        Button valider = new Button("Valider");
        setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setOnAction(e -> {
            elec.getCandidats().remove(cand);
            cands.getItems().removeAll(cands.getItems());
            initCandidatList(cands);
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

    public static void initCandidatList(ListView<String> lv) {
        ArrayList<String> cands = elec.getCandidats();
        lv.getItems().addAll(cands);
        lv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedItem = lv.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    askEditCandidat(selectedItem, lv);
                }
            }
        });
    }

    public static void askEditCandidat(String oldName, ListView<String> cands) {
        Stage stg = new Stage();
        VBox editing = new VBox();
        editing.setAlignment(Pos.CENTER);
        editing.setSpacing(20);
        Label texte = new Label("Quel est le nouveau nom à donner ?");
        texte.setFont(taille20);
        TextField tf = new TextField();
        tf.setOnAction(e -> {
            elec.getCandidats().set(elec.getCandidats().indexOf(oldName), tf.getText());
            cands.getItems().removeAll(cands.getItems());
            initCandidatList(cands);
            stg.close();
        });
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button annuler = new Button("Annuler");
        HBox.setHgrow(annuler, Priority.ALWAYS);
        setButtonBlueStyle(annuler);
        annuler.setOnAction(e -> {
            stg.close();
        });
        Button valider = new Button("Valider");
        setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setOnAction(e -> {
            elec.getCandidats().set(elec.getCandidats().indexOf(oldName), tf.getText());
            cands.getItems().removeAll(cands.getItems());
            initCandidatList(cands);
            stg.close();
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

    public static void setButtonStyleActive(Button b) {
        b.setStyle(BUTTON_STYLE_ACTIVE);
        b.setOnMouseEntered(e -> {
            b.setStyle(b.getStyle() + "-fx-background-color: rgba(0, 45, 108, 0.7);");
        });
        b.setOnMouseExited(e -> {
            b.setStyle(BUTTON_STYLE_ACTIVE);
        });
    }

    public static void setButtonStyleInactive(Button b) {
        b.setStyle(BUTTON_STYLE_INACTIVE);
        b.setOnMouseEntered(e -> {
            b.setStyle(b.getStyle() + "-fx-background-color: rgba(200, 200, 200, 0.7);");
        });
        b.setOnMouseExited(e -> {
            b.setStyle(BUTTON_STYLE_INACTIVE);
        });
    }

    public static void setMenuButtonStyle(Button b) {
        b.setMinHeight(100);
        b.setMaxHeight(100);
        b.setMinWidth(200);
        b.setMaxWidth(200);
        b.setFont(new Font(20));
        b.setOnAction(e -> {
            selected = b;
            desc.setText(b.getText());
            setAllMenuButtonStyle((int)b.getUserData());
            update((int)b.getUserData());
        });
    }

    private static void setAllMenuButtonStyle(int index) {
        for(int i = 0; i < buttonMenu.length; i++) {
            if(i+1 == index) {
                setButtonStyleActive(buttonMenu[i]);
                setMenuButtonStyle(buttonMenu[i]);
            } else {
                setButtonStyleInactive(buttonMenu[i]);
                setMenuButtonStyle(buttonMenu[i]);
            }
        }
    }

    public static VBox initMenu() {
        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(10);
        Region topSpace = new Region();
        topSpace.setPadding(new Insets(10));
        Label title = new Label("ElectionsSim");
        title.setFont(taille24);
        title.setStyle("-fx-text-fill: " + PRIMARY_COLOR + "; -fx-font-weight: bold;");
        if(selected == null) {desc = new Label("Accueil");}
        else {desc = new Label(selected.getText());}
        desc.setFont(taille18);
        Image logo = new Image("file:./res/snu.png");
        ImageView logoviewer = new ImageView(logo);
        logoviewer.setFitWidth(200);
        logoviewer.setFitHeight(150);
        logoviewer.setPreserveRatio(true);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Button accueil = new Button("Accueil");
        accueil.setUserData(1);
        Button scenarios = new Button("Scenarios");
        scenarios.setUserData(2);
        Button newTour = new Button("Nouveau tour");
        newTour.setUserData(3);
        Button settings = new Button("Settings");
        settings.setUserData(4);
        buttonMenu = new Button[] {accueil, scenarios, newTour, settings};
        if(selected == null || selected.getUserData() == null || (int)selected.getUserData() == 1) {
            setAllMenuButtonStyle(1);
        } else {
            setAllMenuButtonStyle((int)selected.getUserData());
        }
        VBox buttons = new VBox();
        buttons.getChildren().addAll(buttonMenu);
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-border-color: " + SECONDARY_COLOR + "; -fx-border-width: 6px; -fx-border-radius: 40px;");
        buttons.setSpacing(10);

        menu.getChildren().addAll(topSpace, title, desc, logoviewer, spacer, buttons);
        menu.setMinWidth(250);
        menu.setMaxWidth(250);
        menu.setAlignment(Pos.CENTER);
        return menu;
    }

    @Override
    public void start(Stage stg) {
        HBox screen = new HBox();
        Scene sc = new Scene(screen);
        VBox right = new VBox();
        main = new VBox();
        VBox vb2 = initMenu();
        vb2.setStyle("-fx-background-color: " + SECONDARY_BG + "; -fx-background-radius: 40px;");
        VBox.setMargin(vb2, new Insets(2));
        VBox.setVgrow(vb2, Priority.ALWAYS);
        main.setStyle("-fx-background-color: " + SECONDARY_BG + "; -fx-background-radius: 40px;");
        main.setPadding(new Insets(20));
        ScrollPane sp = new ScrollPane(main);
        sp.setFitToWidth(true);
        sp.setPannable(true);
        sp.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        HBox.setHgrow(right, Priority.ALWAYS);
        right.getChildren().addAll(sp);
        screen.getChildren().addAll(vb2, right);
        screen.setSpacing(25);
        screen.setPadding(new Insets(25));
        update(1);
        stg.setScene(sc);
        stg.setTitle("ElectionsSim");
        stg.setMaximized(true);
        stg.setResizable(false);
        stg.show();
    }
    
    public static void main(String[] args) {
        elec = new Elections(candidats, "");
        Application.launch(args);
    }
}
