package br.ufrrj.data;

public class RecordPhone {
    private int ID;
    private String phone;
    private boolean whatsapp = false;

    public RecordPhone(int _id, String _phone, boolean _zap){
        this.setID(_id);
        this.setPhone(_phone);
        this.setWhatsapp(_zap);

    }

    public int getID()                                                      { return ID;}

    public void setID(int ID)                                               { this.ID = ID;}

    public String getPhone()                                                { return phone;}

    public void setPhone(String phone)                                      { this.phone = phone; }

    public boolean isWhatsapp()                                             { return whatsapp; }

    public void setWhatsapp(boolean whatsapp)                               { this.whatsapp = whatsapp; }
}
