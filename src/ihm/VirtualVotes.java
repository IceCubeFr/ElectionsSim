package ihm;

import java.util.ArrayList;
import java.util.Map;

import dev.Scenario;
import dev.Voix;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VirtualVotes extends Application{
    private ArrayList<String> candidats;
    private Map<String, Integer> voices;
    private Scenario sc;

    public VirtualVotes(ArrayList<String> candidats, Scenario sc) {
        this.candidats = candidats;
        this.voices = sc.getResults();
        this.sc = sc;
    }

    @Override
    public void start(Stage stg) {
        VBox main = new VBox();
        Label title = new Label("Injecter des voix");
        title.setFont(Start.taille20);
        title.setStyle("-fx-text-fill: " + Start.PRIMARY_BG + ";");
        ListView<HBox> cands = new ListView<HBox>();
        for(String s : this.candidats) {
            if(!this.voices.containsKey(s)) {
                this.voices.put(s, 0);
            }
            HBox candidat = new HBox();
            Label name = new Label(s);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Spinner<Integer> spiCand = new Spinner<Integer>();
            Start.setSpinnerStyle(spiCand);
            spiCand.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, this.voices.get(s)));
            candidat.getChildren().addAll(name, spacer, spiCand);
            cands.getItems().add(candidat);
        }
        Button valider = new Button("Valider");
        Start.setButtonBlueStyle(valider);
        HBox.setHgrow(valider, Priority.ALWAYS);
        valider.setMaxWidth(Double.MAX_VALUE);
        valider.setOnAction(e -> {
            for(HBox cand : cands.getItems()) {
                Label candName = (Label)cand.getChildren().get(0);
                String name = candName.getText();
                Spinner<Integer> spin = (Spinner<Integer>)cand.getChildren().get(2);
                int toAdd = spin.getValue() - this.voices.get(name);
                if(toAdd > 0) {
                    for(int i = 0; i < toAdd; i++) {
                        Voix v = new Voix(name);
                        sc.addVoix(v);
                    }
                } else if(toAdd < 0) {
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    for(int i = 0; i < this.sc.getVotes().size(); i++) {
                        if(sc.getVotes().get(i).getCandidat().equals(name)) {
                            pos.add(i);
                        }
                    }
                    for(int i = 0; i < toAdd * -1; i++) {
                        this.sc.getVotes().remove(pos.get(i).intValue());
                        for(int j = 0; j < pos.size(); j++) {
                            pos.set(j, pos.get(j) - 1);
                        }
                    }
                }
            }
            stg.close();
        });
        main.getChildren().addAll(title, cands, valider);
        main.setSpacing(30);
        main.setPadding(new Insets(30));
        Scene sc = new Scene(main);
        stg.setScene(sc);
        stg.setTitle("Injection de voix");
        stg.show();
        sc.getWindow().setWidth(800);
        sc.getWindow().setHeight(500);
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
