package br.ufrrj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.ufrrj.data.LocalUser;
import br.ufrrj.io.AppHttpComm;




public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity" ;
    private EditText    mEdtName      = null;
    private EditText    mEdtPasswd    = null;

    private CheckBox    mChkAnonimous = null;
    private ImageButton mBtnEnter     = null;
    private boolean     mLogin        = true;
    private ImageButton mBtnNewUser   = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtName      = findViewById(R.id.edtUser);
        mEdtPasswd    = findViewById(R.id.edtPasswd);
        mChkAnonimous = findViewById(R.id.chkAnonymous);
        mBtnEnter     = findViewById(R.id.btnEnter);

        mBtnNewUser   = findViewById(R.id.btnNewUser);


        mEdtName.setEnabled(mLogin);
        mEdtPasswd.setEnabled(mLogin);

        /* Debug - initial valeu */

        mEdtName.setText("bulhoesprojetovisao@gmail.com");
        mEdtPasswd.setText("bulhoesprojetovisao@gmail.com");
        /* Debug - initial valeu */




        AppHttpComm.getInstance().setContext(this.getApplicationContext());

        System.getInstance().setContext(this.getApplicationContext());

        System.getInstance().setAsset(this.getAssets());

        mChkAnonimous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                mLogin = !checked;
                mEdtName.setEnabled(mLogin);
                mEdtPasswd.setEnabled(mLogin);
            }
        });

        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                if (mLogin){
                    if (login()){
                        LocalUser.getInstance().setContext(getApplicationContext());
                        LocalUser.getInstance().setUserData(System.getInstance().getWorker());
                        LocalUser.getInstance().load();
                        LocalUser.getInstance().login();

                        /*
                        Intent intent = null;
                        intent = new Intent(LoginActivity.this, userDataActivity.class);

                        startActivity(intent);

                         */
                    }
                }else{
                    Intent intent = null;
                    intent = new Intent(LoginActivity.this, InitActivity.class);
                    startActivity(intent);
                }
                //protocol.php?state=1"

                // Toast toast = Toast.makeText(getApplicationContext(),  msg,  Toast.LENGTH_SHORT);
                // toast.show();
            }
        });

        mBtnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = null;
                intent = new Intent(LoginActivity.this, userNewActivity.class);
                startActivity(intent);
*/
            }
        });

    }

    private boolean login(){

        String user_id = AppHttpComm.getInstance().userLogin(mEdtName.getText().toString(), mEdtPasswd.getText().toString());

        int id = Integer.parseInt(user_id);
        if (id > 0){
            System.getInstance().deleteTemps();
            AppHttpComm.getInstance().getDataFromServer(getApplicationContext().getString(R.string.https_protocol) + "state=2&id_user=" + user_id, user_id + ".data.dat", true);
            AppHttpComm.getInstance().getDataFromServer(getApplicationContext().getString(R.string.https_protocol) + "state=3&id_user=" + user_id, user_id + ".phone.dat", true);
            AppHttpComm.getInstance().getDataFromServer(getApplicationContext().getString(R.string.https_protocol) + "state=4&id_user=" + user_id, user_id + ".email.dat", true);
            AppHttpComm.getInstance().getDataFromServer(getApplicationContext().getString(R.string.https_protocol) + "state=5&id_user=" + user_id, user_id + ".service.dat", true);
            AppHttpComm.getInstance().getDataFromServer(getApplicationContext().getString(R.string.https_protocol) + "state=6&id_user=" + user_id, user_id + ".addr.dat", true);
            System.getInstance().setWorker(id);
            //new Thread(new Runnable() {
            //public void run(){
            System.getInstance().loads();
            //  }
            //}).start();



            Toast.makeText(getApplicationContext(),
                    " Login realizado com sucesso!!!!",
                    Toast.LENGTH_SHORT).show();


            return true;

        }else
            Toast.makeText(getApplicationContext(),  "Usuário não cadastrado ou login ou senha incorretos!!!",
                    Toast.LENGTH_SHORT).show();

        return false;

    }

}
