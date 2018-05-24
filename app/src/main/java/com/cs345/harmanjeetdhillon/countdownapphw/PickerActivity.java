package com.cs345.harmanjeetdhillon.countdownapphw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import java.util.GregorianCalendar;

/**
 * Provides event handler for button in the user interface, which creates an intent with
 * extras from date and time pickers. The intent is used in launching a new activity to
 * display a countdown. Code From class from Dr.Coles.
 *
 * This view is resizable and should work on any display size.
 *
 * @author Harmanjeet Dhillon
 */
public class PickerActivity extends AppCompatActivity {

    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String YEAR = "year";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String EVENTNAME = "eventName";
    public static final String EVENTCOUNT = "eventCount";
    public static int ECOUNT = 0;

    // for receiving info returned from CountdownActivity
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickRadioButton(View view){
        // Is the button now checked?
        boolean checker = ((RadioButton) view).isChecked();

        // Check what button is clicked
        switch(view.getId()) {
            case R.id.event1RB:
                if (checker)
                   ECOUNT = 1;
                    break;
            case R.id.event2RB:
                if (checker)
                    ECOUNT = 2;
                    break;
            case R.id.event3RB:
                if (checker)
                    ECOUNT = 3;
                    break;
        }
    }

    /**
     * Create an explicit intent with extras encoding the event date and time specified by
     * the user.
     */
    public void startCountDown(View view) {
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        EditText eventName = (EditText) findViewById(R.id.editTextEvent);

        Intent intent = new Intent(getApplicationContext(), CountDownActivity.class);
        intent.putExtra(MONTH, dp.getMonth());
        intent.putExtra(DAY, dp.getDayOfMonth());
        intent.putExtra(YEAR, dp.getYear());
        intent.putExtra(HOUR, tp.getHour());  // requires minSdkVersion 23
        intent.putExtra(MINUTE, tp.getMinute());
        intent.putExtra(EVENTNAME, eventName.getText().toString());
        intent.putExtra(EVENTCOUNT,ECOUNT);

        // WARNING: This will cause the application to crash unless we have added an
        // activity entry in the manifest for CountdownActivity!
        startActivity(intent);

        // Starting activity and processing an intent returned from it
        // startActivityForResult(intent, REQUEST_CODE);
    }

    // Not used, but this is where we would receive extras from CountdownActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String msg = data.getStringExtra("resultMessage");

            // Now do something with the message...
        }
    }

}
