package com.user.app.view;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.github.mikephil.charting.charts.PieChart;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.user.app.R;
import com.user.app.db.DBHelper;
import com.user.app.model.JournalModel;
import com.user.app.model.MoodModel;
import im.dacer.androidcharts.LineView;
import lecho.lib.hellocharts.model.LineChartData;

public class MoodTracker extends AppCompatActivity {
    Toolbar toolbar;
    private ArrayList<MoodModel> moodData;

    DBHelper dbHelper;

    TextView tvMonth;
    TextView tvEmptyPie;
    TextView tvEmptyLine;
    TextView tvEmptyMood;
    TextView tvMood;
    ImageView ivMood;

    RelativeLayout rlMood;

    ArrayList<String> dateList;
    ArrayList<Integer> valueList;

    String mMonth;

    private PieChart pieChart;
    private LineChartView mChart;
    private Tooltip mTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        moodData=new ArrayList<>();


        dateList = new ArrayList<>();
        valueList = new ArrayList<>();

        tvEmptyPie=findViewById(R.id.emptyPie);
        tvEmptyLine=findViewById(R.id.emptyLine);
        tvMonth =findViewById(R.id.month);
        mChart =  findViewById(R.id.lineChart);
        pieChart = findViewById(R.id.pieChart);


        tvMood = findViewById(R.id.tvMood);
        ivMood = findViewById(R.id.ivMood);
        tvEmptyMood = findViewById(R.id.emptyMood);
        rlMood = findViewById(R.id.rlMood);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mood Tracker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        Date now = calendar.getTime();
        mMonth = sdf.format(now);

        getData();

    }

    public void getData(){


        dbHelper=new DBHelper(getApplicationContext());
        moodData=dbHelper.getMoodInMonth(mMonth);
        tvMonth.setText(mMonth);


        if (moodData.size()>0) {


            tvEmptyLine.setVisibility(View.GONE);
            tvEmptyMood.setVisibility(View.GONE);
            tvEmptyPie.setVisibility(View.GONE);

            mChart.setVisibility(View.VISIBLE);
            rlMood.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.VISIBLE);



            getAverageMood();
            setPieChart();
            showLineChart();
        }
        else {

            tvEmptyLine.setVisibility(View.VISIBLE);
            tvEmptyMood.setVisibility(View.VISIBLE);
            tvEmptyPie.setVisibility(View.GONE);

            mChart.setVisibility(View.GONE);
            rlMood.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);

        }
    }

    public void getMonth(View view){

        int yearSelected,monthSelected;
        final Calendar calendar=Calendar.getInstance();
        yearSelected=calendar.get(Calendar.YEAR);
        monthSelected=calendar.get(Calendar.MONTH);


        MonthYearPickerDialogFragment dialogFragment=MonthYearPickerDialogFragment.getInstance(monthSelected,yearSelected);
        dialogFragment.show(getSupportFragmentManager(),null);
        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int month) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);

                SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
                Date date = calendar.getTime();
                mMonth=sdf.format(date);

                Log.d("check", "onDateSet: "+month+" "+year+" "+mMonth);

                getData();
            }
        });

    }

    public void getAverageMood(){
        int totalMoodValue=0;
        int moodCount=0;
        int avgValue;

        if (dbHelper.getMoodCount("Very Happy",mMonth)>0){
            int count=dbHelper.getMoodCount("Very Happy",mMonth);
            moodCount+=count;
            totalMoodValue+=count*50;
        }
        if (dbHelper.getMoodCount("Happy",mMonth)>0){
            int count=dbHelper.getMoodCount("Happy",mMonth);
            moodCount+=count;
            totalMoodValue+=count*40;
        }
        if (dbHelper.getMoodCount("Neutral",mMonth)>0){
            int count=dbHelper.getMoodCount("Neutral",mMonth);
            moodCount+=count;
            totalMoodValue+=count*30;
        }
        if (dbHelper.getMoodCount("Sad",mMonth)>0){
            int count=dbHelper.getMoodCount("Sad",mMonth);
            moodCount+=count;
            totalMoodValue+=count*20;
        }
        if (dbHelper.getMoodCount("Depressed",mMonth)>0){
            int count=dbHelper.getMoodCount("Depressed",mMonth);
            moodCount+=count;
            totalMoodValue+=count*10;
        }

        avgValue=totalMoodValue/moodCount;

        Log.d("check", "average: "+totalMoodValue+" "+moodCount+" "+avgValue);

        if (avgValue<=10){
            ivMood.setImageDrawable(getResources().getDrawable(R.drawable.depressed));
            tvMood.setText("Depressed");
            tvMood.setTextColor(getResources().getColor(R.color.depressed));
        }
        if (11<=avgValue&&avgValue<=20){
            ivMood.setImageDrawable(getResources().getDrawable(R.drawable.sad));
            tvMood.setText("Sad");
            tvMood.setTextColor(getResources().getColor(R.color.sad));
        }
        if (21<=avgValue&&avgValue<=30){
            ivMood.setImageDrawable(getResources().getDrawable(R.drawable.neutral));
            tvMood.setText("Neutral");
            tvMood.setTextColor(getResources().getColor(R.color.neutral));
        }
        if (31<=avgValue&&avgValue<=40){
            ivMood.setImageDrawable(getResources().getDrawable(R.drawable.happy));
            tvMood.setText("Happy");
            tvMood.setTextColor(getResources().getColor(R.color.happy));
        }
        if (41<=avgValue&&avgValue<=50){
            ivMood.setImageDrawable(getResources().getDrawable(R.drawable.very_happy));
            tvMood.setText("Very Happy");
            tvMood.setTextColor(getResources().getColor(R.color.very_happy));
        }

    }

    public void setPieChart(){

        pieChart.setUsePercentValues(true);

        ArrayList<Entry> entries=new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> labels=new ArrayList<>();
        ArrayList<MoodModel> values = new ArrayList<>();

        values.add(new MoodModel("Very Happy", dbHelper.moodCount("Very Happy", mMonth)));
        values.add(new MoodModel("Happy", dbHelper.moodCount("Happy", mMonth)));
        values.add(new MoodModel("Neutral", dbHelper.moodCount("Neutral", mMonth)));
        values.add(new MoodModel("Sad", dbHelper.moodCount("Sad", mMonth)));
        values.add(new MoodModel("Depressed", dbHelper.moodCount("Depressed", mMonth)));

        int j=0;
        for (int i=0;i<values.size();i++){
            if (values.get(i).getMoodValue()>0){
                entries.add(new Entry(values.get(i).getMoodValue(),j));
                labels.add(values.get(i).getMood());

                if (values.get(i).getMood().equals("Very Happy"))
                    colors.add(getResources().getColor(R.color.very_happy));
                if (values.get(i).getMood().equals("Happy"))
                    colors.add(getResources().getColor(R.color.happy));
                if (values.get(i).getMood().equals("Neutral"))
                    colors.add(getResources().getColor(R.color.neutral));
                if (values.get(i).getMood().equals("Sad"))
                    colors.add(getResources().getColor(R.color.sad));
                if (values.get(i).getMood().equals("Depressed"))
                    colors.add(getResources().getColor(R.color.depressed));


                j++;
            }
        }

        PieDataSet dataSet=new PieDataSet(entries,"");
        dataSet.setColors(colors);
        PieData pieData=new PieData(labels,dataSet);
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setData(pieData);
        pieChart.setDescription("");

        pieChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);

    }


    public void showLineChart() {


        // Tooltip
        mTip = new Tooltip(this, R.layout.tool_tip, R.id.value);

//        ((TextView) mTip.findViewById(R.id.value)).setTypeface(
//                Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        String[] labels = new String[moodData.size()];
        final float[] values= new float[moodData.size()];

        for (int i = 0; i < moodData.size(); i++) {
            String[] parts= moodData.get(i).getDate().split(" ");
            labels[i]=(String.valueOf(parts[0]));
        }


        for (int i = 0; i < moodData.size(); i++) {

            values[i]=(float) moodData.get(i).getMoodValue();
        }


        // Data
        LineSet dataset = new LineSet(labels, values);
        dataset.setColor(Color.parseColor("#758cbb"))
//                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[]{10f, 10f});
        mChart.addData(dataset);

        dataset = new LineSet(labels, values);
        dataset.setColor(Color.parseColor("#b3b5bb"))
//                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4);
        mChart.addData(dataset);

////        mBaseAction = action;
//        Runnable chartAction = new Runnable() {
//            @Override
//            public void run() {
//
////                mBaseAction.run();
//                mTip.prepare(mChart.getEntriesArea(0).get(3), values[3]);
//                mChart.showTooltip(mTip, true);
//            }
//        };

        mChart.setAxisBorderValues(0, 100)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setTooltips(mTip)
                .show();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for toolbar arrow
            case android.R.id.home:
//                startActivity(new Intent(getApplicationContext(), MoodList.class));
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(getApplicationContext(), MoodList.class));
        finish();
    }
}