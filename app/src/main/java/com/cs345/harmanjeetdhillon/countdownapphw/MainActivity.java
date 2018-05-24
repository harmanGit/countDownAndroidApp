package com.cs345.harmanjeetdhillon.countdownapphw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

/**
 * Class is used for the main activity, then first view
 * that pops up when the app is started.This can segue into
 * the new event view or the old saved event view.
 *
 * @author harmanjeetdhillon on 10/12/17.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Notifies users that the free app only allows 3 events, and nothing more
        Toast.makeText(this, "FREE APP ONLY ALLOWS UP TO 3 EVENTS!", Toast.LENGTH_LONG).show();
    }

    /*
     * Method segues into the new event view
     */
    public void newEventPA(View view){
        Intent i = new Intent(getApplicationContext(), PickerActivity.class);
        startActivity(i);
    }

    /*
     * Method segues into the old event view
     */
    public void oldEventPA(View view){
        Intent ii = new Intent(getApplicationContext(), oldEventActivity.class);
        startActivity(ii);
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
