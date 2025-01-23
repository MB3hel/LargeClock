package io.github.mb3hel.largeclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txt, ampm;

    private final MyDateChangeReceiver mDateReceiver = new MyDateChangeReceiver();

    public class MyDateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            updateTime();
        }
    }

    void updateTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm", Locale.US);
        String s = sdf.format(d);
        if(s.length() == 4)
            s = "!" + s; // ! = all off so width stays same as 5 character times
        txt.setText(s);
        SimpleDateFormat sdf2 = new SimpleDateFormat("a", Locale.US);
        String s2 = sdf2.format(d);
        if(s2.equals("AM")){
            ampm.setText("AM\n\n ");
        }else if(s2.equals("PM")){
            ampm.setText("\n\nPM");
        }else{
            ampm.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txtview);
        ampm = findViewById(R.id.ampm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mDateReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        updateTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDateReceiver);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}