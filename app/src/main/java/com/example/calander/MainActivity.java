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

    public static final String EXTRA_MESSAGE ="com.example.calander.extra.MESSAGE";
    CalendarView calendarView;
    FloatingActionButton fab;
    String selectedDate;
    TextView t,d,e;
    String eventData,eventType;
    String message;
    int c = 0;
    String[] m = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
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
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);

                d.setText(Integer.toString(dayOfMonth)+" - "+m[month-1]+" - "+year);



            }
        });



        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra(EXTRA_MESSAGE, selectedDate);
                startActivity(i);

            }
        });
    }
    public void act(View v , String select){
        main2Activity.InsertDB(select);
        Intent jj = getIntent();
        eventData = jj.getStringExtra(Main2Activity.EXTRA_MESSAGE2);
        eventType = jj.getStringExtra(Main2Activity.EXTRA_MESSAGE3);
        e.setText(selectedDate);
        t.setText(eventType);
    }



}
