package dev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Stocke les différents votes pour l'élection actuelle et calcule les stats (Participation, abstention...)
 */
public class Scenario {
    private ArrayList<Voix> votes;

    /**
     * Constructeur du scénatio créant la liste des votes
     */
    public Scenario() {
        this.votes = new ArrayList<Voix>();
    }

    /**
     * Ajouter une voix dans la liste des votes
     * @param v vote à ajouter
     */
    public void addVoix(Voix v) {this.votes.add(v);}

    /**
     * Retire une voix de la liste des votes à partir de l'objet
     * @param v vote à retirer
     */
    public void removeVoix(Voix v) {this.votes.remove(v);}

    /**
     * Retire une voix de la liste des votes à partir de sa position
     * @param i position de la voix à retirer
     */
    public void removeVoix(int i) {this.votes.remove(i);}

    public ArrayList<Voix> getVotes() {return this.votes;}

    /**
     * Récupère le nombre de voix du scénario (abstention et votes blancs compris)
     * @return nbVoix
     */
    public int nbVoix() {return this.votes.size();}

    /**
     * Récupère le nombre d'abstentions dans les votes
     * @return nbAbstention
     */
    public int nbAbstention() {
        int abs = 0;
        for(Voix v : this.votes) {
            if(v.isAbstention()) {
                abs++;
            }
        }
        return abs;
    }

    /**
     * Renvoie le nombre de voix exprimés (nbVoix - nbAbstention)
     * @return votes exprimés
     */
    public int exprimes() {return this.nbVoix() - this.nbAbstention();}

    /**
     * Renvoie la part d'abstention parmis les votes
     * @return partAbstention
     */
    public double partAbstention() {
        try {
            return Math.round(this.nbAbstention() * 100 / this.nbVoix() * 100) / 100.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Renvoie les résultats des élections par candidat
     * @return results
     */
    public Map<String, Integer> getResults() {
        Map<String, Integer> results = new HashMap<String, Integer>();
        for(Voix v : this.votes) {
            if(!v.isAbstention() && !v.isWhite()) {
                int toAdd = 0;
                if(results.keySet().contains(v.getCandidat())) {
                    toAdd = results.get(v.getCandidat()) + 1;
                } else {
                    toAdd = 1;
                }
                results.put(v.getCandidat(), toAdd);
            }
        }
        return results;
    }

    /**
     * Renvoie le candidat avec le plus de voix
     * @param results résultats générés depuis la méthode getResults()
     * @return gagnant
     */
    public Entry<String, Integer> getWinner(Map<String, Integer> results) {
        Entry<String, Integer> max = null;
        for(Entry<String, Integer> e : results.entrySet()) {
            if(max == null || max.getValue() < e.getValue()) {max = e;}
        }
        return max;
    }
}
