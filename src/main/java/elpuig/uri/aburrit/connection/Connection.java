package elpuig.uri.aburrit.connection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.utils.JSONcontrol;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class Connection {
    private String url_source;
    private JSONcontrol jsoNcontrol;

    public Connection() {
        jsoNcontrol= new JSONcontrol();
    }

    public Bored getRandomActivityToDo(String params) {
        String url_source2;

        Bored activity = null;
        URL url = null;
        url_source2 = connectionConfig.URL_DO_RANDOM;
        StringBuilder sb = new StringBuilder(url_source2).append(params);
        try {
            url = new URL(sb.toString());
            String json=new ObjectMapper().writeValueAsString(activity = new ObjectMapper().readValue(url, Bored.class));
            jsoNcontrol.saveJSON(activity);
        } catch (JsonProcessingException | MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return activity;


    }


}
