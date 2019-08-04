package kr.lindol.android.myphonebook.service;

import kr.lindol.android.myphonebook.base.CallState;

public class MyIntentServicePresenter implements MyIntentServiceContract.Presenter {

    private MyIntentServiceContract.View mView;
    private PhoneNumberModel mModel;

    public MyIntentServicePresenter(MyIntentServiceContract.View view) {
        mView = view;

        mModel = new PhoneNumberModel();
    }
    public PhoneNumberModel getModel() {
        return mModel;
    }
    @Override
    public void handleIncomingCall(String phoneNumber, String state) {
        if (state.equals(CallState.RINGING.getState())) {
            if (mModel.contains(phoneNumber)) {
                String extraData = mModel.get(phoneNumber);
                mView.showSummary(phoneNumber, extraData);
            } else {
                mView.showErrorForNotFoundPhoneNumber(phoneNumber);
            }
        } else {
            mView.closeSummary();
        }
    }
}
