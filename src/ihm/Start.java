package ihm;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
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
    public static String secretCode = "aaa";
    private static Elections elec = new Elections(candidats, secretCode);

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
        Scenario sc = elec.getScenarios().getFirst();
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
            Label part = new Label(res.getValue() * 1000 / (sc.exprimes() - sc.nbBlanc()) / 10.0 + "%");
            part.setFont(taille18);
            ProgressBar pb = new ProgressBar(res.getValue() * 100 / (sc.exprimes() - sc.nbBlanc()) / 100.0);
            pb.setPrefWidth(300);
            pb.setStyle("-fx-accent: " + PRIMARY_COLOR + "; -fx-background-radius: 40px; -fx-border-radius: 40px;");
            cand.getChildren().addAll(name, space, part, pb);
            cand.setSpacing(10);
            main.getChildren().add(cand);
        }
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
            /* Là yaura tous les trucs pour faire les tours */
        });
        main.getChildren().addAll(text, lancer);
        main.setAlignment(Pos.CENTER);
    }

    public static void updateSpinners(Spinner<Integer> spiMin, Spinner<Integer> spiMax, boolean updateMin) {
        int minVal = elec.getSettings().getMinRandomAbstention();
        spiMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, spiMax.getValue() - 1 < minVal && !updateMin ? spiMax.getValue() - 1 : minVal));
        int maxVal = elec.getSettings().getMaxRandomAbstention();
        spiMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, spiMin.getValue() + 1 > maxVal ? spiMin.getValue() + 1 : maxVal));
    }

    public static void settings() {
        Label nbAbs = new Label("Bornes aléatoires du nombre d'abstentionnistes : ");
        nbAbs.setFont(taille18);
        HBox bornesAbs = new HBox();
        Label min = new Label("Min : ");
        Spinner<Integer> spiMin = new Spinner<Integer>();
        Label max = new Label("Max : ");
        Spinner<Integer> spiMax = new Spinner<Integer>();
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
        main.getChildren().addAll(nbAbs, bornesAbs);
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

        menu.getChildren().addAll(topSpace, title, desc, spacer, buttons);
        menu.setMinWidth(250);
        menu.setMaxWidth(250);
        menu.setAlignment(Pos.CENTER);
        return menu;
    }

    @Override
    public void start(Stage stg) throws Exception {
        HBox screen = new HBox();
        Scene sc = new Scene(screen, 1080, 720);
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
        stg.setTitle("prout");
        stg.show();

    }
    
    public static void main(String[] args) {
        String[] cands = new String[]{"lala","pala","mala"};
        candidats.add("lala");
        candidats.add("pala");
        candidats.add("mala");
        elec = new Elections(candidats, secretCode);
        Scenario sc = new Scenario();
        Random r = new Random();
        for(int i = 0; i < 50; i++) {
            sc.addVoix(new Voix(cands[r.nextInt(0,3)]));
            int prob = r.nextInt(100);
            if(prob < 4) {
                sc.getVotes().get(i).makeAbstention();
            } else if(prob > 96) {
                sc.getVotes().get(i).makeWhite();
            }
        }
        elec.getScenarios().add(sc);
        Application.launch(args);
    }
}
