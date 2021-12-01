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

//회원가입 화면 액티비티
public class join extends AppCompatActivity {
    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    Button registerBtn;
    EditText idet, pwet, nmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        setTitle("JOIN");

        registerBtn = (Button)findViewById(R.id.register_btn);
        idet = (EditText)findViewById(R.id.register_id);
        pwet = (EditText)findViewById(R.id.register_pw);
        nmet = (EditText)findViewById(R.id.register_name);
        helper = new DatabaseOpenHelper(join.this, null, version);
        database = helper.getWritableDatabase();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String result;
                String id = idet.getText().toString();
                String pw = pwet.getText().toString();
                String name = nmet.getText().toString();
                if (id.equals("") || pw.equals("") || name.equals("")) { //정보를 모두 입력하지 않은 경우
                    result = "정보를 모두 입력하세요";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    return;
                }
                sql = "SELECT id FROM " + helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);
                if (cursor.getCount() != 0) { //이미 존재하는 아이디인 경우
                    result = "이미 존재하는 아이디입니다";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    return;
                }
                helper.insertUser(database, name, id, pw); //회원 정보 테이블에 입력받은 정보를 저장한다
                result = "회원가입 성공!";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}