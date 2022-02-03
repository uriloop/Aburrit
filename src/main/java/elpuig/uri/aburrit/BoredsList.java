package elpuig.uri.aburrit;

import java.util.ArrayList;
import java.util.List;

public class BoredsList {
    List<Bored> boreds;

    public BoredsList() {
        boreds=new ArrayList<>();
    }

    public void setBoreds(List<Bored> boreds) {
        this.boreds = boreds;
    }

    public List<Bored> getBoreds() {
        return boreds;
    }

    public void add(Bored bored) {
        boreds.add(bored);
    }
}
