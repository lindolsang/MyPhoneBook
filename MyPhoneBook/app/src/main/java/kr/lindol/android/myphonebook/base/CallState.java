package kr.lindol.android.myphonebook.base;

public enum CallState {
    RINGING("RINGING", 1),
    OFFHOOK("OFFHOOK", 2),
    IDLE("IDLE", 0);

    private final String mState;
    private final int mIntState;

    public String getState() {
        return mState;
    }

    public int getIntState() {
        return mIntState;
    }

    CallState(String stateStr, int stateInt) {
        mState = stateStr;
        mIntState = stateInt;
    }
}
