package com.example.resh.analogclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;

public class MyItemAdapter extends ArrayAdapter {

    private Context context;
    private ClockPart[] parts;
    private int resource;

    public MyItemAdapter(@NonNull Context context, int resource, ClockPart [] parts) {
        super(context, resource, parts);

        this.context = context;
        this.resource = resource;
        this.parts = parts;
    }

    public View getView(final int position, View view, ViewGroup group) {
        View row = null;

        //get an inflater and inflate row
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        //wire objects with row's widgets
        row = inflater.inflate(resource, null);

        //populate row's objects with data
        final ImageButton colorButton = row.findViewById(R.id.imageButton);
        TextView componentType = row.findViewById(R.id.textView);

        final ClockPart currentPart = parts[position];
        SharedPreferences sharedPref = context.getSharedPreferences("ColorSettings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        componentType.setText(currentPart.getName());
        Log.d("DEBUGGING", "Value for color for " + currentPart.getName() + " : " + currentPart.getColorString());
        colorButton.setBackgroundColor(Color.parseColor(currentPart.getColorString()));

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                builder.setTitle("ColorPicker Dialog");
                builder.setPreferenceName("MyColorPickerDialog");
//                builder.setFlagView(new CustomFlag(this, R.layout.layout_flag));
                builder.setPositiveButton("CONFIRM", new ColorListener() {
                    @Override
                    public void onColorSelected(ColorEnvelope colorEnvelope) {

                        Log.d("DEBUGGING", "For " + currentPart.getName() + " setting Color : " + colorEnvelope.getColorHtml());
                        //Set color of item in list
                        String colorString = "#" + colorEnvelope.getColorHtml();
                        currentPart.setColorString(colorString);
                        colorButton.setBackgroundColor(colorEnvelope.getColor());
                        editor.putString(currentPart.getName(), colorString);
                        editor.commit();
                        parts[position] = currentPart;
                        Log.d("DEBUGGING", "Updated data of list: " + parts[position].getColorString());

                        notifyDataSetChanged();
                        Log.d("DEBUGGING", "Notified data change");
                        MySurfaceView.prefChanged = true;

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        return row;
    }

}

