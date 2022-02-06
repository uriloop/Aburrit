package elpuig.uri.aburrit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe model per al json dades.json que conté tots els json que anem reben com a resposta a les querys que fem a la api
 */
public class BoredsList {
    private List<Bored> boreds;


    /**
     * constructor de la classe que inicia la llista d'activitats(boreds)
     */
    public BoredsList() {
        boreds=new ArrayList<>();
    }

    /**Setter per construir la classe desde el json
     * @param boreds rep la llista de boreds
     */
    public void setBoreds(List<Bored> boreds) {
        this.boreds = boreds;
    }

    /**
     * @return retorna la llista de boreds
     */
    public List<Bored> getBoreds() {
        return boreds;
    }

    /**Rep una activitat i la afegeix a la llista
     * @param bored Rep una nova activitat
     */
    public void add(Bored bored) {
        boreds.add(bored);
    }


}
