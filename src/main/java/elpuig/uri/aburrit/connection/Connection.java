package elpuig.uri.aburrit.connection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.accesdata.JSONcontrol;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Classe encarregada de gestionar la conexió i consultes a la api
 */
public class Connection {
    private String url_source;
    private JSONcontrol jsoNcontrol;

    /**
     * Constructor de classe. Es declara la classe json que ens permetra treballar amb la resposta
     */
    public Connection() {
        jsoNcontrol= new JSONcontrol();
    }


    /**Metode que crea una conexió amb la api amb els paràmetres que s'han designat al controller i retorna un objecte Bored (una activitat).
     * També envia una copia de l'objecte Bored a la classe Json per guardar la informació per a les estadístiques
     * @param params parametre en format string que conté els paràmetres de busqueda ja units.
     * @return retorna una activitat de la classe Bored  que és el model principal
     */
    public Bored getRandomActivityToDo(String params) {
        String url_source2;

        try{
            if (params.charAt(0)=='&')params=params.substring(1);
        }catch(StringIndexOutOfBoundsException e){/*Si li enviem l'string params buit salta aquesta excepció*/}

        System.out.println(params);
        Bored activity = null;
        URL url = null;
        url_source2 = connectionConfig.URL_DO_RANDOM;
        StringBuilder sb = new StringBuilder(url_source2).append(params);
        try {
            url = new URL(sb.toString());
            System.out.println(url.toString());
            String json=new ObjectMapper().writeValueAsString(activity = new ObjectMapper().readValue(url, Bored.class));
            jsoNcontrol.saveJSON(activity);
        } catch (JsonProcessingException | MalformedInputException e) {
            /*e.printStackTrace();*/
        } catch (IOException e) {
            e.printStackTrace();
        }


        return activity;


    }


}
