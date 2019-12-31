package br.ufrrj.io;

import android.content.Context;
import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

import static javax.net.ssl.SSLContext.getInstance;

public class HTTPCommSSLSendFile extends AsyncTask<String, Void, Boolean>{

    private static final int TIMEOUT = 1000;
    private static final int SIZE    = 1024;

    private static final String TAG = "HTTPCommSSLSendFile";

    private String attachmentName = "zip_file";
    private String attachmentFileName = "";
    private String crlf = "\r\n";
    private String twoHyphens = "--";
    private String boundary =  "*****";

    private byte[] data= null;

    private String fileLocation = null;

    private int status = -1;

    private String status_msg = "";

    public void setFileLocation(String path){ fileLocation = path; }

    // Verifier that verifies all hosts
    private static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private Context mContext       = null;

    private int mBytesRead         = -1;

    private String mInMSG = null;

    public void setData(byte[] d)                {this.data = d; }


    public void setContex(Context c)        { mContext = c; }

    public int getBytesRead()               { return mBytesRead; }

    public String getInMsg(){

        String output = "";
        if (mInMSG != null)
            output = mInMSG;
        mInMSG = null;
        return output;
    }


    public String getStatusError(){ return new String(this.status_msg); }

    private void setStatusMsg(){
        switch (this.status){
            case HttpsURLConnection.HTTP_ACCEPTED: status_msg = "HTTP Status-Code 202: Accepted.";break;
            case HttpsURLConnection.HTTP_BAD_GATEWAY: status_msg = "HTTP Status-Code 502: Bad Gateway.";break;
            case HttpsURLConnection.HTTP_BAD_METHOD: status_msg = "HTTP Status-Code 405: Method Not Allowed.";break;
            case HttpsURLConnection.HTTP_BAD_REQUEST: status_msg = "HTTP Status-Code 400: Bad Request.";break;
            case HttpsURLConnection.HTTP_CLIENT_TIMEOUT: status_msg = "HTTP Status-Code 408: Request Time-Out.";break;
            case HttpsURLConnection.HTTP_CONFLICT: status_msg = "HTTP Status-Code 409: Conflict.";break;
            case HttpsURLConnection.HTTP_CREATED: status_msg = "HTTP Status-Code 201: Created.";break;
            case HttpsURLConnection.HTTP_ENTITY_TOO_LARGE: status_msg = "HTTP Status-Code 413: Request Entity Too Large.";break;
            case HttpsURLConnection.HTTP_FORBIDDEN: status_msg = "HTTP Status-Code 403: Forbidden.";break;
            case HttpsURLConnection.HTTP_GATEWAY_TIMEOUT: status_msg = "HTTP Status-Code 504: Gateway Timeout. ";break;
            case HttpsURLConnection.HTTP_GONE: status_msg = "HTTP Status-Code 410: Gone. ";break;
            case HttpsURLConnection.HTTP_INTERNAL_ERROR: status_msg = "HTTP Status-Code 500: Internal Server Error. ";break;
            case HttpsURLConnection.HTTP_LENGTH_REQUIRED: status_msg = "HTTP Status-Code 411: Length Required." ;break;
            case HttpsURLConnection.HTTP_MOVED_PERM: status_msg = "HTTP Status-Code 301: Moved Permanently. ";break;
            case HttpsURLConnection.HTTP_MOVED_TEMP: status_msg = "HTTP Status-Code 302: Temporary Redirect. ";break;
            case HttpsURLConnection.HTTP_MULT_CHOICE: status_msg = "HTTP Status-Code 300: Multiple Choices.";break;
            case HttpsURLConnection.HTTP_NOT_ACCEPTABLE: status_msg = "HTTP Status-Code 406: Not Acceptable. ";break;
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE: status_msg = "HTTP Status-Code 203: Non-Authoritative Information. ";break;
            case HttpsURLConnection.HTTP_NOT_FOUND: status_msg = "HTTP Status-Code 404: Not Found. ";break;
            case HttpsURLConnection.HTTP_NOT_IMPLEMENTED: status_msg = "HTTP Status-Code 501: Not Implemented. ";break;
            case HttpsURLConnection.HTTP_NOT_MODIFIED: status_msg = "HTTP Status-Code 304: Not Modified. ";break;
            case HttpsURLConnection.HTTP_NO_CONTENT: status_msg = "HTTP Status-Code 204: No Content.";break;
            case HttpsURLConnection.HTTP_OK: status_msg = "HTTP Status-Code 200: OK. ";break;
            case HttpsURLConnection.HTTP_PARTIAL: status_msg = "HTTP Status-Code 206: Partial Content.";break;
            case HttpsURLConnection.HTTP_PAYMENT_REQUIRED: status_msg = "HTTP Status-Code 402: Payment Required. ";break;
            case HttpsURLConnection.HTTP_PRECON_FAILED: status_msg = "HTTP Status-Code 412: Precondition Failed. ";break;
            case HttpsURLConnection.HTTP_PROXY_AUTH: status_msg = "HTTP Status-Code 407: Proxy Authentication Required.";break;
            case HttpsURLConnection.HTTP_REQ_TOO_LONG: status_msg = "HTTP Status-Code 414: Request-URI Too Large.";break;
            case HttpsURLConnection.HTTP_RESET: status_msg = "HTTP Status-Code 205: Reset Content. " ;break;
            case HttpsURLConnection.HTTP_SEE_OTHER: status_msg = "HTTP Status-Code 303: See Other. ";break;
            case HttpsURLConnection.HTTP_UNAUTHORIZED: status_msg = "HTTP Status-Code 401: Unauthorized. ";break;
            case HttpsURLConnection.HTTP_UNAVAILABLE: status_msg = "HTTP Status-Code 503: Service Unavailable. ";break;
            case HttpsURLConnection.HTTP_UNSUPPORTED_TYPE: status_msg = "HTTP Status-Code 415: Unsupported Media Type.";break;
            case HttpsURLConnection.HTTP_USE_PROXY: status_msg = "HTTP Status-Code 305: Use Proxy. ";break;
            case HttpsURLConnection.HTTP_VERSION: status_msg = "HTTP Status-Code 505: HTTP Version Not Supported.";break;

        }//end-switch (this.status){
    }//end-private void setStatusMsg(){

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
        boolean ret                      = false;

        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setHostnameVerifier(DUMMY_VERIFIER);
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(0);


            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundary);

            urlConnection.connect();

        } catch (IOException e) {
            e.printStackTrace();

        }

        DataOutputStream request = null;
        try {
            request = new DataOutputStream(urlConnection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

//addFormField
        try {
            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
            //request.writeBytes("Content-Disposition: form-data; name=\"" + this.attachmentName + "\";filename=\"" + this.attachmentFileName + "\"" + this.crlf);
            //request.writeBytes(this.crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"zip_file\""+ this.crlf);
            request.writeBytes("Content-Type: text/plain; charset=UTF-8" + this.crlf);
            request.writeBytes(this.crlf);
            request.writeBytes(fileLocation + this.crlf);
            request.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //byte[] data = this.getZipFile("upload", "test.zip");
//addFilePart
        attachmentFileName = params[1];
        try {
            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
            //request.writeBytes("Content-Disposition: form-data; name=\"zip_file\""+ this.crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"zip_file\";filename=\"" + this.attachmentFileName  + "\"" + this.crlf);
            request.writeBytes(this.crlf);
            request.write(data);


            request.writeBytes(this.crlf);
            request.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.crlf);

            request.flush();
            request.close();

            status  = urlConnection.getResponseCode();
            setStatusMsg();
            if (status == urlConnection.HTTP_OK)
                ret = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }


}
