package br.ufrrj.data;

public class RecordServiceFromTable {
    private int mID;
    private String mServiceName;

    public RecordServiceFromTable(int _id, String _msn){
        this.setmID(_id);
        this.setmServiceName(_msn);

    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }
}
