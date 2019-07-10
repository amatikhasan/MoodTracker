package com.user.app.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.user.app.R;
import com.user.app.adapter.JournalAdapter;
import com.user.app.db.DBHelper;
import com.user.app.model.JournalModel;

public class MyJournal extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<JournalModel> journalList = new ArrayList<>();
    TextView emptyList;
    Toolbar toolbar;
    Bundle extras;
    String month,monthShort;


    JournalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_journal);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            month = extras.getString("month");
            monthShort = extras.getString("monthShort");
            String title="Journals in "+month;
            getSupportActionBar().setTitle(title);

        }

        emptyList=findViewById(R.id.emptyList);
        recyclerView=findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the calander
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date now = c.getTime();
        String year = sdf.format(now);
        String monthYear=monthShort+" "+year;


        DBHelper dbHelper = new DBHelper(getApplicationContext());
        journalList = dbHelper.getJournalByMonth(monthYear);
//        journalList=dbHelper.showJournalList();

        Log.d("check", "journalList: "+journalList.size());


        if(journalList.size()>0) {
            adapter = new JournalAdapter(this, journalList);
            recyclerView.setAdapter(adapter);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        }
    }



    //For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        MenuItem item=menu.findItem(R.id.menuFilter);

        SearchView searchView= (SearchView) item.getActionView();
        searchView.setQueryHint("Search Journal");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//              if (obj.size()>0)

                try {
                    adapter.filter(newText);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for toolbar arrow
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, MonthView.class));
        finish();
    }
}
