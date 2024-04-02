package com.example.btlsqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.content.Context;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //khai bao bien
    EditText edtmasv,edttensv,edtgioitinh,edtquequan;
    Button btninsert,btndelete,btnupdate,btnsearch;
    String DB_PATH_SUFFIX = "/database/";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "db.db";
    //khai bao liv
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    SQLiteDatabase databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtmasv = findViewById(R.id.edtmalop);
        edttensv = findViewById(R.id.edttensv);
        edtgioitinh = findViewById(R.id.edtgioitinh);
        edtquequan = findViewById(R.id.edtquequan);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnsearch  = findViewById(R.id.btnsearch);

        lv = findViewById(R.id.lv);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        databases = openOrCreateDatabase("db.db",MODE_PRIVATE,null);
        try{

            String sql = "CREATE TABLE QLSV(masv TEXT primary key,tensv TEXT,gioitinh TEXT,quequan TEXT)";
            databases.execSQL(sql);
        }
        catch (Exception e)
        {
            Log.e("Error","table đã tồn tại");
        }
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masv = edtmasv.getText().toString();
                String tensv = edttensv.getText().toString();
                String gioitinh = edtgioitinh.getText().toString();
                String quequan = edtquequan.getText().toString();
                ContentValues values = new ContentValues();
                values.put("masv",masv);
                values.put("tensv",tensv);
                values.put("quequan",quequan);
                values.put("gioitinh",gioitinh);
                String msg ="";
                if(databases.insert("QLSV",null,values)==-1)
                {
                    msg ="khoong theem ddc ";

                }
                else {
                  msg ="them thanh cong";
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtmasv.getText().toString();
                int n = databases.delete("QLSV","masv = ? ",new String[]{malop});
                String msg = "";
                if(n==0)
                {
                    msg = "khong xoa dc";

                }
                else {
                    msg = n +"duoc xoa";

                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensv = edttensv.getText().toString();
                String malop = edtmasv.getText().toString();
                ContentValues values = new ContentValues();
                values.put("tensv",tensv);
                int n = databases.update("QLSV",values ,"masv =?",new String[]{malop});
                String msg ="";
                if(n==0)
                {
                    msg="cap nhat k thanh cong";
                }
                else{
                    msg="cap nhat thanh cong";
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                Cursor c = databases.query("QLSV",null,null,null,null,null,null,null);
                c.moveToNext();
                String data ="";
                while(c.isAfterLast()==false){
                    data = c.getString(0)+"-"+c.getString(1)+"-"+c.getString(2)+"-"+c.getString(3);
                    c.moveToNext();
                    list.add(data);
                }
                c.close();
                adapter.notifyDataSetChanged();
            }
        });
    }
}