package br.ufrrj.data;

public class RecordService {
    private int ID;
    private int userID;
    private int serviceID;
    private String description;

    public RecordService(int _id, int _user_id, int _service_id, String _description){
        this.setID(_id);
        this.setUserID(_user_id);
        this.setServiceID(_service_id);
        this.setDescription(_description);
    }

    public int getID()                                   {return ID;}

    public void setID(int ID)                            {this.ID = ID;}

    public int getUserID()                               {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
