package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {

    Intent intent;
    EditText join_id, join_pw, join_pw2, join_name, join_telno, join_email;
    Button btn_idCheck, btn_back, btn_join;
    String id, pw, pw2, name, telno, email, urlAddr, urlAddr2;
    ArrayList<UserInfo> userInfos;
    int pwCheck = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        urlAddr = ConnectValue.baseUrl+"userInfo_SignUp.jsp?";
        urlAddr2=ConnectValue.baseUrl+"userInfo_IdCheck.jsp?";
//        urlAddr = "http://192.168.4.119:8080/market/userInfo_SignUp.jsp?";

        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_pw2 = findViewById(R.id.join_pw2);
        join_name = findViewById(R.id.join_name);
        join_telno = findViewById(R.id.join_telno);
        join_email = findViewById(R.id.join_email);

        btn_idCheck = findViewById(R.id.btn_idCheck);
        btn_back = findViewById(R.id.btn_back);
        btn_join = findViewById(R.id.btn_join);

        btn_idCheck.setOnClickListener(onClickListener);
        btn_back.setOnClickListener(onClickListener);
        btn_join.setOnClickListener(onClickListener);

        join_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(join_pw.getText().toString().equals(join_pw2.getText().toString())){
                    pwCheck = 1;
                }else{
                    pwCheck = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_idCheck:
                    id = join_id.getText().toString().trim();

                    if(id.length()!=0){
                        urlAddr2 = urlAddr2 + "user_Id="+id;
                        connectIdCheckData();
                    }else{
                        Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_id.requestFocus();
                        return;
                    }
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_join:
                    id = join_id.getText().toString().trim();
                    pw = join_pw.getText().toString().trim();
                    pw2 = join_pw2.getText().toString().trim();
                    name = join_name.getText().toString().trim();
                    telno = join_telno.getText().toString().trim();
                    email = join_email.getText().toString().trim();

                    if(id.length() == 0) {
                        Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_id.requestFocus();
                        return;
                    }else if(pw.length() == 0) {
                        Toast.makeText(JoinActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_pw.requestFocus();
                        return;
                    }else if(name.length() == 0) {
                        Toast.makeText(JoinActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_name.requestFocus();
                        return;
                    }else if(telno.length() == 0) {
                        Toast.makeText(JoinActivity.this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_telno.requestFocus();
                        return;
                    }else if(email.length() == 0) {
                        Toast.makeText(JoinActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        join_email.requestFocus();
                        return;
                    }else if(pwCheck == 0) {
                        Toast.makeText(JoinActivity.this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                        join_pw.requestFocus();
                        return;
                    }else{
                        urlAddr = urlAddr + "user_Id=" + id + "&user_Pw=" + pw + "&user_Name=" + name + "&user_Telno=" + telno + "&user_Email=" + email;
                        connectInsertData();
                        Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;

                case 2:

                    break;
            }
        }
    };

    private void connectIdCheckData(){
        try{
            JoinIdCheckNetworkTask joinIdCheckNetworkTask = new JoinIdCheckNetworkTask(JoinActivity.this, urlAddr2);
            Object obj = joinIdCheckNetworkTask.execute().get();

            userInfos = (ArrayList<UserInfo>) obj;

            if (userInfos.size() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("아이디 중복 체크").setMessage("사용 가능한 아이디입니다.");
                builder.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("아이디 중복 체크").setMessage("사용중인 아이디 입니다.");
                builder.show();
            }
            urlAddr2=ConnectValue.baseUrl+"userInfo_IdCheck.jsp?";
//            urlAddr2="http://192.168.4.119:8080/market/userInfo_IdCheck.jsp?";
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void connectInsertData(){
        try{
            JoinNetworkTask joinNetworkTask = new JoinNetworkTask(JoinActivity.this, urlAddr);
            joinNetworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
