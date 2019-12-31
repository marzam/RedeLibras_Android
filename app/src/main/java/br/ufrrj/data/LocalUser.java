package br.ufrrj.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import br.ufrrj.R;
import br.ufrrj.System;
import br.ufrrj.io.AppHttpComm;

public class LocalUser {
    private static final LocalUser ourInstance = new LocalUser();

    public static LocalUser getInstance() {
        return ourInstance;
    }

    private LocalUser() {

    }

    private int    ID;
    private String name;
    private String login;
    private String passwd;
    private String type;
    private float  rank;
    private Bitmap photo;
    private Context mContext;

    private boolean mIsLogged = false;
    //private boolean isNewUser;

//    nome,rank,tipo,login,senha

    public ArrayList<RecordPhone>      mPhone;
    public ArrayList<RecordEmail>      mEmail;
    public ArrayList<RecordService>    mService;
    public ArrayList<RecordAddr>       mAddr;

    public boolean                   []mListOfService ;
    public String                      mServiceDescript;

    public void login()  { mIsLogged = true; }
    public void logout() { mIsLogged = false; }
    public boolean isLogged() { return mIsLogged; }

    public void setNewUser(int _id, String _userid, String _passwd){
        System.getInstance().loads();
        mListOfService = new boolean[System.getInstance().mListService.size()];
        for (int i = 0; i < mListOfService.length; i++) mListOfService[i] = false;

        this.setUserData(_id);
        this.setLogin(_userid);
        this.setPasswd(_passwd);

    }
    public void update(){

//--------------------------------------------------------------------------------------------------

        String  msg = name + ";" ;
        msg += Float.toString(rank) + ";" ;
        msg += type + ";";
        msg += login + ";";
        msg += passwd + ";\n";
        String fileName = Integer.toString(ID) + ".data.dat";
        System.getInstance().save(fileName, msg, false);
//------------------------------------------------------------------------------------------
        //  mAddr.add(new RecordAddr(_id, addr, addrMore,neighborhood,cityID, stateID, cityName));
        /*    public RecordAddr(int _id,
                      String _addr,
                      String _addrMore,
                      String _neighborhood,
                      int _id_city,
                      int _id_state,
                      String _city_name)*/

        msg = "";
        for (int i = 0; i < mAddr.size(); i++){
            RecordAddr addr = mAddr.get(i);
            msg += Integer.toString(addr.getID()) + ";";
            msg += new String(addr.getAddr()) + " ;";
            msg += new String(addr.getAddrMore()) + " ;";
            msg += new String(addr.getNeighborhood()) + " ;";
            msg += Integer.toString(addr.getId_city()) + " ;";
            msg += Integer.toString(addr.getId_state()) + " ;";
            msg += addr.getCity_name() + "; \n";
        }

        fileName = Integer.toString(ID) + ".addr.dat";
        System.getInstance().save(fileName, msg, false);

//------------------------------------------------------------------------------------------
        msg = "";
        for (int i = 0; i < mEmail.size(); i++){
            RecordEmail email = mEmail.get(i);
            msg += Integer.toString(email.getID()) + " ;";
            msg += new String(email.getEmail()) + " ; \n";
        }

        fileName = Integer.toString(ID) + ".email.dat";
        System.getInstance().save(fileName, msg, false);

//------------------------------------------------------------------------------------------
        msg = "";
        for (int i = 0; i < mPhone.size(); i++){
            RecordPhone phone = mPhone.get(i);
            msg += Integer.toString(phone.getID()) + " ;";
            msg += new String(phone.getPhone()) + " ;";
            msg += Boolean.toString(phone.isWhatsapp()) + " ;\n";
        }

        fileName = Integer.toString(ID) + ".phone.dat";
        System.getInstance().save(fileName, msg, false);


//------------------------------------------------------------------------------------------
        msg = "";
        for (int i = 0; i < mListOfService.length; i++){
            String type = Boolean.toString(mListOfService[i]);
            msg += type.substring(0,1).toUpperCase();
        }

        msg += ";" + mServiceDescript + ";\n";

        fileName = Integer.toString(ID) + ".service.dat";
        System.getInstance().save( fileName, msg, false);



    }

    //     LocalUser.getInstance().setUserData(System.getInstance().getWorker(), System.getInstance().getBaseDir(), false);
    //
    public void setUserData(int _id){
        this.setID(_id);
        this.setName("");
        this.setPasswd("");
        this.setPhoto(null);


        mPhone   =  new ArrayList<RecordPhone>();
        mEmail   =  new ArrayList<RecordEmail>();
        mService =  new ArrayList<RecordService>();
        mAddr    =  new ArrayList<RecordAddr>();
    }

    public void deleteUser(){
       int ret =  AppHttpComm.getInstance().getDataFromServer(mContext.getString(R.string.https_protocol ) + "state=8&id_user=" + Integer.toString(ID), "", false);
       if (ret == 1)
           Toast.makeText(mContext, "Usuário apagado!", Toast.LENGTH_SHORT).show();
       // AppHttpComm.getInstance().deleteFromServer(mContext.getString(R.string.https_delete ) + "state=5&id_user=" + Integer.toString(ID));

    }
    //public void setBaseDir(String b) { this.baseDir = b; }

    public void load(){


        loadUserData();
        loadUserAddr();
        loadUserPhone();
        loadUserEmail();
        loadUserService();
    }



    private void loadUserData(){
        String filename = getContext().getCacheDir() + "//" + getContext().getString(R.string.home) + "//" + Integer.toString(this.getID()) + ".data.dat";
        BufferedReader reader = null;

//    nome,rank,tipo,login,senha


        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = reader.readLine();
            if (line != null){
                StringTokenizer tokens = new StringTokenizer(line, ";");
                setName(new String(tokens.nextToken()));
                setRank(Float.valueOf(tokens.nextToken()));
                setType(new String(tokens.nextToken()));
                setLogin(new String(tokens.nextToken()));
                setPasswd(new String(tokens.nextToken()));


            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserAddr(){
        String filename = getContext().getCacheDir() + "//" + getContext().getString(R.string.home) + "//" + Integer.toString(this.getID()) + ".addr.dat";
        BufferedReader reader = null;



        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = "";
            line = reader.readLine();
            do{
                if (line != null){
                    StringTokenizer tokens = new StringTokenizer(line, ";");
                    int _id             = Integer.valueOf(tokens.nextToken());
                    String addr         = new String(tokens.nextToken());
                    String addrMore     = new String(tokens.nextToken());
                    String neighborhood = new String(tokens.nextToken());
                    int cityID          = Integer.valueOf(tokens.nextToken());
                    int stateID         = Integer.valueOf(tokens.nextToken());
                    String cityName     = new String(tokens.nextToken());
                    mAddr.add(new RecordAddr(_id, addr, addrMore,neighborhood,cityID, stateID, cityName));

                }
                line = reader.readLine();
            }while (line != null);


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserPhone(){
        String filename = getContext().getCacheDir() + "//" + getContext().getString(R.string.home) + "//" +Integer.toString(this.getID()) + ".phone.dat";
        BufferedReader reader = null;

//  ID,telefone


        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = "";
            line = reader.readLine();
            do{
                if (line != null){
                    StringTokenizer tokens = new StringTokenizer(line, ";");
                    int id = Integer.valueOf(tokens.nextToken());
                    String phone =  new String(tokens.nextToken());
                    boolean zap = true;
                    mPhone.add(new RecordPhone(id, phone, zap));
                }
                line = reader.readLine();
            }while (line != null);


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserEmail(){
        String filename = getContext().getCacheDir() + "//" + getContext().getString(R.string.home) + "//" + Integer.toString(this.getID()) + ".email.dat";
        BufferedReader reader = null;

//  ID,telefone


        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = "";
            line = reader.readLine();
            do{
                if (line != null){
                    StringTokenizer tokens = new StringTokenizer(line, ";");
                    int id = Integer.valueOf(tokens.nextToken());
                    String email =  new String (tokens.nextToken());
                    mEmail.add(new RecordEmail(id, email));
                }
                line = reader.readLine();
            }while (line != null);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserService(){
        String filename = getContext().getCacheDir() + "//" + getContext().getString(R.string.home) + "//" + Integer.toString(this.getID()) + ".service.dat";
        BufferedReader reader = null;

//  ID,telefone
/*
    private int ID;
    private int userID;
    private int serviceID;
    private String description;

 */




        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = "";
            line = reader.readLine();
            do{
                if (line != null){
                    StringTokenizer tokens = new StringTokenizer(line, ";");
                    int id              = Integer.valueOf(tokens.nextToken());
                    int userid          = Integer.valueOf(tokens.nextToken());
                    int serviceid       = Integer.valueOf(tokens.nextToken());
                    String desc         = new String (tokens.nextToken());

                    mListOfService[serviceid - 1] = true;
                    mService.add(new RecordService(id, userid, serviceid, desc));
                }
                line = reader.readLine();
            }while (line != null);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    // public boolean isNewUser() {
   //     return isNewUser;
   // }

  //  public void setNewUser(boolean newUser) {
  //      isNewUser = newUser;
   // }
}
