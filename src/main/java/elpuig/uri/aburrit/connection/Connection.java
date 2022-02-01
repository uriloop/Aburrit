package elpuig.uri.aburrit.connection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.Bored;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class Connection {
    private String url_source;





    public Bored getRandomActivityToDo(String params) {
        String url_source2;
        Bored activity = null;
        URL url = null;
        url_source2 = connectionConfig.URL_DO_RANDOM;
        StringBuilder sb = new StringBuilder(url_source2).append(params);
        try {
            url = new URL(sb.toString());
            activity = new ObjectMapper().readValue(url, Bored.class);
        } catch (JsonProcessingException | MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity;


    }


}
