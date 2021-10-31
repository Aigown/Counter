package com.android.counter;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView mCountView;
    TextView mTimer;
    int Count=0;
    Button mTimerPopup;
    Button mTimerReset;
    Button mPlay;
    Button mCountReset;
    Button mMinusCount;
    Time time;


    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCountView=findViewById(R.id.Count);
        mTimer =findViewById(R.id.Timer);
        mTimerPopup=findViewById(R.id.timerpopup);
        mTimerReset=findViewById(R.id.reset);
        mPlay=findViewById(R.id.play);
        mCountReset =findViewById(R.id.resetcount);
        mMinusCount =findViewById(R.id.Minus_Count);

        /*TImer Setting*/
        mTimerPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog =new TimePickerDialog(MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        time = new Time(hour,minute,0);
                        String timer=time.toString();
                        mTimer.setText(timer);
                    }
                },24, 0, true);
                timePickerDialog.show();

            }

        });
        mTimerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimer.setText("00:00:00");
                mCountDownTimer.cancel();
            }
        });
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
                //LocalDateTime localDate = LocalDateTime.parse(date, formatter);
                long timeInMilliseconds = time.getHours()*3600000 + time.getMinutes()*60000;
                Log.d(TAG, "Date in milli :: FOR API >= 26 >>> " + timeInMilliseconds);
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                mCountDownTimer = new CountDownTimer(timeInMilliseconds, 1000) {
                    @Override
                    public void onFinish() {
                        mTimer.setText("00:00:00");
                    }

                    public void onTick(long millisUntilFinished) {

                        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                        mTimer.setText(hms);
                    }
                };
                mCountDownTimer.start();
            }
        });

        LinearLayout CounterLayout =findViewById(R.id.CounterLayout);
        CounterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count++;
                mCountView.setText( String.valueOf(Count));
            }
        });

        mCountReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountView.setText("00");
            }
        });
        mMinusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count--;
                mCountView.setText(String.valueOf(Count));
            }
        });

    }
}