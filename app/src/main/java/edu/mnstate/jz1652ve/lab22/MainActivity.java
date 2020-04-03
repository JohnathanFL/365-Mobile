package edu.mnstate.jz1652ve.lab22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static int notificationID = 101;
    private final static String KEY_TXT_REPLY = "keyTextReply";
    private final static String chID = "edu.mnstate.jz1652ve.lab22.news";

    NotificationManager nMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nMan = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(chID, "My News", "Example Direct Reply Notification");
    }

    private void createNotificationChannel(String id, String name, String desc) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            ch.setDescription(desc);
            ch.enableVibration(true);
            ch.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            nMan.createNotificationChannel(ch);
        }
        processInlineReply();
    }

    Notification.Builder getBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(this, chID);
        } else {
            return  new Notification.Builder(this);
        }
    }

    public void sendNotification(View view) {
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TXT_REPLY)
                .setLabel(getString(R.string.enterReplyHere))
                .build();

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Icon icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info);

        Notification.Action replyAction = new Notification.Action.Builder(icon, "Reply", pi)
                .addRemoteInput(remoteInput)
                .build();

        Notification newMsgNotification = getBuilder()
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(getResources().getString(R.string.msg))
                .addAction(replyAction)
                .build();

        nMan.notify(notificationID, newMsgNotification);

    }

    private void processInlineReply() {
        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if(remoteInput != null) {
            TextView reply = findViewById(R.id.reply);
            reply.setText(remoteInput.getCharSequence(KEY_TXT_REPLY).toString());

            Notification replyNotification = getBuilder()
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentText(getResources().getString(R.string.gotReply))
                    .build();

            nMan.notify(notificationID, replyNotification);

        }
    }
}
