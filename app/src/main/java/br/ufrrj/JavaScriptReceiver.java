package br.ufrrj;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class JavaScriptReceiver {
    private Context mContext;
    JavaScriptReceiver(Context c) { mContext = c; }

    @JavascriptInterface
    public void onClick_states_maps(int id){

        //Toast toast = Toast.makeText(mContext, "Ola Android" + Integer.toString(orderid), Toast.LENGTH_SHORT);
        //toast.show();
        System.getInstance().setState(id);
        sendMSG("2");


    }

    @JavascriptInterface
    public void onClick_services_in_state(int id){
        System.getInstance().setService(id);
        sendMSG("3");


    }

    @JavascriptInterface
    public void onClick_workers_by_service_and_state(int id){
        System.getInstance().setWorker(id);
        sendMSG("4");


    }

    @JavascriptInterface
    public void onClick_send_email(String email){
//        Toast toast = Toast.makeText(mContext, "Ola Android " +  Integer.toString(email), Toast.LENGTH_SHORT);
        Toast toast = Toast.makeText(mContext, "Enviar email " +  email, Toast.LENGTH_SHORT);
        toast.show();


    }

    @JavascriptInterface
    public void onClick_make_call(String msg){
//        Toast toast = Toast.makeText(mContext, "Ola Android " +  Integer.toString(email), Toast.LENGTH_SHORT);
        Toast toast = Toast.makeText(mContext, "Chamada telefonica " +  msg, Toast.LENGTH_SHORT);
        toast.show();


    }

    @JavascriptInterface
    public void onClick_send_by_whatsapp(String msg){
//        Toast toast = Toast.makeText(mContext, "Ola Android " +  Integer.toString(email), Toast.LENGTH_SHORT);
        Toast toast = Toast.makeText(mContext, "Whats app " +  msg, Toast.LENGTH_SHORT);
        toast.show();


    }

    @JavascriptInterface
    public void onClick_find_in_maps(String msg){
//        Toast toast = Toast.makeText(mContext, "Ola Android " +  Integer.toString(email), Toast.LENGTH_SHORT);
        Toast toast = Toast.makeText(mContext, "Whats app " +  msg, Toast.LENGTH_SHORT);
        toast.show();


    }

    private void sendMSG(String msg){
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}