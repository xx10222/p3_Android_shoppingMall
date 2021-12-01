package com.example.project3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//글작성을 위한 액티비티
public class write extends AppCompatActivity {
    DatabaseWrite helper;
    SQLiteDatabase database;
    int version=1;
    Button rw, rb;
    EditText title;
    EditText quantity;
    EditText price;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);
        setTitle("WRITE");

        rw=(Button)findViewById(R.id.register_write);
        rb=(Button)findViewById(R.id.register_back);
        title = (EditText)findViewById(R.id.register_title);
        quantity = (EditText)findViewById(R.id.register_quantity);
        price = (EditText)findViewById(R.id.register_price);
        content = (EditText)findViewById(R.id.register_content);
        helper = new DatabaseWrite(write.this, null, version);
        database = helper.getWritableDatabase();
        rw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String result;
                String stitle = title.getText().toString();
                String squantity = quantity.getText().toString();
                String sprice = price.getText().toString();
                String scontent = content.getText().toString();
                if (stitle.equals("") || squantity.equals("") || sprice.equals("") || scontent.equals("")) { //글을 모두 입력하지 않은 경우
                    result = "정보를 모두 입력하세요";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    return;
                }
                String swriter=((USER)getApplicationContext()).getId(); //현재 접속한 사용자의 아이디를 작성자로 등록한다
                helper.insertUser(database, stitle, scontent,swriter, Integer.parseInt(sprice),Integer.parseInt(squantity));
                result = "글작성 성공!";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //메인화면으로 돌아간다
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}