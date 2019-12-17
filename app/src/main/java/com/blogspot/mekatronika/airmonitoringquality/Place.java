package com.blogspot.mekatronika.airmonitoringquality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Place extends AppCompatActivity {
    ConstraintLayout constraintAddImages;
    ConstraintLayout constraintShowImages;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        constraintAddImages=findViewById(R.id.constraint_addimages);
        constraintShowImages=findViewById(R.id.constraint_showimages);

        constraintAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Place.this, AddImages.class));
            }
        });
        constraintShowImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Place.this, Showimage.class));
            }
        });
    }
}
