package org.firstinspires.ftc.robotcontroller.internal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

public class ExceptionRecovery implements Thread.UncaughtExceptionHandler {
    private Activity activity;
    private Context context;

    public ExceptionRecovery(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        Log.e("FTCRobotControler", "Restarting App");

        try {
            Intent intent = new Intent(activity, FtcRobotControllerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);

            activity.finish();
            System.exit(2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
