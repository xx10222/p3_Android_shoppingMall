package com.example.project3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//글 수정 화면 액티비티
public class updateView extends AppCompatActivity {
    DatabaseWrite helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    ListView listView;
    int nn;
    String tt;
    String cc;
    String ww;
    int s_cart;
    int s_hit;
    int qq;
    int pp;
    TextView tnum,twriter, tcart,thit;
    EditText etitle, econtent, equantity,eprice;

    //List<HashMap<String,String>> list = new ArrayList<>();
    Button ru, rb, rd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateview);
        setTitle("UPDATE");
        listView = (ListView)findViewById(R.id.list);

        ru=(Button)findViewById(R.id.register_update);
        rb=(Button)findViewById(R.id.register_back);
        rd=(Button)findViewById(R.id.register_delete);

        tnum = (TextView)findViewById(R.id.text1);
        twriter = (TextView)findViewById(R.id.text4);
        tcart = (TextView)findViewById(R.id.text5);
        thit = (TextView)findViewById(R.id.text6);

        etitle=(EditText)findViewById(R.id.text2);
        econtent=(EditText)findViewById(R.id.text3);
        equantity=(EditText)findViewById(R.id.text7);
        eprice=(EditText)findViewById(R.id.text8);

        helper = new DatabaseWrite(updateView.this, null, version);
        database = helper.getWritableDatabase();
        int product_num = ((USER)getApplicationContext()).getBno();
        sql="SELECT * FROM "+ helper.tableName + " where num=" + product_num; //저장된 글번호에 위치한 테이블의 정보를 얻어온다
        cursor=database.rawQuery(sql,null);

        if(cursor.moveToNext()){ /*
                    0:num
                    1:title
                    2:content
                    3:writer
                    4:cart
                    5:hit
                    6:quantity
                    7:price
        */
            do{
                nn=cursor.getInt(0);
                tt=cursor.getString(1);
                cc=cursor.getString(2);
                ww=cursor.getString(3);
                s_cart=cursor.getInt(4);
                s_hit=cursor.getInt(5);
                qq=cursor.getInt(6);
                pp=cursor.getInt(7);

            }while(cursor.moveToNext());
        }
        cursor.close();
        //위에서 받은 정보 출력
        tnum.setText(Integer.toString(nn));
        etitle.setText(tt);
        econtent.setText(cc);
        twriter.setText(ww);
        tcart.setText(Integer.toString(s_cart));
        thit.setText(Integer.toString(s_hit));
        equantity.setText(Integer.toString(qq));
        eprice.setText(Integer.toString(pp));


        ru.setOnClickListener(new View.OnClickListener() { //수정
            //@Override
            public void onClick(View view) {
                String up_title = etitle.getText().toString();
                String up_content = econtent.getText().toString();
                String s_quantity = equantity.getText().toString();
                String s_price = eprice.getText().toString();
                if(up_title.equals("")||up_content.equals("")||s_quantity.equals("")||s_price.equals("")) //아이디 또는 비밀번호를 모두 입력하지 않은 경우
                {
                    String result="내용을 모두 입력하세요";
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                    return;
                }
                int product_num=((USER)getApplicationContext()).getBno();
                int up_quantity = Integer.parseInt(s_quantity);
                int up_price = Integer.parseInt(s_price);
                //입력받은 정보로 수정하여 테이블에 저장한다
                sql="UPDATE "+ helper.tableName + " SET (title, content, quantity, price) = ('"+up_title+"','"+up_content+"','"+up_quantity+"','"+up_price+"')"
                        + " where num=" + product_num;
                database.execSQL(sql);
                String message ="수정완료!";
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), readView.class); //상세조회 페이지로 돌아간다
                startActivity(intent);
                finish();
            }
        });

        rb.setOnClickListener(new View.OnClickListener() { //돌아가기
            public void onClick(View view) { //상세조회 페이지로 돌아간다
                Intent intent = new Intent(getApplicationContext(), readView.class);
                startActivity(intent);
                finish();
            }
        });
        rd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int product_num=((USER)getApplicationContext()).getBno();
                sql="DELETE FROM "+ helper.tableName + " where num=" + product_num; //테이블에서 현재 글과 관련된 정보를 삭제한다
                database.execSQL(sql);
                String message ="삭제완료!";
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Home.class); //메인화면으로 넘어간다
                startActivity(intent);
                finish();
            }
        });
    }
}