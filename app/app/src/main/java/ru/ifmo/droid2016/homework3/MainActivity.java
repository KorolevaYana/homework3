package ru.ifmo.droid2016.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    BroadcastReceiver startingReceiver, drawingReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView);
        image.setVisibility(View.INVISIBLE);

        startingReceiver = new StartingReceiver();

        drawingReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                drawImage();
            }
        };

        registerReceiver(startingReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(drawingReceiver, new IntentFilter("ru.ifmo.droid16.homework3.broadcast"));
    }

    private void drawImage() {
        File file = new File(getFilesDir(), "image.jpg");

        if (file.exists()) {
            try {
                image.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
                image.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                Toast toast = Toast.makeText(this, "Problems with drawing picture.", Toast.LENGTH_LONG);
                toast.show();
                e.printStackTrace();
            }
        }
    }
}
