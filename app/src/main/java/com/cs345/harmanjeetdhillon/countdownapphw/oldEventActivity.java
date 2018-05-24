package com.cs345.harmanjeetdhillon.countdownapphw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

/**
 * Class runs all three events at the same time, or will run events that were created by the user.
 * This view should also resize to the screen.
 *
 * Created by harmanjeetdhillon on 10/16/17.
 */

public class oldEventActivity extends AppCompatActivity {

    private static final String TAG = "OldEvent";


    // each event has its 4 vars for displaying times
    private int daysRemaining0;
    private int hrsRemaining0;
    private int minsRemaining0;
    private int secsRemaining0;

    private int daysRemaining1;
    private int hrsRemaining1;
    private int minsRemaining1;
    private int secsRemaining1;

    private int daysRemaining2;
    private int hrsRemaining2;
    private int minsRemaining2;
    private int secsRemaining2;

    private int freeVersion;
    private static final DecimalFormat f = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_event_main);


        if(runOnce0()){
            startEventTimer0();
        }
        if(runOnce1()){
            startEventTimer1();
        }
        if(runOnce2()){
            startEventTimer2();
        }

        if(!runOnce0() && !runOnce1() && !runOnce2()){
            Toast.makeText(this, "No Events Selected", Toast.LENGTH_LONG).show();
        }
    }

    /*
     * Method start count timer for the #1 event. Also starts the timer thread.
     */
    private void startEventTimer0(){

        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName1", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        String appendString = eventName + "1";
        int year = sharedPref.getInt("event-" + appendString + "a", 0);
        int month = sharedPref.getInt("event-" + appendString + "b", 0);
        int day = sharedPref.getInt("event-" + appendString + "c", 0);
        int hr = sharedPref.getInt("event-" + appendString + "d", 0);
        int min = sharedPref.getInt("event-" + appendString + "e", 0);

        Log.d(TAG, "Results " + year + " " + month + " " + day + " " + hr + " " + min + ". " + "sort=" + sortNumber);

        // String eventName = sharedPref.getString("event1Name", "Nothing");
        // calculate time in seconds from now until event
        GregorianCalendar later = new GregorianCalendar(year, month, day, hr, min);
        GregorianCalendar now = new GregorianCalendar();
        long eventTime = later.getTimeInMillis();
        long nowTime = now.getTimeInMillis();
        long diffSecs = (long) ((eventTime - nowTime) / 1000.0);

        // calculate time remaining until event
        final int SECS_PER_DAY = 86400;
        daysRemaining0 = (int) diffSecs / SECS_PER_DAY;
        long leftOver = diffSecs - daysRemaining0 * SECS_PER_DAY;

        hrsRemaining0 = (int) (leftOver / 3600);
        leftOver -= hrsRemaining0 * 3600;

        minsRemaining0 = (int) (leftOver / 60);
        leftOver -= minsRemaining0 * 60;
        secsRemaining0 = (int) leftOver;

        // get references to text views in countdown activity
        final TextView daysView = (TextView)
                findViewById(R.id.daysRemainingView0);
        final TextView hrsView = (TextView)
                findViewById(R.id.hrsRemainingView0);
        final TextView minsView = (TextView)
                findViewById(R.id.minsRemainingView0);
        final TextView secsView = (TextView)
                findViewById(R.id.secsRemainingView0);

        class MyTimer extends CountDownTimer { // in the Android API

            // see CountDownTimer documentation
            public MyTimer(long secsRemaining) {
                super(secsRemaining * 1000 + 1, 1000);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                secsRemaining0--;
                if (secsRemaining0 < 0) {
                    secsRemaining0 = 59;
                    minsRemaining0--;
                    if (minsRemaining0 < 0) {
                        minsRemaining0 = 59;
                        hrsRemaining0--;
                    }
                    if (hrsRemaining0 < 0) {
                        hrsRemaining0 = 0;
                        daysRemaining0--;
                    }
                }

                daysView.setText(f.format(daysRemaining0));
                hrsView.setText(f.format(hrsRemaining0));
                minsView.setText(f.format(minsRemaining0));
                secsView.setText(f.format(secsRemaining0));
            }

            @Override
            public void onFinish() {
                finish();  // in CountdownActivity
            }
        }

        // Calculate total number of seconds remaining to event
        int secs = secsRemaining0 + 60 * minsRemaining0 + 3600 * hrsRemaining0 +
                SECS_PER_DAY * daysRemaining0;
        MyTimer timer = new MyTimer(secs);
        timer.start();
    }
    /*
     * Method resets all info for event 1. Also changes view to the main act.
     */
    public void button0(View view){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String eventName = sharedPref.getString("eventName1", "Nothing");
        String appendString = eventName + "1";
        editor.putInt("event-" + appendString + "a", 0);
        editor.putInt("event-" + appendString + "b", 0);
        editor.putInt("event-" + appendString + "c", 0);
        editor.putInt("event-" + appendString + "d", 0);
        editor.putInt("event-" + appendString + "e", 0);
        editor.putInt("eventSort-" + eventName, 0);
        editor.apply();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    /*
     * Method start count timer for the #2 event. Also starts the timer thread.
     */
    private void startEventTimer1(){

        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName2", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        String appendString = eventName + "2";
        int year = sharedPref.getInt("event-" + appendString + "a", 0);
        int month = sharedPref.getInt("event-" + appendString + "b", 0);
        int day = sharedPref.getInt("event-" + appendString + "c", 0);
        int hr = sharedPref.getInt("event-" + appendString + "d", 0);
        int min = sharedPref.getInt("event-" + appendString + "e", 0);


        Log.d(TAG, "Results1 " + year + " " + month + " " + day + " " + hr + " " + min + ". " + "sort=" + sortNumber);
        // calculate time in seconds from now until event
        GregorianCalendar later = new GregorianCalendar(year, month, day, hr, min);
        GregorianCalendar now = new GregorianCalendar();
        long eventTime = later.getTimeInMillis();
        long nowTime = now.getTimeInMillis();
        long diffSecs = (long) ((eventTime - nowTime) / 1000.0);

        // calculate time remaining until event
        final int SECS_PER_DAY = 86400;
        daysRemaining1 = (int) diffSecs / SECS_PER_DAY;
        long leftOver = diffSecs - daysRemaining1 * SECS_PER_DAY;

        hrsRemaining1 = (int) (leftOver / 3600);
        leftOver -= hrsRemaining1 * 3600;

        minsRemaining1 = (int) (leftOver / 60);
        leftOver -= minsRemaining1 * 60;
        secsRemaining1 = (int) leftOver;

        // get references to text views in countdown activity
        final TextView daysView = (TextView)
                findViewById(R.id.daysRemainingView1);
        final TextView hrsView = (TextView)
                findViewById(R.id.hrsRemainingView1);
        final TextView minsView = (TextView)
                findViewById(R.id.minsRemainingView1);
        final TextView secsView = (TextView)
                findViewById(R.id.secsRemainingView1);

        class MyTimer extends CountDownTimer { // in the Android API

            // see CountDownTimer documentation
            public MyTimer(long secsRemaining) {
                super(secsRemaining * 1000 + 1, 1000);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                secsRemaining1--;
                if (secsRemaining1 < 0) {
                    secsRemaining1 = 59;
                    minsRemaining1--;
                    if (minsRemaining1 < 0) {
                        minsRemaining1 = 59;
                        hrsRemaining1--;
                    }
                    if (hrsRemaining1 < 0) {
                        hrsRemaining1 = 0;
                        daysRemaining1--;
                    }
                }

                daysView.setText(f.format(daysRemaining1));
                hrsView.setText(f.format(hrsRemaining1));
                minsView.setText(f.format(minsRemaining1));
                secsView.setText(f.format(secsRemaining1));
            }

            @Override
            public void onFinish() {
                finish();  // in CountdownActivity
            }
        }

        // Calculate total number of seconds remaining to event
        int secs = secsRemaining1 + 60 * minsRemaining1 + 3600 * hrsRemaining1 +
                SECS_PER_DAY * daysRemaining1;
        MyTimer timer = new MyTimer(secs);
        timer.start();
    }
    /*
     * Method resets all info for event 2. Also changes view to the main act.
     */
    public void button1(View view){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String eventName = sharedPref.getString("eventName2", "Nothing");
        String appendString = eventName + "2";
        editor.putInt("event-" + appendString + "a", 0);
        editor.putInt("event-" + appendString + "b", 0);
        editor.putInt("event-" + appendString + "c", 0);
        editor.putInt("event-" + appendString + "d", 0);
        editor.putInt("event-" + appendString + "e", 0);
        editor.putInt("eventSort-" + eventName, 0);
        editor.apply();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    /*
     * Method start count timer for the #3 event. Also starts the timer thread.
     */
    private void startEventTimer2(){

        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName3", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        String appendString = eventName + "3";
        int year = sharedPref.getInt("event-" + appendString + "a", 0);
        int month = sharedPref.getInt("event-" + appendString + "b", 0);
        int day = sharedPref.getInt("event-" + appendString + "c", 0);
        int hr = sharedPref.getInt("event-" + appendString + "d", 0);
        int min = sharedPref.getInt("event-" + appendString + "e", 0);

        Log.d(TAG, "Results2 " + year + " " + month + " " + day + " " + hr + " " + min + ". " + "sort=" + sortNumber);
        // String eventName = sharedPref.getString("event1Name", "Nothing");
        // calculate time in seconds from now until event
        GregorianCalendar later = new GregorianCalendar(year, month, day, hr, min);
        GregorianCalendar now = new GregorianCalendar();
        long eventTime = later.getTimeInMillis();
        long nowTime = now.getTimeInMillis();
        long diffSecs = (long) ((eventTime - nowTime) / 1000.0);

        // calculate time remaining until event
        final int SECS_PER_DAY = 86400;
        daysRemaining2 = (int) diffSecs / SECS_PER_DAY;
        long leftOver = diffSecs - daysRemaining2 * SECS_PER_DAY;

        hrsRemaining2 = (int) (leftOver / 3600);
        leftOver -= hrsRemaining2 * 3600;

        minsRemaining2 = (int) (leftOver / 60);
        leftOver -= minsRemaining2 * 60;
        secsRemaining2 = (int) leftOver;

        // get references to text views in countdown activity
        final TextView daysView = (TextView)
                findViewById(R.id.daysRemainingView2);
        final TextView hrsView = (TextView)
                findViewById(R.id.hrsRemainingView2);
        final TextView minsView = (TextView)
                findViewById(R.id.minsRemainingView2);
        final TextView secsView = (TextView)
                findViewById(R.id.secsRemainingView2);

        class MyTimer extends CountDownTimer { // in the Android API

            // see CountDownTimer documentation
            public MyTimer(long secsRemaining) {
                super(secsRemaining * 1000 + 1, 1000);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                secsRemaining2--;
                if (secsRemaining2 < 0) {
                    secsRemaining2 = 59;
                    minsRemaining2--;
                    if (minsRemaining2 < 0) {
                        minsRemaining2 = 59;
                        hrsRemaining2--;
                    }
                    if (hrsRemaining2 < 0) {
                        hrsRemaining2 = 0;
                        daysRemaining2--;
                    }
                }

                daysView.setText(f.format(daysRemaining2));
                hrsView.setText(f.format(hrsRemaining2));
                minsView.setText(f.format(minsRemaining2));
                secsView.setText(f.format(secsRemaining2));
            }

            @Override
            public void onFinish() {
                finish();  // in CountdownActivity
            }
        }

        // Calculate total number of seconds remaining to event
        int secs = secsRemaining2 + 60 * minsRemaining2 + 3600 * hrsRemaining2 +
                SECS_PER_DAY * daysRemaining2;
        MyTimer timer = new MyTimer(secs);
        timer.start();
    }
    /*
     * Method resets all info for event 3. Also changes view to the main act.
     * 3
     */
    public void button2(View view){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String eventName = sharedPref.getString("eventName3", "Nothing");
        String appendString = eventName + "3";
        editor.putInt("event-" + appendString + "a", 0);
        editor.putInt("event-" + appendString + "b", 0);
        editor.putInt("event-" + appendString + "c", 0);
        editor.putInt("event-" + appendString + "d", 0);
        editor.putInt("event-" + appendString + "e", 0);
        editor.putInt("eventSort-" + eventName, 0);
        editor.apply();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    /*
     * Method checks to see if a event 1 was created or saved
     */
    public boolean runOnce0(){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName1", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        if(sortNumber == 1){
            return true;
        }
        return false;
    }

    /*
     * Method checks to see if a event 2 was created or saved
     */
    public boolean runOnce1(){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName2", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        if(sortNumber == 2){
            return true;
        }
        return false;
    }

    /*
     * Method checks to see if a event 3 was created or saved
     */
    public boolean runOnce2(){
        SharedPreferences sharedPref = getSharedPreferences("EVENTDATA", Context.MODE_PRIVATE);
        String eventName = sharedPref.getString("eventName3", "Nothing");
        int sortNumber = sharedPref.getInt("eventSort-" + eventName,0);
        if(sortNumber == 3){
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
