package com.example.project3;

import android.app.Application;
//현재 접속한 사용자의 아이디와 현재 접속한 글의 번호를 저장하는 액티비티
public class USER extends Application {
    private String id ;
    private Integer bno;

    public void setId(String id) {
        this.id = id;
    }
    public void setBno(Integer bno) {this.bno=bno;}
    public String getId() {
        return id;
    }
    public Integer getBno() {
        return bno;
    }
}
