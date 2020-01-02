package br.ufrrj;
//http://www.planalto.gov.br/ccivil_03/_ato2015-2018/2018/lei/L13709.htm
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {
    private WebView mWebView = null;
    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        mWebView = findViewById(R.id.mainWeb);


        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setSubtitle("Selecione um Estado");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new JavaScriptReceiver(this.getApplicationContext()), "JSReceiver");
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new SSLTolerentWebViewClient());


        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "1");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);





        /*
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "AQUI";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
         */
    }//end-protected void onCreate(Bundle savedInstanceState) {

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            int opt = Integer.parseInt(message);

            switch (opt) {

                case 1:
                    mToolbar.setSubtitle("Selecione um Estado");
                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=1");
                    break;
                case 2:
                    mToolbar.setSubtitle("Selecione uma cidade");
                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=2&id_state=" + Global.getSelectedStateString());
                    break;
                case 4:
                    mToolbar.setSubtitle("Selecione uma especialidade");
                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=4&id_city=" + Global.getSelectedCityString());
                    //mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_state=" + System.getInstance().getState_toString() + "&id_service=" + System.getInstance().getService_toString());
                    break;

                case 5:
                    mToolbar.setSubtitle("Selecione o/a prestador(a) de serviço");

                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_service=" + Global.getSelectedServiceString() + "&id_city=" + Global.getSelectedCityString());
                    //mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_state=" + System.getInstance().getState_toString() + "&id_service=" + System.getInstance().getService_toString());
                    break;

                case 6:
                    mToolbar.setSubtitle("Dados do prestador");

                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_prestador) + "id_user=" + Global.getSelectedWorkerString());
                    //mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_state=" + System.getInstance().getState_toString() + "&id_service=" + System.getInstance().getService_toString());
                    break;
                case 7:
                    openGoogleMaps();
                    break;
                case 8:

                    makePhoneCall();

                    break;
                case 9:
                    callInWhatsApp();
                    break;
                case 10:
                    sendEmail();
                    break;
                //case 4: mWebView.loadUrl(getApplicationContext().getString(R.string.https_server) + "state=6&id_user=" + System.getInstance().getWorker_toString());break;
                //case 2: mWebView.loadUrl("https://192.168.1.11/redelibras.php?state=1");break;
            }


            //https://192.168.1.11/redelibras.php?state=3&id_state=18
            //https://192.168.1.11/redelibras.php?state=5&id_state=18&id_service=5
            //https://192.168.1.11/redelibras.php?state=6&id_user=85

        }
    };//end-private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

    private void openGoogleMaps() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Global.getInstance().getWorkerAddr());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    private void makePhoneCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:982000483"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void callInWhatsApp(){
        Uri uri = Uri.parse("smsto:" + Global.getWorkerPhone());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", "olá");
        i.setPackage("com.whatsapp");
        startActivity(i);
    }

    private void sendEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Global.getWorkerEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT, "RedeLibras");
        i.putExtra(Intent.EXTRA_TEXT   , "Usuário do Redelibras");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Não existe cliente de e-mail instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if (mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }





}//end-public class InitActivity extends AppCompatActivity {
