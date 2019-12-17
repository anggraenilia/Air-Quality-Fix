package com.blogspot.mekatronika.airmonitoringquality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {
    FirebaseDatabase databaseAqi1;
    Query myRef1;
    ImageView realtimeButton;
    ImageView placeButton;
    TextView textCo2;
    TextView textO2;
    TextView textTvoc;
    TextView textPm;
    TextView textTemp;
    TextView textRh;
    TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseAqi1 = FirebaseDatabase.getInstance();
        myRef1 = databaseAqi1.getReference("DSK-W2-WP").limitToLast(100);

        realtimeButton = findViewById(R.id.realtime_button);
        placeButton=findViewById(R.id.place_button);
        textCo2 = findViewById(R.id.text_co2);
        textO2=findViewById(R.id.text_o2);
        textTvoc=findViewById(R.id.text_tvoc);
        textPm=findViewById(R.id.text_pm);
        textTemp=findViewById(R.id.text_temp);
        textRh=findViewById(R.id.text_rh);
        textTime=findViewById(R.id.text_time);

        final DecimalFormat df = new DecimalFormat("#.#");

        realtimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Realtime.class));
            }
        });
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Place.class));
            }
        });

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String co2 = ds.child("CO2").getValue().toString();
                    String o2=ds.child("O2").getValue().toString();
                    String tvoc=ds.child("TVOC").getValue().toString();
                    String pm=ds.child("PM").getValue().toString();
                    String temp=ds.child("T").getValue().toString();
                    String rh=ds.child("RH").getValue().toString();
                    String timestamp = ds.child("Timestamp").getValue().toString();
//
                    if (!timestamp.isEmpty()) {

                        float SensorCo2=Float.parseFloat(co2);
                        float SensorO2=Float.parseFloat(o2);
                        float SensorTvoc=Float.parseFloat(tvoc);
                        float SensorPm=Float.parseFloat(pm);
                        float SensorTemp=Float.parseFloat(temp);
                        float SensorRh=Float.parseFloat(rh);
                        long TimeStamp = Long.parseLong(timestamp);
                        Date date = new Date((long) TimeStamp);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);

                        textCo2.setText(String.valueOf(df.format(SensorCo2)));
                        textO2.setText(String.valueOf(df.format(SensorO2)));
                        textTvoc.setText(String.valueOf(df.format(SensorTvoc)));
                        textPm.setText(String.valueOf(df.format(SensorPm)));
                        textTemp.setText(String.valueOf(df.format(SensorTemp)));
                        textRh.setText(String.valueOf(df.format(SensorRh)));
                        textTime.setText(sdf.format(date));

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
}

