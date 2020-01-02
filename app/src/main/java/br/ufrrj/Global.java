package br.ufrrj;

public class Global {

    private static int mSelectedState = -1;
    public static int getSelectedState(){ return mSelectedState; }
    public static String getSelectedStateString(){ return Integer.toString(mSelectedState); }
    public static void setSelectedState(int s){ mSelectedState = s; }

    private static int mSelectedCity = -1;
    public static int getSelectedCity(){ return mSelectedCity; }
    public static String getSelectedCityString(){ return Integer.toString(mSelectedCity); }
    public static void setSelectedCity(int c){ mSelectedCity = c; }


    private static int mSelectedService = -1;
    public static int getSelectedService(){ return mSelectedService; }
    public static String getSelectedServiceString(){ return Integer.toString(mSelectedService); }
    public static void setSelectedService(int w){ mSelectedService = w; }

    private static int mSelectedWorker = -1;
    public static int getSelectedWorker(){ return mSelectedWorker; }
    public static String getSelectedWorkerString(){ return Integer.toString(mSelectedWorker); }
    public static void setSelectedWorker(int w){ mSelectedWorker = w; }

    private static String mAddr = "";
    public static String getWorkerAddr(){ return mAddr; }
    public static void setWokerAddr(String s){ mAddr = new String(s); }

    private static String mPhone = "";
    public static String getWorkerPhone(){ return mPhone; }
    public static void setWokerPhone(String p){ mPhone = new String(p); }

    private static String mEmail = "";
    public static String getWorkerEmail(){ return mEmail; }
    public static void setWokerEmail(String e){ mEmail = new String(e); }

    private static final Global ourInstance = new Global();
    public static Global getInstance() {
        return ourInstance;
    }
    private Global() { }

}
