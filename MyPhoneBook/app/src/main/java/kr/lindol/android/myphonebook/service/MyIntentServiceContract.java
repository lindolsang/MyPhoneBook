package kr.lindol.android.myphonebook.service;

public interface MyIntentServiceContract {
    interface View {
        void showSummary(String phoneNumber, String information);

        void closeSummary();

        void showErrorForNotFoundPhoneNumber(String phoneNumber);
    }

    interface Presenter {
        void handleIncomingCall(String phoneNumber, String state);
    }
}
