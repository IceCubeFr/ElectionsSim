package dev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Elections {
    private ArrayList<Scenario> scenarios;
    private SettingsManager settings;
    private static Scanner sc = new Scanner(System.in);
    private static Random r = new Random();

    public Elections(ArrayList<String> candidats, String secretCode) {
        this.scenarios = new ArrayList<Scenario>();
        this.settings = new SettingsManager(candidats, secretCode);
    }

    public SettingsManager getSettings() {
        return settings;
    }

    public void addCandidat(String s) {
        this.settings.getCandidats().add(s);
    }
    public ArrayList<Scenario> getScenarios() {return this.scenarios;}

    public ArrayList<String> getCandidats() {return this.settings.getCandidats();}
    public void setSecretCode(String s) {this.settings.setSecretCode(s);}

    public Scenario startNewEntries() {
        Scenario s = new Scenario();
        Launch.showCandidats(this);
        String entree = sc.nextLine();
        while(!"fin".equals(entree.toLowerCase())) {
            Launch.showCandidats(this);
            try {
                int cand = Integer.parseInt(entree);
                if(cand < settings.getCandidats().size() && cand >= 0) {
                    s.addVoix(new Voix(settings.getCandidats().get(cand)));
                    System.out.println("Vote pris en compte.");
                } else {
                    System.out.println("Le nombre doit être compris entre 1 et " + this.settings.getCandidats().size());
                }
            } catch(Exception e) {
                System.out.println("Vous devez entrer un nombre.");
            }
            entree = sc.nextLine();
        }
        return s;
    }

    public Scenario startNewRandomScenario() {
        Scenario s = new Scenario();
        for(Voix vx : this.scenarios.get(0).getVotes()) {
            Voix v = new Voix(vx.getCandidat(), vx.isAbstention(), vx.isWhite());
            s.addVoix(v);
        }
        Set<Integer> pos = new HashSet<Integer>();
        int size;
        do {
            size = r.nextInt(this.getSettings().getMinRandomAbstention(), this.getSettings().getMaxRandomAbstention());
        } while(size > s.getVotes().size());
        while(pos.size() < size) {
            pos.add(r.nextInt(s.getVotes().size() - 1));
        }
        for(Integer i : pos) {
            s.getVotes().get(i).makeAbstention();
        }
        return s;
    }

    public void addScenario(Scenario s) {
        this.scenarios.add(s);
    }
}
