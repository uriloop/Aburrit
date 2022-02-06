package elpuig.uri.aburrit.model;

import java.security.Timestamp;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Classe model pels json que rebem de la api
 */
public class Bored {

    private  String activity,type,link;
    private  int participants,key;
    private  double price,accessibility;


    /**
     * @return retorna el link si n'hi ha o un string buit
     */
    public String getLink() {
        return link;
    }

    /**Setter per a construir l'element
     * @param activity parametre d'entrada del metode
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**Setter per a construir l'element
     * @param type parametre d'entrada del metode
     */
    public void setType(String type) {
        this.type = type;
    }

    /**Setter per a construir l'element
     * @param participants parametre d'entrada del metode
     */
    public void setParticipants(Number participants) {
        this.participants = (int)participants;
    }

    /**Setter per a construir l'element
     * @param price parametre d'entrada del metode
     */
    public void setPrice(Number price) {
        this.price = price.doubleValue();
    }

    /**Setter per a construir l'element
     * @param link parametre d'entrada del metode
     */
    public void setLink(String link) {
        if (link==null) this.link="";
        else this.link = link;
    }

    /**Setter per a construir l'element
     * @param key parametre d'entrada del metode
     */
    public void setKey(Number key) {
        this.key = (int)key;

    }

    /**Setter per a construir l'element
     * @param accessibility parametre d'entrada del metode
     */
    public void setAccessibility(Number accessibility) {
        this.accessibility =accessibility.doubleValue();
    }

    /**
     * @return retorna la descripcio de l'activitat
     */
    public String getActivity(){
        return activity;
    }

    /**
     * @return retorna el tipus d'activitat
     */
    public String getType() {
        return type;
    }

    /**
     * @return retorna el numero de participants de l'activitat
     */
    public int getParticipants() {
        return participants;
    }

    /**
     * @return retorna la clau/id de l'activitat
     */
    public int getKey() {
        return key;
    }

    /**
     * @return retorna el preu de l'activitat. Entre 0.0=gratis i 1.0 que representa el maxim de car
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return retorna la dificultat de l'activitat
     */
    public double getAccessibility() {
        return accessibility;
    }

    /**
     * @return retorna l'activitat en forma d'string
     */
    @Override
    public String toString() {
        return "Bored{\n" +
                "activitat=" + activity + '\n' +
                ", tipus=" + type + '\n' +
                ", participants=" + participants + '\n' +
                ", preu=" + price + '\n' +
                ", id=" + key + '\n' +
                ", Accessible=" + accessibility ;
    }
}
