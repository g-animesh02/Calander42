package com.example.calander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.calander.Main2Activity.EXTRA_MESSAGE2;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.calander.extra.MESSAGE";
    CalendarView calendarView;
    FloatingActionButton fab;
    String selectedDate;
    private static Boolean RUN_ONCE = true;
    TextView t, d, e;
    String eventData, eventType;
    mySQLiteDBHandler mydb;


    String[] m = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    Main2Activity main2Activity = new Main2Activity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String date = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault()).format(new Date());
        d = findViewById(R.id.Date);
        d.setText(date);
        calendarView = findViewById(R.id.date_picker);
        e = findViewById(R.id.event_display);
        t = findViewById(R.id.typ);
        mydb = new mySQLiteDBHandler(this);
        selectedDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);

                d.setText(Integer.toString(dayOfMonth) + " - " + m[month - 1] + " - " + year);
                if (!RUN_ONCE) {
                    String final_value = mydb.GetData(selectedDate);
                    int ind = final_value.indexOf(",");
                    e.setText(final_value.substring(0, ind));
                    t.setText(final_value.substring(ind + 1, final_value.length()));
                }

            }
        });
        runOnce();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra(EXTRA_MESSAGE, selectedDate);
                startActivity(i);

            }
        });
    }

    private void runOnce() {
        if (RUN_ONCE) {
            RUN_ONCE = false;

            // do something
        }


    }
}
