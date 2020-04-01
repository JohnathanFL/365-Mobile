package edu.mnstate.jz1652ve.lab21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.notifyBtn).setOnClickListener(v -> {
            String id = "channel_001", name = "name";
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("CSIS 365 Notification!")
                    .setContentText("A test has arrived");

            // My tablet only supports up to API level 24, but this needs 26
            // so I refactored the code a bit to work in either event
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
                builder.setChannelId(id);
            }
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pi);

            manager.notify(0, builder.build());
        });
    }
}
