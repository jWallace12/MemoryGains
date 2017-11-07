package a2.csci412.wwu.edu.memorygain;

/**
 * Created by danie on 11/7/2017.
 */

public class Recall {
    private int id;
    private String type;
    private String pass;

    public Recall(int id, String type, String pass) {
        this.id = id;
        this.type = type;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getPass() {
        return pass;
    }
}
