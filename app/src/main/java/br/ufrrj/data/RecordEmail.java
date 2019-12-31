package br.ufrrj.data;

public class RecordEmail {
    private int ID;
    private String email;

    public RecordEmail(int _id, String _email){
        this.setID(_id);
        this.setEmail(_email);
    }

    public int getID()                                                      { return ID; }

    public void setID(int ID)                                               { this.ID = ID; }

    public String getEmail()                                                { return email; }

    public void setEmail(String email)                                      { this.email = email;}
}
