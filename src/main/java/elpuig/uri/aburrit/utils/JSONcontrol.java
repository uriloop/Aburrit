package elpuig.uri.aburrit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.model.BoredsList;

import java.io.File;
import java.io.IOException;

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
