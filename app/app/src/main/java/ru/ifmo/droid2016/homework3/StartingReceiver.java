package ru.ifmo.droid2016.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Koroleva Yana.
 */

public class StartingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, LoadingService.class));
    }
}
