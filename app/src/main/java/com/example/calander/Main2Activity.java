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
        Intent ck = new Intent(Main2Activity.EXTRA_MESSAGE4);
        ck.putExtra(EXTRA_MESSAGE4,ck);

        Button button = findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SQLiteTableBuild();

                InsertDataIntoSQLiteDatabase();

                Intent i = new Intent(Main2Activity.this,MainActivity.class);

                startActivity(i);


            }
        });




    }



    public void SQLiteTableBuild(){

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS EventCalenders (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Event TEXT, Date TEXT, Type TEXT);");

    }


    public void InsertDataIntoSQLiteDatabase(){
        s = editText.getText().toString();

        if(s == null || s.equals(""))
        {

            Toast.makeText(Main2Activity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();


        }
        else {

            SQLiteDataBaseQueryHolder = "INSERT INTO EventCalenders (Event,Date,Type) VALUES('"+s+"', '"+selectedDate+"', '"+typo+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            Toast.makeText(Main2Activity.this,"Event added successfully", Toast.LENGTH_LONG).show();

        }

    }

    public void InsertDB(String selectedDatee) {




        SQLiteTableBuild();

        InsertDataIntoSQLiteDatabase();

        String str ,typ;

        Cursor cursor , cursor1;

        String query = "SELECT Event FROM EventCalenders WHERE Date =" + selectedDatee;
        String query2 = "SELECT Type FROM EventCalenders WHERE Date =" + selectedDatee;

        cursor = sqLiteDatabaseObj.rawQuery(query,null);
        cursor1 = sqLiteDatabaseObj.rawQuery(query2,null);


        try {
            cursor.moveToFirst();
            cursor1.moveToFirst();

            str = cursor.getString(cursor.getColumnIndex("Event"));

            typ = cursor1.getString(cursor1.getColumnIndex("Type"));


        }
            catch (Exception e) {
            e.printStackTrace();
            str = "No Event Entered";
            typ = "";

        }

       Intent kk = new Intent(Main2Activity.this,MainActivity.class);

        kk.putExtra(EXTRA_MESSAGE2,str);
        kk.putExtra(EXTRA_MESSAGE3,typ);

        startActivity(kk);




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


