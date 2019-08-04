package kr.lindol.android.myphonebook.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import kr.lindol.android.myphonebook.MainActivity;
import kr.lindol.android.myphonebook.R;
import kr.lindol.android.myphonebook.base.CallState;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService implements MyIntentServiceContract.View {

    private static final String TAG = MyIntentService.class.getSimpleName();
    private static final String ACTION_INCOMING_CALL = "kr.lindol.android.myphonebook.service.action.INCOMING_CALL";
    private static final String EXTRA_PHONE_NUMBER = "kr.lindol.android.myphonebook.service.extra.PHONE_NUMBER";
    private static final String EXTRA_STATE = "kr.lindol.android.myphonebook.service.extra.STATE";

    private MyIntentServiceContract.Presenter mPresenter;

    public MyIntentService() {
        super("MyIntentService");

        mPresenter = new MyIntentServicePresenter(this);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionIncomingCall(Context context, String phoneNumber, CallState state) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INCOMING_CALL);
        intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        intent.putExtra(EXTRA_STATE, state.getState());
        context.startService(intent);
    }

    public static void startActionIncomingCallInForeground(Context context, String phoneNumber, CallState state) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(context, MyIntentService.class);
            intent.setAction(ACTION_INCOMING_CALL);
            intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
            intent.putExtra(EXTRA_STATE, state.getState());
            context.startForegroundService(intent);
        } else {
            Log.w(TAG, "startActionIncomingCallInForegound() is not supported in this SDK [" + Build.VERSION.SDK_INT + "]");
        }
    }

    private void handleForegroundForOsPolicy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction("Action2");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            //Bitmap icon = BitmapFactory.decodeResource(getResources(),
            //        R.mipmap.ic_launcher_round);

            Notification notification = new NotificationCompat.Builder(this,
                    "channel1")
                    .setContentTitle("backgound machine")
                    .setTicker("측정중...")
                    .setContentText("백그라운드 동작중")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();

            startForeground(111, notification);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INCOMING_CALL.equals(action)) {
                handleForegroundForOsPolicy();

                final String param1 = intent.getStringExtra(EXTRA_PHONE_NUMBER);
                final String param2 = intent.getStringExtra(EXTRA_STATE);
                handleActionIncomingCall(param1, param2);
            }
        }
    }

    private void handleActionIncomingCall(String phoneNumber, String state) {
        mPresenter.handleIncomingCall(phoneNumber, state);
    }

    @Override
    public void showSummary(String phoneNumber, String information) {
        Log.d(TAG, "showSummaryView: " + phoneNumber + " information: " + information);
    }

    @Override
    public void closeSummary() {
        Log.d(TAG, "closeSummaryView:");
    }

    @Override
    public void showErrorForNotFoundPhoneNumber(String phoneNumber) {
        Log.d(TAG, "showNotFoundPhoneNumberView: " + phoneNumber);
    }
}
