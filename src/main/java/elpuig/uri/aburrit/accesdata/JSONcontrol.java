package elpuig.uri.aburrit.accesdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.model.BoredsList;

import java.io.*;
import java.util.stream.Stream;

/**
 * Classe encarregada de gestionar el read write sobre l'arxiu json on enmagatzemem totes les respostes de la api
 */
public class JSONcontrol {

    private File file;
    private BoredsList boreds;

    /**
     * Constructor de la classe on carreguem el file dades.json
     */
    public JSONcontrol() {
        file = new File("dades.json");
    }

    /**Carrega els boreds del file dades.json, afegeix el nou bored passat per parametre,
     * i sobreescriu la nova llista de boreds a l'arxiu dades.json
     * @param bored rep un nou bored per afegir al json
     */
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

    /**Carrega una llista de boreds amb la informació del file dades.json
     * @throws IOException la excepció la tractem fora del metode
     */
    private void loadJSON() throws IOException {


        boreds = new ObjectMapper().readValue(file, BoredsList.class);

    }

    /**
     * @return retorna la llista de boreds que hi ha al file dades.json o si esta buit retorna una llista buida
     */
    public BoredsList load() {

        try {
            loadJSON();
        } catch (IOException e) {
            boreds = new BoredsList();
        }
        return boreds;
    }


    /**
     * Esborra les dades del file dades.json
     * @return un boolea en resposta a si ha funcionat
     */
    public boolean deleteDades() {

        return file.delete();

    }

    /**Retorna l'arxiu dades.json en forma d'string
     * @return retorna un string del json amb salts de linia per a que sigui més llegible
     */
    public String getJSONasString() {
        StringBuilder resultat = new StringBuilder();
        Stream<String> lines = null;
        try {
            BufferedReader br= new BufferedReader(new FileReader(file));
            lines=br.lines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String linia :
                lines.toList()) {
            resultat.append(linia.toString());
        }

        // tractem el resultat per a que sigui més visible
        String resultatFinal="";
        for (int i = 0; i < resultat.length(); i++) {
            if (resultat.charAt(i)=='{') resultatFinal+="{\n";
            else if (resultat.charAt(i)=='[') resultatFinal+= "[\n";
            else if (resultat.charAt(i)==',') resultatFinal+= ",\n";
            else resultatFinal+=resultat.charAt(i);
        }

        return resultatFinal;
    }
}
