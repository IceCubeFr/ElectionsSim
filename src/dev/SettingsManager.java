package dev;

import java.util.ArrayList;

/**
 * La classe {@code SettingsManager} permet de gérer les paramètres liés à une
 * simulation ou un système de vote.
 * 
 * Elle inclut des options comme la possibilité de voter blanc, de s'abstenir,
 * l'utilisation d'un code secret, ainsi que des bornes pour une abstention aléatoire.
 */
public class SettingsManager {
    private boolean canVoteWhite;
    private boolean canAbstention;
    private ArrayList<String> candidats;
    private String secretCode;
    private boolean askSecretCode;
    private int minRandomAbstention;
    private int maxRandomAbstention;

    /**
     * Crée une instance de {@code SettingsManager} avec une liste de candidats donnée.
     * 
     * @param candidats La liste des candidats disponibles pour le vote.
     */
    public SettingsManager(ArrayList<String> candidats) {
        this.candidats = candidats;
    }

    /**
     * Indique si le vote blanc est autorisé.
     * 
     * @return {@code true} si le vote blanc est autorisé, sinon {@code false}.
     */
    public boolean isCanVoteWhite() {
        return canVoteWhite;
    }

    /**
     * Indique si l'abstention est autorisée.
     * 
     * @return {@code true} si l'abstention est autorisée, sinon {@code false}.
     */
    public boolean isCanAbstention() {
        return canAbstention;
    }

    /**
     * Retourne la liste des candidats.
     * 
     * @return Une {@code ArrayList} contenant les noms des candidats.
     */
    public ArrayList<String> getCandidats() {
        return candidats;
    }

    /**
     * Retourne le code secret défini.
     * 
     * @return Le code secret sous forme de {@code String}.
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * Indique si un code secret est requis.
     * 
     * @return {@code true} si un code secret est requis, sinon {@code false}.
     */
    public boolean isAskSecretCode() {
        return askSecretCode;
    }

    /**
     * Retourne la valeur minimale pour l'abstention aléatoire.
     * 
     * @return La valeur minimale (en pourcentage ou en nombre selon le contexte).
     */
    public int getMinRandomAbstention() {
        return minRandomAbstention;
    }

    /**
     * Retourne la valeur maximale pour l'abstention aléatoire.
     * 
     * @return La valeur maximale (en pourcentage ou en nombre selon le contexte).
     */
    public int getMaxRandomAbstention() {
        return maxRandomAbstention;
    }

    /**
     * Définit si le vote blanc est autorisé.
     * 
     * @param canVoteWhite {@code true} pour autoriser le vote blanc, sinon {@code false}.
     */
    public void setCanVoteWhite(boolean canVoteWhite) {
        this.canVoteWhite = canVoteWhite;
    }

    /**
     * Définit si l'abstention est autorisée.
     * 
     * @param canAbstention {@code true} pour autoriser l'abstention, sinon {@code false}.
     */
    public void setCanAbstention(boolean canAbstention) {
        this.canAbstention = canAbstention;
    }

    /**
     * Définit le code secret à utiliser.
     * 
     * @param secretCode Le code secret à enregistrer.
     */
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    /**
     * Définit si l'utilisation du code secret est requise.
     * 
     * @param askSecretCode {@code true} pour exiger un code secret, sinon {@code false}.
     */
    public void setAskSecretCode(boolean askSecretCode) {
        this.askSecretCode = askSecretCode;
    }

    /**
     * Définit la valeur minimale pour l'abstention aléatoire.
     * 
     * @param minRandomAbstention La valeur minimale à définir.
     */
    public void setMinRandomAbstention(int minRandomAbstention) {
        this.minRandomAbstention = minRandomAbstention;
    }

    /**
     * Définit la valeur maximale pour l'abstention aléatoire.
     * 
     * @param maxRandomAbstention La valeur maximale à définir.
     */
    public void setMaxRandomAbstention(int maxRandomAbstention) {
        this.maxRandomAbstention = maxRandomAbstention;
    }
}
