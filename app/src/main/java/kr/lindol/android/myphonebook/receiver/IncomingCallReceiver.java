package kr.lindol.android.myphonebook.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import kr.lindol.android.myphonebook.base.CallState;
import kr.lindol.android.myphonebook.service.MyIntentService;

public class IncomingCallReceiver extends BroadcastReceiver {

    private static final String TAG = IncomingCallReceiver.class.getSimpleName();
    private static int mLastCallState;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "PHONE_STATE intent received");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int callState = tm.getCallState();
        if (mLastCallState == callState) {
            Log.d(TAG, "callState: " + callState);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            switch (callState) {
                case TelephonyManager.CALL_STATE_RINGING:
                    processIncomingCall(context, incomingNumber, CallState.RINGING);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    processIncomingCall(context, incomingNumber, CallState.OFFHOOK);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    processIncomingCall(context, incomingNumber, CallState.IDLE);
                    break;
                default:
                    Log.w(TAG, "Unknown call state: " + callState);
                    break;
            }
        } else {
            mLastCallState = callState;
        }
    }

    /**
     * ref
     * https://www.blueswt.com/71
     *
     * @param context
     * @param phoneNumber
     * @param state
     */
    private void processIncomingCall(Context context, String phoneNumber, CallState state) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // make notification
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("channel1", "1번 채널", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("1번 채널입니다.");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);

            MyIntentService.startActionIncomingCallInForeground(context, phoneNumber, state);
        } else {
            MyIntentService.startActionIncomingCall(context, phoneNumber, state);
        }
    }
}