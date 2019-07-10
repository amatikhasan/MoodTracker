package com.user.app.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.user.app.R;
import com.user.app.adapter.MonthAdapter;
import com.user.app.db.DBHelper;
import com.user.app.model.MonthModel;
import com.zarinpal.libs.cardviwepager.CardViewPager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        CardViewPager viewPager = findViewById(R.id.card_viewpager);

        DBHelper database=new DBHelper(this);
        int janCount=database.journalCountInMonth("Jan 2019");
        int febCount=database.journalCountInMonth("Feb 2019");
        int marCount=database.journalCountInMonth("Mar 2019");
        int aprCount=database.journalCountInMonth("Apr 2019");
        int mayCount=database.journalCountInMonth("May 2019");
        int junCount=database.journalCountInMonth("Jun 2019");
        int julCount=database.journalCountInMonth("Jul 2019");
        int augCount=database.journalCountInMonth("Aug 2019");
        int sepCount=database.journalCountInMonth("Sep 2019");
        int octCount=database.journalCountInMonth("Oct 2019");
        int novCount=database.journalCountInMonth("Nov 2019");
        int decCount=database.journalCountInMonth("Dec 2019");

        MonthModel jan = new MonthModel("January","Jan", Color.parseColor("#1abc9c"),janCount);
        MonthModel feb = new MonthModel("February","Feb", Color.parseColor("#9b59b6"),febCount);
        MonthModel mar = new MonthModel("March","Mar", Color.parseColor("#e74c3c"),marCount);
        MonthModel apr = new MonthModel("April","Apr", Color.parseColor("#3498db"),aprCount);
        MonthModel may = new MonthModel("May","May", Color.parseColor("#2c3e50"),mayCount);
        MonthModel jun = new MonthModel("June","Jun", Color.parseColor("#1abc9c"),junCount);
        MonthModel jul = new MonthModel("July","Jul", Color.parseColor("#9b59b6"),julCount);
        MonthModel aug = new MonthModel("August","Aug", Color.parseColor("#e74c3c"),augCount);
        MonthModel sep = new MonthModel("September","Sep", Color.parseColor("#3498db"),sepCount);
        MonthModel oct = new MonthModel("October","Oct", Color.parseColor("#2c3e50"),octCount);
        MonthModel nov = new MonthModel("November","Nov", Color.parseColor("#1abc9c"),novCount);
        MonthModel dec = new MonthModel("December","Dec", Color.parseColor("#9b59b6"),decCount);

        MonthAdapter adapter = new MonthAdapter(this);
        adapter.addCardItem(jan);
        adapter.addCardItem(feb);
        adapter.addCardItem(mar);
        adapter.addCardItem(apr);
        adapter.addCardItem(may);
        adapter.addCardItem(jun);
        adapter.addCardItem(jul);
        adapter.addCardItem(aug);
        adapter.addCardItem(sep);
        adapter.addCardItem(oct);
        adapter.addCardItem(nov);
        adapter.addCardItem(dec);

        adapter.setElevation(0.6f);
        viewPager.setAdapter(adapter);
        viewPager.isShowShadowTransformer(true);

        // Get the calander
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        Date now = c.getTime();
        String month = sdf.format(now);

        if (month.equals("Jan")){
            viewPager.getViewPager().setCurrentItem(0);
        }
        if (month.equals("Feb")){
            viewPager.getViewPager().setCurrentItem(1);
        }
        if (month.equals("Mar")){
            viewPager.getViewPager().setCurrentItem(2);
        }
        if (month.equals("Apr")){
            viewPager.getViewPager().setCurrentItem(3);
        }
        if (month.equals("May")){
            viewPager.getViewPager().setCurrentItem(4);
        }
        if (month.equals("Jun")){
            viewPager.getViewPager().setCurrentItem(5);
        }
        if (month.equals("Jul")){
            viewPager.getViewPager().setCurrentItem(6);
        }
        if (month.equals("Aug")){
            viewPager.getViewPager().setCurrentItem(7);
        }
        if (month.equals("Sep")){
            viewPager.getViewPager().setCurrentItem(8);
        }
        if (month.equals("Oct")){
            viewPager.getViewPager().setCurrentItem(9);
        }
        if (month.equals("Nov")){
            viewPager.getViewPager().setCurrentItem(10);
        }
        if (month.equals("Dec")){
            viewPager.getViewPager().setCurrentItem(11);
        }





    }

    public void createJournal(View view){
        Intent intent=new Intent(this, CreateJournal.class);
        startActivity(intent);
//        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(this, MonthView.class));
//            finish();
        }

        if (id == R.id.menu_Mood_list) {
            startActivity(new Intent(this, MoodList.class));
//            finish();
        }
        if (id == R.id.menu_tracker) {
            startActivity(new Intent(this, MoodTracker.class));
//            finish();
            drawerLayout.closeDrawer(Gravity.START);
        }

        return false;
    }


    @Override
    public void onBackPressed() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit this app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

                        finish();
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }


}