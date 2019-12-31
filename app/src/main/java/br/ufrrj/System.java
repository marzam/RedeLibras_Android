package br.ufrrj;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import br.ufrrj.data.RecordCity;
import br.ufrrj.data.RecordServiceFromTable;
import br.ufrrj.data.RecordState;

import static android.content.Context.MODE_PRIVATE;



public class System {
    private static final String         TAG            = "System";

    private int mState = -1;

    private int mService = -1;

    private int mWorker  = -1;


    private Context mContext              = null;

    private AssetManager   mAsset         = null;

    public ArrayList<RecordState>       mListStates;

    public ArrayList<RecordCity>        mListCities;

    public ArrayList<RecordServiceFromTable>  mListService;

    private static final System ourInstance = new System();

    public static System getInstance() {
        return ourInstance;
    }

    private System() {
    }

    public void setContext(Context c)                {mContext = c; }

    public void setAsset(AssetManager a)             {mAsset = a ;  }

    public void setState(int s) { this.mState = s ;}

    public int getState() {return this.mState; }

    public String getState_toString(){ return Integer.toString(this.mState) ; }


    public void setService(int s) { this.mService = s ;}

    public int getService() {return this.mService; }

    public String getService_toString(){ return Integer.toString(this.mService) ; }


    public void setWorker(int s) { this.mWorker = s ;}

    public int getWorker() {return this.mWorker; }

    public String getWorker_toString(){ return Integer.toString(this.mWorker) ; }

    public String getBaseDir() { return mContext.getCacheDir() + "//" + mContext.getString(R.string.home) + "//"; }


    public void getCities (List<String> listCity, List<Integer> listID, int index){

        int idx           = 0;


        RecordCity rc;
        do{
            rc = mListCities.get(idx);
            idx++;
        }while ((idx < mListCities.size()) && (rc.getID_state() != index));

        /*
        if (rc.getID_state() == index){
            idx--;
        }
        */

        listCity.clear();
        listID.clear();

        while ((rc.getID_state() == index) && (idx < mListCities.size())){
            String city = rc.getCity();
            listCity.add(city);
            listID.add(rc.getID());
            rc = mListCities.get(idx++);
        }



    }

    //    public void save(String baseDir, String filename, String fileContents, boolean append){
    public void save( String filename, String fileContents, boolean append){
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

    public void save(String filename, String fileContents){
        FileOutputStream outputStream;

        try {
            outputStream = mContext.openFileOutput(filename,MODE_PRIVATE);
            // outputStream = new FileOutputStream(filename, false);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String load(String filename){

        FileInputStream inputStream;
        String line = "";
        try {
            inputStream = mContext.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            line =  bufferedReader.readLine();


            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return line;
    }


    public void loads(){
        loadServiceFromTable();
        loadStates();
        loadCities();
        int i = mListCities.size();

    }

    private void loadStates(){
        BufferedReader reader = null;
        mListStates = new ArrayList<RecordState>();
        mListStates.clear();



        try {
            reader = new BufferedReader(new InputStreamReader(mAsset.open("estados.dat"), "UTF-8"));

            String mLine = null;
            do{
                mLine = reader.readLine();
                if (mLine != null){

                    StringTokenizer tokens = new StringTokenizer(mLine, ";");
                    int _ID = Integer.valueOf(tokens.nextToken());
                    int _ID_state_capital = Integer.valueOf(tokens.nextToken());
                    String _State = new String(tokens.nextToken());
                    String _Initials = new String(tokens.nextToken());
                    //String _MapName = new_"mapa_" + _Initials.toLowerCase();

                    mListStates.add(new RecordState(_ID, _ID_state_capital, _State, _Initials));


                }

            }while (mLine != null);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }



    }


    private void loadCities(){
        BufferedReader reader = null;
        mListCities = new ArrayList<RecordCity>();

        mListCities.clear();

        try {
            reader = new BufferedReader(new InputStreamReader(mAsset.open("cidades.dat"), "UTF-8"));

            String mLine = null;
            do{
                mLine = reader.readLine();
                if (mLine != null){


                    StringTokenizer tokens = new StringTokenizer(mLine, ";");
                    int _ID       = Integer.valueOf(tokens.nextToken());
                    int _ID_state = Integer.valueOf(tokens.nextToken());
                    String _city  = new String(tokens.nextElement().toString());

                    mListCities.add(new RecordCity(_ID, _ID_state, _city));


                }

            }while (mLine != null);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }



    }

    private void loadServiceFromTable(){
        BufferedReader reader = null;
        mListService = new ArrayList<RecordServiceFromTable>();

        mListService.clear();

        try {
            reader = new BufferedReader(new InputStreamReader(mAsset.open("servicos.dat"), "UTF-8"));

            String mLine = null;
            do{
                mLine = reader.readLine();
                if (mLine != null){


                    StringTokenizer tokens = new StringTokenizer(mLine, ";");
                    int _ID       = Integer.valueOf(tokens.nextToken());
                    String _service  = new String(tokens.nextElement().toString());

                    mListService.add(new RecordServiceFromTable(_ID, _service));


                }

            }while (mLine != null);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }



    }

    public void deleteTemps() {

        String           dir_name = mContext.getCacheDir() + "//" + mContext.getString(R.string.home) + "//";

        //= //temp//";
        File dir      = new File(dir_name);
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }

            dir.delete();
        }



    }
    //--------------------------------------------------------------------------------------------------
// Zip methods
//--------------------------------------------------------------------------------------------------
//baseDir is upload path used to put new/update date
//zipFileName is the name of compressed file
    public boolean zip(String baseDir, String zipFileName){
        //public boolean zip(File sourceFile, File zipFile) {

        String           dir_name = mContext.getCacheDir() + "//" + baseDir + "//";
        File sourceFile           = new File(dir_name);
        File zipFile              = new File(mContext.getCacheDir() + "//", zipFileName);

        List<File> fileList = getSubFiles(sourceFile, true);
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            int bufferSize = 1024;
            byte[] buf = new byte[bufferSize];
            ZipEntry zipEntry;
            for(int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                zipEntry = new ZipEntry(sourceFile.toURI().relativize(file.toURI()).getPath());
                zipOutputStream.putNextEntry(zipEntry);
                if (!file.isDirectory()) {
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                    int readLength;
                    while ((readLength = inputStream.read(buf, 0, bufferSize)) != -1) {
                        zipOutputStream.write(buf, 0, readLength);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //IoUtils.closeOS(zipOutputStream);
        }
        return true;
    }

    private static List<File> getSubFiles(File baseDir, boolean isContainFolder) {
        List<File> fileList = new ArrayList<>();
        File[] tmpList = baseDir.listFiles();
        for (File file : tmpList) {
            if (file.isFile()) {
                fileList.add(file);
            }
            if (file.isDirectory()) {
                if (isContainFolder) {
                    fileList.add(file); //key code
                }
                fileList.addAll(getSubFiles(file, true));
            }
        }
        return fileList;
    }


    public byte[] getZipFile(String baseDir, String zipFileName){
        String           dir_name = mContext.getCacheDir() + "//" + baseDir + "//";
        File sourceFile           = new File(dir_name);
        File zipFile              = new File(mContext.getCacheDir() + "//", zipFileName);
        byte[] fileData           = new byte[(int) zipFile.length()];
        DataInputStream binary    = null;
        try {
            binary= new DataInputStream(new FileInputStream(zipFile));
            binary.readFully(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            binary.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return fileData;
    }

}