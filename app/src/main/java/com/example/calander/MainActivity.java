package com.example.calander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.calander.Main2Activity.EXTRA_MESSAGE2;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.calander.extra.MESSAGE";
    public static final String EXTRA_MESSAGE0 = "com.example.calander.extra.MESSAGE";
    CalendarView calendarView;
    FloatingActionButton fab , fab2;
    String selectedDate;
    private static Boolean RUN_ONCE = true;
    TextView t, d, e ,t1,t2,t3;
    String final_value , desc , ev , ty;
    mySQLiteDBHandler mydb;
    String dark_light = "";


    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
            dark_light = "dark";
        } else {
            setTheme(R.style.LightTheme);
            dark_light="light";
        }
        setContentView(R.layout.activity_main);


        String date = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault()).format(new Date());

        d = findViewById(R.id.Date);
        d.setText(date);
        calendarView = findViewById(R.id.date_picker);
        e = findViewById(R.id.event_display);
        t1 = (TextView)findViewById(R.id.type);
        t2 = (TextView)findViewById(R.id.eventss);
        t3 = (TextView)findViewById(R.id.descri);
        final ViewGroup c;
        t = findViewById(R.id.typ);
        mydb = new mySQLiteDBHandler(this);

        selectedDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        final_value = mydb.GetData(selectedDate);
        desc = mydb.GetDescription(selectedDate);
        int ind = final_value.indexOf(",");
        ev = final_value.substring(0, ind);
        ty = final_value.substring(ind + 1, final_value.length());
        e.setText(ev);
        t.setText(ty);


        View bottomsheet = findViewById(R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setHideable(true); //Important to add
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN); //Important to add

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);

                d.setText(Integer.toString(dayOfMonth) + " - " + m[month] + " - " + year);
                if (!RUN_ONCE) {
                    final_value = mydb.GetData(selectedDate);
                    desc = mydb.GetDescription(selectedDate);
                    int ind = final_value.indexOf(",");
                    ev = final_value.substring(0, ind);
                    ty = final_value.substring(ind + 1, final_value.length());
                    e.setText(ev);
                    t.setText(ty);
                }

            }
        });
        runOnce();

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_DRAGGING");
                        t1.setText(ty);
                        t2.setText(ev);
                        t3.setText("");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_SETTLING");
                        t1.setText(ty);
                        t2.setText(ev);
                        t3.setText("");

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_EXPANDED");
                        t3.setText(desc);
                        t1.setText(ty);
                        t2.setText(ev);
                        t3.setText(desc);

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_COLLAPSED");
                        t1.setText(ty);
                        t2.setText(ev);
                        t3.setText("");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback","BottomSheetBehavior.STATE_HIDDEN");
                        t1.setText(ty);
                        t2.setText(ev);
                        t3.setText("");
                        break;

                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback","slideOffset: " + slideOffset);

            }
        });





        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra(EXTRA_MESSAGE, selectedDate);
                i.putExtra(EXTRA_MESSAGE0,dark_light);
                startActivity(i);

            }
        });
        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else{
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Dark_Mode:

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.this.getClass()));


                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void runOnce() {
        if (RUN_ONCE) {
            RUN_ONCE = false;

            // do something
        }


    }

}
