package com.blogspot.mekatronika.airmonitoringquality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.EntityReference;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Realtime extends AppCompatActivity {

    FirebaseDatabase databaseRealtime;
    Query refRealtime;

    ArrayList<Entry> co2Data;
    ArrayList<Entry> o2Data;
    ArrayList<Entry> tvocData;
    ArrayList<Entry> pmData;
    ArrayList<Entry> tempData;
    ArrayList<Entry> rhData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        databaseRealtime=FirebaseDatabase.getInstance();
        refRealtime=databaseRealtime.getReference("FSD-W1-NP").limitToLast(3000);
        final LineChart lineChart1;
        final LineChart lineChart2;
        final LineChart lineChart3;
        final LineChart lineChart4;
        final LineChart lineChart5;
        final LineChart lineChart6;

        lineChart1=findViewById(R.id.realtime_chart1);
        lineChart2=findViewById(R.id.realtime_chart2);
        lineChart3=findViewById(R.id.realtime_chart3);
        lineChart4=findViewById(R.id.realtime_chart4);
        lineChart5=findViewById(R.id.realtime_chart5);
        lineChart6=findViewById(R.id.realtime_chart6);


        lineChart1.setDrawBorders(true);
        lineChart1.setDragEnabled(true);
        CustomMarkerView mv1 = new CustomMarkerView(this, R.layout.marker_view);
        mv1.setChartView(lineChart1);
        lineChart1.setMarker(mv1);
        lineChart1.setTouchEnabled(true);
        lineChart1.setDrawMarkers(true);
        lineChart1.setHighlightPerTapEnabled(true);
        lineChart1.setViewPortOffsets(50, 50, 100, 50);



        lineChart2.setDrawBorders(true);
        lineChart2.setDragEnabled(true);

        lineChart3.setDrawBorders(true);
        lineChart3.setDragEnabled(true);

        lineChart4.setDrawBorders(true);
        lineChart4.setDragEnabled(true);

        lineChart5.setDrawBorders(true);
        lineChart5.setDragEnabled(true);

        lineChart6.setDrawBorders(true);
        lineChart6.setDragEnabled(true);
        refRealtime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                co2Data=new ArrayList<>();
                o2Data=new ArrayList<>();
                tvocData=new ArrayList<>();
                pmData=new ArrayList<>();
                tempData=new ArrayList<>();
                rhData=new ArrayList<>();

                int i=0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String co2 = ds.child("CO2").getValue().toString();
                    String o2=ds.child("O2").getValue().toString();
                    String tvoc=ds.child("TVOC").getValue().toString();
                    String pm=ds.child("PM").getValue().toString();
                    String temp=ds.child("T").getValue().toString();
                    String rh=ds.child("RH").getValue().toString();
                    String timestamp = ds.child("Timestamp").getValue().toString();

                    if(!timestamp.isEmpty()){
                        if(i==11){
                            float SensorCo2=Float.parseFloat(co2);
                            float SensorO2=Float.parseFloat(o2);
                            float SensorTvoc=Float.parseFloat(tvoc);
                            float SensorPm=Float.parseFloat(pm);
                            float SensorTemp=Float.parseFloat(temp);
                            float SensorRh=Float.parseFloat(rh);
                            long TimeStamp = Long.parseLong(timestamp);

                            co2Data.add(new Entry(TimeStamp, SensorCo2));
                            o2Data.add(new Entry(TimeStamp, SensorO2));
                            tvocData.add(new Entry(TimeStamp, SensorTvoc));
                            pmData.add(new Entry(TimeStamp, SensorPm));
                            tempData.add(new Entry(TimeStamp,SensorTemp));
                            rhData.add(new Entry(TimeStamp, SensorRh));
                            i=0;

                        }
                        i=i+1;
                    }
                }
                final LineDataSet lineDataSet1= new LineDataSet(co2Data, "CO2");
                final LineDataSet lineDataSet2= new LineDataSet(o2Data,"O2");
                final LineDataSet lineDataSet3= new LineDataSet(tvocData, "TVOC");
                final LineDataSet lineDataSet4= new LineDataSet(pmData, "PM2.5");
                final LineDataSet lineDataSet5= new LineDataSet(tempData,"Temperature");
                final LineDataSet lineDataSet6= new LineDataSet(rhData, "Relative Humidity");

                lineDataSet1.setColor(Color.parseColor("#0071bc"));
                lineDataSet1.setDrawCircles(true);
                lineDataSet1.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet1.setDrawCircleHole(true);
                lineDataSet1.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet1.setDrawValues(false);
                lineDataSet1.setDrawFilled(false);
                lineDataSet1.setCircleRadius(3f);
                lineDataSet1.setLineWidth(3f);
                lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
                lineDataSet1.setHighlightEnabled(true);


                lineDataSet2.setColor(Color.parseColor("#0071bc"));
                lineDataSet2.setDrawCircles(true);
                lineDataSet2.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet2.setDrawCircleHole(true);
                lineDataSet2.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setDrawFilled(false);
                lineDataSet2.setCircleRadius(3f);
                lineDataSet2.setLineWidth(3f);
                lineDataSet2.setMode(LineDataSet.Mode.LINEAR);


                lineDataSet3.setColor(Color.parseColor("#0071bc"));
                lineDataSet3.setDrawCircles(true);
                lineDataSet3.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet3.setDrawCircleHole(true);
                lineDataSet3.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet3.setDrawValues(false);
                lineDataSet3.setDrawFilled(false);
                lineDataSet3.setCircleRadius(3f);
                lineDataSet3.setLineWidth(3f);
                lineDataSet3.setMode(LineDataSet.Mode.CUBIC_BEZIER);


                lineDataSet4.setColor(Color.parseColor("#0071bc"));
                lineDataSet4.setDrawCircles(true);
                lineDataSet4.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet4.setDrawCircleHole(true);
                lineDataSet4.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet4.setDrawValues(false);
                lineDataSet4.setDrawFilled(false);
                lineDataSet4.setCircleRadius(3f);
                lineDataSet4.setLineWidth(3f);
                lineDataSet4.setMode(LineDataSet.Mode.LINEAR);


                lineDataSet5.setColor(Color.parseColor("#0071bc"));
                lineDataSet5.setDrawCircles(true);
                lineDataSet5.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet5.setDrawCircleHole(true);
                lineDataSet5.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet5.setDrawValues(false);
                lineDataSet5.setDrawFilled(false);
                lineDataSet5.setCircleRadius(3f);
                lineDataSet5.setLineWidth(3f);
                lineDataSet5.setMode(LineDataSet.Mode.LINEAR);


                lineDataSet6.setColor(Color.parseColor("#0071bc"));
                lineDataSet6.setDrawCircles(true);
                lineDataSet6.setCircleColor(Color.parseColor("#0071bc"));
                lineDataSet6.setDrawCircleHole(true);
                lineDataSet6.setCircleHoleColor(Color.parseColor("#0071bc"));
                lineDataSet6.setDrawValues(false);
                lineDataSet6.setDrawFilled(false);
                lineDataSet6.setCircleRadius(3f);
                lineDataSet6.setLineWidth(3f);
                lineDataSet6.setMode(LineDataSet.Mode.LINEAR);


                ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
                ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
                ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
                ArrayList<ILineDataSet> dataSets4 = new ArrayList<>();
                ArrayList<ILineDataSet> dataSets5 = new ArrayList<>();
                ArrayList<ILineDataSet> dataSets6 = new ArrayList<>();


                dataSets1.add(lineDataSet1);
                dataSets2.add(lineDataSet2);
                dataSets3.add(lineDataSet3);
                dataSets4.add(lineDataSet4);
                dataSets5.add(lineDataSet5);
                dataSets6.add(lineDataSet6);

                XAxis xAxis1 = lineChart1.getXAxis();
                xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis1.enableGridDashedLine(10f, 10f, 0f);
                xAxis1.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft1 = lineChart1.getAxisLeft();
                YAxis yAxisRight1 = lineChart1.getAxisRight();
                yAxisLeft1.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight1.enableGridDashedLine(10f, 10f, 0f);

//                LimitLine limit1 = new LimitLine(30f, "Limit");
//                limit1.setLineColor(getResources().getColor(R.color.colorLimit));
//                limit1.setLineWidth(2f);
//                limit1.enableDashedLine(10f, 10f, 0f);
//                limit1.setTextColor(Color.BLACK);
//                limit1.setTextSize(12f);

//                yAxisLeft1.addLimitLine(limit1);


                XAxis xAxis2 = lineChart2.getXAxis();
                xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis2.enableGridDashedLine(10f, 10f, 0f);
                xAxis2.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft2 = lineChart2.getAxisLeft();
                YAxis yAxisRight2 = lineChart2.getAxisRight();
                yAxisLeft2.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight2.enableGridDashedLine(10f, 10f, 0f);

                XAxis xAxis3 = lineChart3.getXAxis();
                xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis3.enableGridDashedLine(10f, 10f, 0f);
                xAxis3.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft3 = lineChart3.getAxisLeft();
                YAxis yAxisRight3 = lineChart3.getAxisRight();
                yAxisLeft3.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight3.enableGridDashedLine(10f, 10f, 0f);

                XAxis xAxis4 = lineChart4.getXAxis();
                xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis4.enableGridDashedLine(10f, 10f, 0f);
                xAxis4.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft4 = lineChart4.getAxisLeft();
                YAxis yAxisRight4 = lineChart4.getAxisRight();
                yAxisLeft4.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight4.enableGridDashedLine(10f, 10f, 0f);

                XAxis xAxis5 = lineChart5.getXAxis();
                xAxis5.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis5.enableGridDashedLine(10f, 10f, 0f);
                xAxis5.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft5 = lineChart5.getAxisLeft();
                YAxis yAxisRight5 = lineChart5.getAxisRight();
                yAxisLeft5.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight5.enableGridDashedLine(10f, 10f, 0f);

                XAxis xAxis6 = lineChart6.getXAxis();
                xAxis6.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis6.enableGridDashedLine(10f, 10f, 0f);
                xAxis6.setValueFormatter(new TheFormatYouWant());

                YAxis yAxisLeft6 = lineChart6.getAxisLeft();
                YAxis yAxisRight6 = lineChart6.getAxisRight();
                yAxisLeft6.enableGridDashedLine(10f, 10f, 0f);
                yAxisRight6.enableGridDashedLine(10f, 10f, 0f);


                //process data view
                LineData data1 = new LineData(dataSets1);
                LineData data2 = new LineData(dataSets2);
                LineData data3 = new LineData(dataSets3);
                LineData data4 = new LineData(dataSets4);
                LineData data5 = new LineData(dataSets5);
                LineData data6 = new LineData(dataSets6);


                lineChart1.setData(data1);
                lineChart1.notifyDataSetChanged();
                lineChart1.invalidate();
                lineChart1.setVisibleXRangeMaximum(10800000);
                lineChart1.moveViewToX(data1.getXMax());



                lineChart2.setData(data2);
                lineChart2.notifyDataSetChanged();
                lineChart2.invalidate();
                lineChart2.setVisibleXRangeMaximum(10800000);
                lineChart2.moveViewToX(data1.getXMax());

                lineChart3.setData(data3);
                lineChart3.notifyDataSetChanged();
                lineChart3.invalidate();
                lineChart3.setVisibleXRangeMaximum(10800000);
                lineChart3.moveViewToX(data3.getXMax());

                lineChart4.setData(data4);
                lineChart4.notifyDataSetChanged();
                lineChart4.invalidate();
                lineChart4.setVisibleXRangeMaximum(10800000);
                lineChart4.moveViewToX(data3.getXMax());

                lineChart5.setData(data5);
                lineChart5.notifyDataSetChanged();
                lineChart5.invalidate();
                lineChart5.setVisibleXRangeMaximum(10800000);
                lineChart5.moveViewToX(data3.getXMax());


                lineChart6.setData(data6);
                lineChart6.notifyDataSetChanged();
                lineChart6.invalidate();
                lineChart6.setVisibleXRangeMaximum(10800000);
                lineChart6.moveViewToX(data3.getXMax());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public class TheFormatYouWant extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {

            Date date = new Date((long) value);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            return sdf.format(date);
        }
    }

}
