package dev;

import java.util.ArrayList;
import java.util.Scanner;

public class Launch {

    public static Scanner sc = new Scanner(System.in);

    public static void settings(Elections e) {
        System.out.println("Quels paramètres souhaitez-vous modifier ?");
        System.out.println("0. Retour \n1. Modifier les candidats \n2. Autoriser l'abstention (actuel : " + e.getSettings().isCanAbstention() + ") \n3. Autoriser le vote blanc (actuel : " + e.getSettings().isCanVoteWhite() +") \n4. Modifier le code secret \n5. Demander le code secret à chaque vote (actuel : " + e.getSettings().isAskSecretCode() + ") \n 6. Bornes aléatoires");
        String entry = sc.nextLine();
        try {
            int choice = Integer.parseInt(entry);
            switch (choice) {
                case 0:
                    mainMenu(e);
                    break;
                case 1:
                    editCandidatsListe(e);
                    break;
                case 2:
                    e.getSettings().setCanAbstention(!e.getSettings().isCanAbstention());
                    settings(e);
                    break;
                case 3:
                    e.getSettings().setCanVoteWhite(!e.getSettings().isCanVoteWhite());
                    settings(e);
                    break;
                case 4:
                    System.out.print("Entrez le nouveau code secret : ");
                    e.getSettings().setSecretCode(sc.nextLine());
                    System.out.println("Code changé!");
                    settings(e);
                    break;
                case 5:
                    e.getSettings().setAskSecretCode(!e.getSettings().isAskSecretCode());
                    settings(e);
                    break;
                case 6:
                    editRandomBornes(e);
            
                default:
                    break;
            }
        } catch(Exception ex) {
            System.out.println("L'entrée doit être un nombre");
            settings(e);
        }
    }

    public static void editRandomBornes(Elections e) {
        int min = -1;
        int max = -1;
        while(min < 0) {
            try {
                System.out.print("Minimum : ");
                String entry = sc.nextLine();
                int choice = Integer.parseInt(entry);
                if(choice >= 0 && choice <= 100) {min = choice;}
                else {throw new Exception();}
            } catch (Exception ex) {
                System.out.println("Entrée invalide. Doit être un nombre positif et inférieur ou égal à 100");
            }
        }
        System.out.println("Minimum sauvegardé");
        while(max < 0) {
            try {
                System.out.print("Maximum : ");
                String entry = sc.nextLine();
                int choice = Integer.parseInt(entry);
                if(choice >= min && choice <= 100) {max = choice;}
                else {throw new Exception();}
            } catch (Exception ex) {
                System.out.println("Entrée invalide. Doit être un nombre positif, supérieur ou égal au min et inférieur ou égal à 100");
            }
        }
        System.out.println("Maximum sauvegardé");
        e.getSettings().setMinRandomAbstention(min);
        e.getSettings().setMaxRandomAbstention(max);
        settings(e);
    }

    public static void editCandidatsListe(Elections e) {
        System.out.println("Liste des candidats : \n0. Retour");
        ArrayList<String> cands = e.getCandidats();
        for(int i = 1; i <= cands.size(); i++) {
            System.out.println(i + ". " + cands.get(i-1));
        }
        System.out.print("Choisissez le candidat à renommer : ");
        String entry = sc.nextLine();
        try {
            int choice = Integer.parseInt(entry);
            if(choice < 0 || choice > cands.size()) {throw new Exception();}
            editOneCandidat(e, choice - 1);
        } catch (Exception ex) {
            System.out.println("Entrée invalide. Doit être un nombre positif et inférieur à " + cands.size());
            editCandidatsListe(e);
        }
    }

    public static void editOneCandidat(Elections e, int index) {
        System.out.print("Nouveau nom : ");
        e.getCandidats().set(index, sc.nextLine());
        settings(e);
    }

    public static void showCandidats(Elections e) {
        ArrayList<String> cands = e.getCandidats();
        for(int i = 0; i < cands.size(); i++) {
            System.out.println(cands.get(i));
        }
    }

    public static void showScenario(Elections e, int i) {
        Scenario s = e.getScenarios().get(i);
        System.out.println("Scenario de base :");
        System.out.println("Inscrits : " + s.nbVoix());
        System.out.println("Exprimés : " + s.exprimes());
        System.out.println("Abstention : " + s.nbAbstention());
        System.out.println("Part abstention : " + s.partAbstention());
        System.out.println("Résultats : \n" + s.getResults().toString());
        mainMenu(e);
    }

    public static void putScenario(Elections e, Scenario s) {
        ArrayList<Scenario> scenar = e.getScenarios();
        scenar.add(null);
        for(int i = scenar.size()-2; i > 1; i--) {
            scenar.set(i+1, scenar.get(i));
        }
        scenar.set(1, s);
    }

    public static void mainMenu(Elections e) {
        int maxEntry = 4;
        System.out.println("Choisissez une action : \n0. Fermer le programme \n1. Paramètres \n2. Voir les candidats");
        if(e.getScenarios().size() <= 0) {System.out.println("3. Commencer un premier tour");}
        else {
            System.out.println("3. Commencer un nouveau tour (supprimera le tour précédent) \n4. Consulter le tour \n5. Générer un scénario aléatoire \n6. Consulter le dernier scénario aléatoire");
            maxEntry = 6;
        }
        String entry = sc.nextLine();
        clearConsole();
        try {
            int choice = Integer.parseInt(entry);
            if(choice > maxEntry || choice < 0) {
                System.out.println("L'entrée doit être comprise entre 0 et " + maxEntry);
                mainMenu(e);
                return;
            }
            switch (choice) {
                case 0:
                    System.exit(0);
                
                case 1:
                    settings(e);
                    break;
                
                case 2:
                    showCandidats(e);
                    mainMenu(e);
                    break;
                
                case 3:
                    Scenario s = e.startNewEntries();
                    if(e.getScenarios().isEmpty()) {e.getScenarios().add(s);}
                    else {e.getScenarios().set(0, s);}
                    mainMenu(e);
                    break;
                
                case 4:
                    showScenario(e, 0);
                    break;

                case 5:
                    Scenario sr = e.startNewRandomScenario();
                    putScenario(e, sr);
                    mainMenu(e);
                    break;
                
                case 6:
                    if(e.getScenarios().size() > 1) {showScenario(e, 1);}
                    mainMenu(e);
                    break;
                
                default:
                    break;
            }
        } catch(Exception ex) {
            System.out.println("L'entrée doit être un nombre.");
            mainMenu(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> candidats = new ArrayList<String>();
        System.out.println("Entrez les candidats (fin pour terminer les entrées, vous pourrez modifier les candidats plus tard) : ");
        String entry = sc.nextLine();
        while(!"fin".equals(entry.toLowerCase())) {
            candidats.add(entry);
            entry = sc.nextLine();
        }
        System.out.print("Entrez le code secret : ");
        String secretCode = sc.nextLine();
        Elections e = new Elections(candidats, secretCode);
        mainMenu(e);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }
}
