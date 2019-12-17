package com.blogspot.mekatronika.airmonitoringquality;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent;


    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);


    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        long currentTimestamp =(long) e.getX();
        Date date = new Date((long)currentTimestamp);

        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        tvContent.setText("" + df.format(e.getY())+"\n"+sdf.format(date));
        super.refreshContent(e, highlight); // <----- IMPORTANT

    }
}
