package dev;

import java.io.Serializable;

public class Voix implements Serializable {
    private String candidat;
    private boolean abstention;
    private boolean blanc;

    public Voix(String cand, boolean abst, boolean blanc) {
        this.candidat = cand;
        this.abstention = abst;
        this.blanc = blanc;
    }

    public Voix(String cand) {
        this(cand, false, false);
    }

    public boolean isAbstention() {return this.abstention;}
    public boolean isWhite() {return this.blanc;}
    public String getCandidat() {return this.candidat;}

    public void makeAbstention() {this.abstention = true;}
    public void removeAbstention() {this.abstention = false;}
    public void makeWhite() {this.blanc = true;}
    public void removeWhite() {this.blanc = false;}
    public void setCandidat(String s) {
        if(this.isWhite()) {this.removeWhite();}
        this.candidat = s;
    }
}