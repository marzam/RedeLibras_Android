package br.ufrrj;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        Global.getInstance().setSelectedState(id);

        sendMSG("2");


    }

    @JavascriptInterface
    public void onClick_select_city(int id){

        //Toast toast = Toast.makeText(mContext, "Ola Android" + Integer.toString(orderid), Toast.LENGTH_SHORT);
        //toast.show();
        Global.getInstance().setSelectedCity(id);
        //SELECT * FROM tbCidades WHERE ID='2780';ping
        sendMSG("4");


    }

    @JavascriptInterface
    public void onClick_select_service_in_city(int id){

        //Toast toast = Toast.makeText(mContext, "Ola Android" + Integer.toString(orderid), Toast.LENGTH_SHORT);
        //toast.show();
        Global.getInstance().setSelectedService(id);

        sendMSG("5");


    }

    @JavascriptInterface
    public void onClick_select_worker(int id){

        //Toast toast = Toast.makeText(mContext, "Ola Android" + Integer.toString(orderid), Toast.LENGTH_SHORT);
        //toast.show();
        Global.getInstance().setSelectedWorker(id);

        sendMSG("6");


    }

    @JavascriptInterface
    public void onClick_callMaps(String txt){
        Global.getInstance().setWokerAddr(txt);
        sendMSG("7");

/*
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + txt);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mContext.startActivity(mapIntent);
*/

    }

    @JavascriptInterface
    public void onClick_callPhone(String txt, int opt){
        Global.getInstance().setWokerPhone(txt);
        if (opt == 0){
            sendMSG("8");
        }else{
            sendMSG("9");
        }
    }

    @JavascriptInterface
    public void onClick_sendEmail(String txt){
        Global.getInstance().setWokerEmail(txt);
         sendMSG("10");

    }

    @JavascriptInterface
    public void onClick_callWhatsApp(String txt){

        Toast toast = Toast.makeText(mContext, "WhatsApp" + txt, Toast.LENGTH_SHORT);
        toast.show();



    }
//-------------------------------

    @JavascriptInterface
    public void onClick_services_in_state(int id){

        sendMSG("3");


    }

    @JavascriptInterface
    public void onClick_workers_by_service_and_state(int id){

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