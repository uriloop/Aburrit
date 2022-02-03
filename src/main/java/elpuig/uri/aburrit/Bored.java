package elpuig.uri.aburrit;

public class Bored {

    public String activity,type,link;
    public int participants,key;
    public double price,accessibility;
    /*public MenuItem quit;*/

    public String getLink() {
        return link;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParticipants(Number participants) {
        this.participants = (int)participants;
    }

    public void setPrice(Number price) {
        this.price = price.doubleValue();
    }

    public void setLink(String link) {
        if (link==null) this.link="";
        else this.link = link;
    }

    public void setKey(Number key) {

        this.key = (int)key;
    }

    public void setAccessibility(Number accessibility) {
        this.accessibility =accessibility.doubleValue();
    }

    public String getActivity(){
        return activity;
    }

    public String getType() {
        return type;
    }

    public int getParticipants() {
        return participants;
    }

    public int getKey() {
        return key;
    }

    public double getPrice() {
        return price;
    }

    public double getAccessibility() {
        return accessibility;
    }

    @Override
    public String toString() {
        return "DoSomething{\n" +
                "activitat=" + activity + '\n' +
                ", tipus=" + type + '\n' +
                ", participants=" + participants + '\n' +
                ", preu=" + price + '\n' +
                ", id=" + key + '\n' +
                ", Accessible=" + accessibility ;
    }
}
