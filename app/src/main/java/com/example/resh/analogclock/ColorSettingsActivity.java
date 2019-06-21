package com.example.resh.analogclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;

public class ColorSettingsActivity extends AppCompatActivity {

    private ListView listView = null;
    private ArrayAdapter<String> adapter = null;
    private String [] clockHands = {"Clock outline", "Hour Markings", "Hour Hand", "Minute Hand", "Second Hand"};
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        this.activity = this;
        //wire and make the list
        listView = findViewById(R.id.listView);

        //Get set colors from shared preference
        SharedPreferences sharedPref = this.getSharedPreferences("ColorSettings", Context.MODE_PRIVATE);
        String outlineColor = sharedPref.getString("Clock Outline","#FF0000");
        String hourMarkingsColor = sharedPref.getString("Hour Markings","#00FF00");
        String hourHandColor = sharedPref.getString("Hour Hand","#FF0000");
        String minuteHandColor = sharedPref.getString("Minute Hand","#FF0000");
        String secondHandColor = sharedPref.getString("Second Hand","#0000FF");

        ClockPart [] parts = {
                new ClockPart("Clock Outline", outlineColor),
                new ClockPart("Hour Markings", hourMarkingsColor),
                new ClockPart("Hour Hand", hourHandColor),
                new ClockPart("Minute Hand", minuteHandColor),
                new ClockPart("Second Hand", secondHandColor),
        };
        String [] handColors = {"#FF0000", "#FF0000", "#FF0000", "#FF0000", "#FF0000"};

        adapter = new MyItemAdapter(this, R.layout.row_layout, parts);

        listView.setAdapter(adapter);

    }
}
