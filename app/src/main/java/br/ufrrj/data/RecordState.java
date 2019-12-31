package br.ufrrj.data;

public class RecordState  implements Comparable<RecordState> {
    private int mID;
    private int mID_state_capital;
    private String mState;
    private String mInitials;




    public RecordState(RecordState r){
        mID = r.getID();
        mID_state_capital = r.getID_state_capital();
        mState = r.getState();
        mInitials = r.getInitials();


    }


    public RecordState(int _id,
                       int _id_state_capital,
                       String _state,
                       String _initials){
        mID = _id;
        mID_state_capital = _id_state_capital;
        mState = _state;
        mInitials = _initials;

    }

    public int getID() { return mID;  }
    public int getID_state_capital() { return mID_state_capital; }
    public String getState() { return mState;     }
    public String getInitials() { return mInitials; }



    @Override
    public int compareTo(RecordState otherRecord) { return mInitials.compareTo(otherRecord.mInitials); }
}