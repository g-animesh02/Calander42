package com.example.calander;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editText;
    String selectedDate;
    String SQLiteDataBaseQueryHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    public static final String EXTRA_MESSAGE2 ="com.example.calander.extra.MESSAGE";
    public static final String EXTRA_MESSAGE4 ="com.example.calander.extra.MESSAGE";
    public static final String EXTRA_MESSAGE3 ="com.example.calander.extra.MESSAGE";
    String[] type = { "Event", "Anniversary", "Birthday"};
    String s="", typo = type[0];
    mySQLiteDBHandler mydbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(aa);

        editText = findViewById(R.id.edittext);
        Intent intent3 = getIntent();
        selectedDate = intent3.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mydbHandler = new mySQLiteDBHandler(this);

        Button button = findViewById(R.id.save);

    }
    public void InsertDataIntoSQLiteDatabase(View v){
        s = editText.getText().toString();

        if(s == null || s.equals(""))
        {

            Toast.makeText(Main2Activity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();


        }
        else {

            Boolean t = mydbHandler.insertData(s,selectedDate,typo);
            if(t) {
                Toast.makeText(Main2Activity.this, "Event added successfully", Toast.LENGTH_LONG).show();
                Intent i =new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(Main2Activity.this, "Failed to add Event", Toast.LENGTH_LONG).show();

            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),type[position] , Toast.LENGTH_SHORT).show();
        typo = type[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


