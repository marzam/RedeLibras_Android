package br.ufrrj.io;
import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import br.ufrrj.R;

public class AppHttpComm {
    private static final AppHttpComm ourInstance = new AppHttpComm();
    private  Context mContext        = null;
    private  String  mError          = "";

    public static AppHttpComm getInstance()          { return ourInstance; }
    private AppHttpComm() {}
    public void setContext(Context c)                {mContext = c; }

    public String getError(){
        String ret = mError;
        mError = "";
        return ret;
    }
/*
    public int deleteFromServer(String inMsg){

        String sURL =   inMsg ; //state=1&app=" + mContext.getString(R.string.app_name);
        //String sURL =  mContext.getString(R.string.server) + "/data.zip"; //state=1&app=" + mContext.getString(R.string.app_name);
        int size = -1;
        boolean ret = false;
        try {

            HTTPCommSSL myHttpComm = new HTTPCommSSL();
            myHttpComm.setContex(mContext);

            ret = myHttpComm.execute(sURL, "", "false").get();

            if (ret){
                size  = myHttpComm.getBytesRead();
                if (myHttpComm.getStatus() == AsyncTask.Status.RUNNING){
                    while (myHttpComm.isCancelled() == false)
                        myHttpComm.cancel(true);
                }
                myHttpComm = null;
            }




        } catch (ExecutionException e) {
            mError = e.toString();
            e.printStackTrace();
        } catch (InterruptedException e) {
            mError = e.toString();
            e.printStackTrace();
        }




        return size;
    }

*/

    public int getDataFromServer(String inMsg, String filename, boolean append){

        String sURL =   inMsg ; //state=1&app=" + mContext.getString(R.string.app_name);
        //String sURL =  mContext.getString(R.string.server) + "/data.zip"; //state=1&app=" + mContext.getString(R.string.app_name);
        int size = -1;
        boolean ret = false;
        try {

            HTTPCommSSL myHttpComm = new HTTPCommSSL();
            myHttpComm.setContex(mContext);

            ret = myHttpComm.execute(sURL, filename, Boolean.toString(append)).get();

            if (ret){
                size  = myHttpComm.getBytesRead();
                if (myHttpComm.getStatus() == AsyncTask.Status.RUNNING){
                    while (myHttpComm.isCancelled() == false)
                        myHttpComm.cancel(true);
                }
                myHttpComm = null;
            }




        } catch (ExecutionException e) {
            mError = e.toString();
            e.printStackTrace();
        } catch (InterruptedException e) {
            mError = e.toString();
            e.printStackTrace();
        }




        return size;
    }

    public String userLogin(String user, String passwd){

        String msg  = "";
        String sURL =  mContext.getString(R.string.https_protocol) + "state=1&id_user=" +  user + "&id_passwd=" + passwd;
        boolean ret = false;

        try {

            HTTPCommSSL myHttpComm = new HTTPCommSSL();
            myHttpComm.setContex(mContext);
            ret  = myHttpComm.execute(sURL, "", "false").get();
            if (ret)
                msg = myHttpComm.getInMsg();

            if (myHttpComm.getStatus() == AsyncTask.Status.RUNNING){
                while (myHttpComm.isCancelled() == false)
                    myHttpComm.cancel(true);
            }
            myHttpComm = null;

        } catch (ExecutionException e) {
            mError = e.toString();
            e.printStackTrace();
        } catch (InterruptedException e) {
            mError = e.toString();
            e.printStackTrace();
        }



        return msg;
    }





    public int checkNewLoginAndInsert(String inMsg){

        String sURL =   inMsg ; //state=1&app=" + mContext.getString(R.string.app_name);
        //String sURL =  mContext.getString(R.string.server) + "/data.zip"; //state=1&app=" + mContext.getString(R.string.app_name);
        int size = -1;
        boolean ret = false;
        String myMsg = "";
        try {

            HTTPCommSSL myHttpComm = new HTTPCommSSL();
            myHttpComm.setContex(mContext);

            ret = myHttpComm.execute(sURL, new String(""), new String("false")).get();

            if (ret){
                size  = myHttpComm.getBytesRead();
                if (myHttpComm.getStatus() == AsyncTask.Status.RUNNING){
                    while (myHttpComm.isCancelled() == false)
                        myHttpComm.cancel(true);
                }
                myMsg  = new String(myHttpComm.getInMsg());
                myHttpComm = null;

            }




        } catch (ExecutionException e) {
            mError = e.toString();
            e.printStackTrace();
        } catch (InterruptedException e) {
            mError = e.toString();
            e.printStackTrace();
        }




        int id = Integer.valueOf(myMsg);
        return id;
    }

}
