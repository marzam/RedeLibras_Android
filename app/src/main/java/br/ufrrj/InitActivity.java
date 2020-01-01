package br.ufrrj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class InitActivity extends AppCompatActivity {
    private WebView mWebView = null;
    private Toolbar mToolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        mWebView = findViewById(R.id.mainWeb);


        mToolbar =  findViewById(R.id.toolbar);
        mToolbar.setSubtitle("Selecione um Estado");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.addJavascriptInterface(new JavaScriptReceiver(this), "JSReceiver");
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
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            int opt = Integer.parseInt(message);

            switch (opt){

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
                    mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_service" + Global.getSelectedServiceString() + "&id_city=" + Global.getSelectedCityString());
                    //mWebView.loadUrl(getApplicationContext().getString(R.string.https_redelibras) + "state=5&id_state=" + System.getInstance().getState_toString() + "&id_service=" + System.getInstance().getService_toString());
                    break;
                //case 4: mWebView.loadUrl(getApplicationContext().getString(R.string.https_server) + "state=6&id_user=" + System.getInstance().getWorker_toString());break;
                //case 2: mWebView.loadUrl("https://192.168.1.11/redelibras.php?state=1");break;
            }


            //https://192.168.1.11/redelibras.php?state=3&id_state=18
            //https://192.168.1.11/redelibras.php?state=5&id_state=18&id_service=5
            //https://192.168.1.11/redelibras.php?state=6&id_user=85

        }
    };//end-private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null; //new Intent(ServiceListActivity.this, DealerListActivity.class);
        int opt = item.getItemId();
        /*
        switch (item.getItemId()){
            case R.id.mnu_login:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.mnu_logout:
                break;
            case R.id.mnu_cadastro:
                break;
            case R.id.mnu_estado:
                intent = new Intent(this, StateListActivity.class);

                break;
            case R.id.mnu_servico:
                intent = new Intent(this, ServiceListActivity.class);

                break;
            case R.id.mnu_sobre:
                break;
        }

        if (intent != null){
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }

}//end-public class InitActivity extends AppCompatActivity {
