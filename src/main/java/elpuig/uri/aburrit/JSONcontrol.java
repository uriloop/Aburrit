package elpuig.uri.aburrit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONcontrol {

    private File file;
    private BoredsList boreds;

    public JSONcontrol() {
        file = new File("dades.json");
    }

    public void saveJSON(Bored bored) {

        // llegir el json i guardar-lo a una llista de bored
        try {
            loadJSON();


        } catch (IOException e) {
            boreds = new BoredsList();
        }
        // afegir l'ultim bored
        boreds.add(bored);
        // guardar el json
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(file, boreds);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadJSON() throws IOException {


        boreds = new ObjectMapper().readValue(file, BoredsList.class);

    }

    public BoredsList load() {

        try {
            loadJSON();
        } catch (IOException e) {
            boreds = new BoredsList();
        }
        return boreds;
    }


    public boolean deleteDades() {

        return file.delete();

    }
}
