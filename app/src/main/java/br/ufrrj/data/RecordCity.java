package br.ufrrj.data;

public class RecordCity {
    private int mID;
    private int mID_state;
    private String mCity;

    public int getID()                     { return mID; }

    public int getID_state()               { return mID_state; }

    public String getCity()                { return mCity; }



    public RecordCity(){
        this.mID = 0;
        this.mID_state = 0;
        this.mCity = "";

    }

    public RecordCity(int _id, int _id_state, String _city){
        this.mID = _id;
        this.mID_state = _id_state;
        this.mCity = _city;

    }
}
