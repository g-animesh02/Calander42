package com.example.calander;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText editText;
    private SQLiteDatabase sqLiteDatabase;
    private String selectedDate;
    public static final String EXTRA_MESSAGE2 ="com.example.calander.extra.MESSAGE";
    public static final String EXTRA_MESSAGE3 ="com.example.calander.extra.MESSAGE";
    String[] type = { "Event", "Anniversary", "Birthday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(aa);

        editText = findViewById(R.id.edittext);
        mySQLiteDBHandler dbHandler = new mySQLiteDBHandler(this, "CalenderDataBase", null, 1);
        try {
     sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalender(Date TEXT, Event TEXT , Type TEXT)");
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent3 = getIntent();
        selectedDate = intent3.getStringExtra(MainActivity.EXTRA_MESSAGE);



    }
    public void InsertDB(View v) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", selectedDate);
        String s = editText.getText().toString();
        contentValues.put("Event", s);
        sqLiteDatabase.insert("EventCalender", null, contentValues);
        sqLiteDatabase.close();
        Intent y = new Intent(Main2Activity.this,MainActivity.class);
        startActivity(y);


    }


    public void readDataBase() {
        String str,typ;

        Cursor cursor , cursor1;
        String query = "SELECT Event FROM EventCalender WHERE Date =" + selectedDate;
        String query2 = "SELECT Type FROM EventCalender WHERE Date =" + selectedDate;
        cursor = sqLiteDatabase.rawQuery(query,null);
        cursor1 = sqLiteDatabase.rawQuery(query2,null);
        try {
            cursor.moveToFirst();
            cursor1.moveToFirst();
            str = (cursor.getString(0));
            typ =  cursor1.getString(0);
        } catch (Exception e) {
            e.printStackTrace();
            str = "No Event Entered";
            typ = "No Type";
        }
        Intent intent1 = new Intent(Main2Activity.this,MainActivity.class);
        intent1.putExtra(EXTRA_MESSAGE2,str);
        intent1.putExtra(EXTRA_MESSAGE3,typ);
        cursor.close();
        cursor1.close();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),type[position] , Toast.LENGTH_LONG).show();
        if (type[position].equals("Event")){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


