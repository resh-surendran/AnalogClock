package com.example.resh.analogclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.alarm_settings_item:
                Intent intent = new Intent(this.getApplicationContext(), AlarmActivity.class);

                startActivity(intent);
                break;
            case R.id.color_settings_item:
                Intent intent1 = new Intent(this.getApplicationContext(), ColorSettingsActivity.class);

                startActivity(intent1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
