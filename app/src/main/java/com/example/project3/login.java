package com.example.project3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//로그인 화면 액티비티
public class login extends AppCompatActivity {
    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    Button registerBtn, registerjoin;
    EditText idet, pwet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("LOGIN");

        registerBtn = (Button)findViewById(R.id.register_btn);
        registerjoin = (Button)findViewById(R.id.register_join);
        idet = (EditText)findViewById(R.id.register_id);
        pwet = (EditText)findViewById(R.id.register_pw);
        helper = new DatabaseOpenHelper(login.this, null, version);
        database = helper.getWritableDatabase();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String result;
                String id = idet.getText().toString();
                String pw = pwet.getText().toString();
                if(id.equals("")||pw.equals("")) //아이디 또는 비밀번호를 모두 입력하지 않은 경우
                {
                    result="아이디와 비밀번호를 모두 입력하세요";
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                    return;
                }
                sql="SELECT id FROM "+ helper.tableName + " WHERE id = '" + id +"'";
                cursor=database.rawQuery(sql,null);
                if(cursor.getCount()!=1) //존재하지 않는 아이디
                {
                    result="존재하지 않는 아이디입니다";
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                    return;
                }
                sql = "SELECT pw FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);
                cursor.moveToNext();
                if(!pw.equals(cursor.getString(0))) //비밀번호가 일치하지 않는 경우
                {
                    result="일치하지 않는 비밀번호입니다";
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                    cursor.close();
                    return;
                }
                else
                {
                    result="로그인 성공!";
                    ((USER)getApplicationContext()).setId(id); //로그인한 사용자의 아이디를 저장한다
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Home.class); //메인화면으로 넘어간다
                    startActivity(intent);
                    finish();
                    cursor.close();
                }
            }
        });

        registerjoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //회원가입 화면으로 넘어가는 버튼
                Intent intent = new Intent(getApplicationContext(), join.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

