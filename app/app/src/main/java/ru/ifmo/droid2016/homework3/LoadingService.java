package ru.ifmo.droid2016.homework3;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.Service.START_STICKY;

/**
 * Created by Koroleva Yana.
 */

public class LoadingService extends Service {

    boolean loadingProcess = false;
    String fileName = "image.jpg";
    String url = "http://lisimnik.ru/wp-content/uploads/2013/02/artleo.com-18003.jpg";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (loadingProcess) {
            return START_STICKY;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpURLConnection connection = (HttpURLConnection)
                            (new URL(url)).openConnection();
                    loadingProcess = true;

                    try (InputStream reader = connection.getInputStream()) {
                        try (FileOutputStream writer = new FileOutputStream(new File(getFilesDir(), fileName))) {
                            byte[] buffer = new byte[1024];
                            while (true) {
                                int tmp = reader.read(buffer, 0, 1024);
                                if (tmp > 0) {
                                    writer.write(buffer, 0, tmp);
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadingProcess = false;
                sendBroadcast(new Intent("ru.ifmo.droid16.homework3.broadcast"));
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
