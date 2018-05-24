package com.cs345.harmanjeetdhillon.countdownapphw;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



/**
 * Class shows the new count down and also saves time to the shared Prefs.
 *
 * @author harmanjeetdhillon on 10/12/17.
 */

public class CountDownActivity extends AppCompatActivity {

        private static final String TAG = "MainActivity";

        // for returning extras from this activity to main activity
        private int daysRemaining;
        private int hrsRemaining;
        private int minsRemaining;
        private int secsRemaining;
        private static final DecimalFormat f = new DecimalFormat("00");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.countdown_main);

            Bundle extras = getIntent().getExtras();
            int month = extras.getInt(PickerActivity.MONTH);
            int day = extras.getInt(PickerActivity.DAY);
            int year = extras.getInt(PickerActivity.YEAR);
            int hr = extras.getInt(PickerActivity.HOUR);
            int min = extras.getInt(PickerActivity.MINUTE);
            int eventCount = extras.getInt(PickerActivity.EVENTCOUNT);
            String eventName = extras.getString(PickerActivity.EVENTNAME);


                //Saving data for later use.
                SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                //event Name appended with event Count because of over write issues
                editor.putInt("event-" + eventName + eventCount + "a", year);
                editor.putInt("event-" + eventName + eventCount+ "b", month);
                editor.putInt("event-" + eventName + eventCount+ "c", day);
                editor.putInt("event-" + eventName + eventCount+ "d", hr);
                editor.putInt("event-" + eventName + eventCount+ "e", min);
                editor.putString("eventName" + eventCount, eventName);
                editor.putInt("eventSort-" + eventName, eventCount);
                editor.commit();

            Log.d(TAG, "Results " + year + " " + month + " " + day + " " + hr + " " + min + ". " + "sort=" + eventCount);

            // calculate time in seconds from now until event
            GregorianCalendar later = new GregorianCalendar(year, month, day, hr, min);
            GregorianCalendar now = new GregorianCalendar();
            long eventTime = later.getTimeInMillis();
            long nowTime = now.getTimeInMillis();
            long diffSecs = (long) ((eventTime - nowTime) / 1000.0);

            // calculate time remaining until event
            final int SECS_PER_DAY = 86400;
            daysRemaining = (int) diffSecs / SECS_PER_DAY;
            long leftOver = diffSecs - daysRemaining * SECS_PER_DAY;

            hrsRemaining = (int) (leftOver / 3600);
            leftOver -= hrsRemaining * 3600;

            minsRemaining = (int) (leftOver / 60);
            leftOver -= minsRemaining * 60;
            secsRemaining = (int) leftOver;

            // get references to text views in countdown activity
            final TextView daysView = (TextView)
                    findViewById(R.id.daysRemainingView);
            final TextView hrsView = (TextView)
                    findViewById(R.id.hrsRemainingView);
            final TextView minsView = (TextView)
                    findViewById(R.id.minsRemainingView);
            final TextView secsView = (TextView)
                    findViewById(R.id.secsRemainingView);

            class MyTimer extends CountDownTimer { // in the Android API

                // see CountDownTimer documentation
                public MyTimer(long secsRemaining) {
                    super(secsRemaining * 1000 + 1, 1000);
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    secsRemaining--;
                    if (secsRemaining < 0) {
                        secsRemaining = 59;
                        minsRemaining--;
                        if (minsRemaining < 0) {
                            minsRemaining = 59;
                            hrsRemaining--;
                        }
                        if (hrsRemaining < 0) {
                            hrsRemaining = 0;
                            daysRemaining--;
                        }
                    }

                    daysView.setText(f.format(daysRemaining));
                    hrsView.setText(f.format(hrsRemaining));
                    minsView.setText(f.format(minsRemaining));
                    secsView.setText(f.format(secsRemaining));
                }

                @Override
                public void onFinish() {
                    finish();  // in CountdownActivity
                }
            }

            // Calculate total number of seconds remaining to event
            int secs = secsRemaining + 60 * minsRemaining + 3600 * hrsRemaining +
                    SECS_PER_DAY * daysRemaining;
            MyTimer timer = new MyTimer(secs);
            timer.start();

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (item.getItemId() == R.id.action_settings) {
                return true;
            }

            // This executes if the user navigates back to the main activity via Up button.
            if (id == android.R.id.home) {
                String msg = daysRemaining + " days remaining.";
                Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }

            return super.onOptionsItemSelected(item);
        }

    }
