package kr.lindol.android.myphonebook.service;

import java.util.HashMap;

public class PhoneNumberModel {
    private HashMap<String, String> mPhoneBookData;

    public PhoneNumberModel() {
        mPhoneBookData = new HashMap<String, String>();
    }

    public boolean contains(String phoneNumber) {
        return mPhoneBookData.containsKey(phoneNumber);
    }

    public String get(String phoneNumber) {
        if (mPhoneBookData.containsKey(phoneNumber)) {
            return mPhoneBookData.get(phoneNumber);
        }
        return "";
    }

    public boolean add(String phoneNumber, String extraData) {
        if (mPhoneBookData.containsKey(phoneNumber)) {
            return false;
        }

        mPhoneBookData.put(phoneNumber, extraData);
        return true;
    }
}
