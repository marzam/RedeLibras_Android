package br.ufrrj.io;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import br.ufrrj.R;

import static javax.net.ssl.SSLContext.getInstance;


public class HTTPCommSSL extends AsyncTask<String, Void, Boolean> {

    private static final int TIMEOUT = 1000;
    private static final int SIZE = 1024;

    private static final String TAG = "HTTPCommSSL";

    // Verifier that verifies all hosts
    private static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private Context mContext       = null;

    private int mBytesRead         = -1;

    private String mInMSG = null;

    public void setContex(Context c)        { mContext = c; }

    public int getBytesRead()               { return mBytesRead; }

    public String getInMsg(){

        String output = "";
        if (mInMSG != null)
            output = mInMSG;
        mInMSG = null;
        return output;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Dummy trust manager that trusts all certificates
        TrustManager localTrustmanager = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        };
        // Create SSLContext and set the socket factory as default
        try {
            SSLContext sslc = getInstance("TLS");
            sslc.init(null, new TrustManager[] { localTrustmanager }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }





    @Override
    protected Boolean doInBackground(String... params) {
        URL url                          = null;
        HttpsURLConnection urlConnection = null;
        InputStream inputstream          = null;
        String filename                  = params[1];
        boolean appendFile               = Boolean.valueOf(params[2]);
        byte[] msg                       = new byte[SIZE];
        boolean ret                      = false;

        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setHostnameVerifier(DUMMY_VERIFIER);

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(0);

            urlConnection.connect();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;

        }

        if (ret){
            try {
                inputstream = new BufferedInputStream(urlConnection.getInputStream());
                mBytesRead = 0;

                try {
                    int readBytes = -1;
                    do{
                        readBytes = inputstream.read(msg);
                        if (readBytes > 0) {
                            mBytesRead += readBytes;
                            if (filename.length() > 0)
                                save(filename, new String(msg, 0, readBytes, StandardCharsets.UTF_8), appendFile);
                            else {
                                mInMSG = new String(msg, 0, readBytes, StandardCharsets.UTF_8);
                                readBytes = -1;
                            }
                        }

                    }while(readBytes > -1);
                } catch (IOException e) {
                    e.printStackTrace();
                    ret = false;
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("HTTP:", e.getMessage());
                ret = false;
            } finally {
                urlConnection.disconnect();

            }

        }

        return ret;
    }


    private void save(String filename, String fileContents, boolean append){
        String           dir_name = mContext.getCacheDir() + "//" + mContext.getString(R.string.home) + "//";
        FileOutputStream output;
        File             file;
        File dir      = new File(dir_name);

        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        try {
            file   = new File(dir, filename);
            output = new FileOutputStream (file, append);

            output.write(fileContents.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}