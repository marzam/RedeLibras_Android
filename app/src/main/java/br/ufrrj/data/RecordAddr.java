package br.ufrrj.data;

public class RecordAddr {
    private int ID;
    private String addr;
    private String addrMore;
    private String neighborhood;
    private int id_city;
    private int id_state;
    private String city_name;

    public RecordAddr(int _id,
                      String _addr,
                      String _addrMore,
                      String _neighborhood,
                      int _id_city,
                      int _id_state,
                      String _city_name){
        this.setID(_id);
        this.setAddr(_addr);
        this.setAddrMore(_addrMore);
        this.setNeighborhood(_neighborhood);
        this.setId_city(_id_city);
        this.setId_state(_id_state);
        this.setCity_name(_city_name);
    }


public int getID()                                      { return ID; }

    public void setID(int ID)                           { this.ID = ID; }

    public String getAddr()                             { return addr; }

    public void setAddr(String addr)                    { this.addr = addr; }

    public String getAddrMore()                         { return addrMore; }

    public void setAddrMore(String addrMore)            { this.addrMore = addrMore; }

    public String getNeighborhood()                     { return neighborhood; }

    public void setNeighborhood(String neighborhood)    { this.neighborhood = neighborhood; }

    public int getId_city()                             { return id_city; }

    public void setId_city(int id_city)                 { this.id_city = id_city; }

    public int getId_state()                            { return id_state; }

    public void setId_state(int id_state)               { this.id_state = id_state; }

    public String getCity_name()                        { return city_name; }

    public void setCity_name(String city_name)          { this.city_name = city_name; }
}
