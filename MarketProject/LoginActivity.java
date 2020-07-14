package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    String urlAddr;
    EditText login_id, login_pw;
    Button btn_login, btn_singUp;
    String id, pw;
    ArrayList<UserInfo> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        urlAddr = ConnectValue.baseUrl+"userInfo_Login.jsp?";
        Log.v("connetValueUrl",urlAddr);
//        urlAddr = "http://192.168.4.119:8080/market/userInfo_Login.jsp?";

        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_singUp = findViewById(R.id.btn_singUp);


        btn_login.setOnClickListener(onClickListener);
        btn_singUp.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    id = login_id.getText().toString();
                    pw = login_pw.getText().toString();

                    if(id.length() == 0){
                        Toast.makeText(LoginActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        login_id.requestFocus();
                        return;
                    }else if(pw.length() == 0){
                        Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        login_pw.requestFocus();
                        return;
                    }else{
                        urlAddr = urlAddr + "user_Id="+id+"&user_Pw="+pw;
                        connectSelectData();
                    }
                    break;
                case R.id.btn_singUp:
                    intent = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void connectSelectData(){
        try{
            LoginNetworkTask loginNetworkTask = new LoginNetworkTask(LoginActivity.this, urlAddr);
            Object obj = loginNetworkTask.execute().get();

            userInfos = (ArrayList<UserInfo>) obj;

            for (int i = 0; i<userInfos.size(); i++){
                Log.v("check9", ""+userInfos.get(i).getId());
                Log.v("check9", ""+userInfos.get(i).getPassword());
                Log.v("check9", ""+userInfos.get(i).getName());
                Log.v("check9", ""+userInfos.get(i).getEmail());
                Log.v("check9", ""+userInfos.get(i).getTelno());
            }
            if(userInfos.size()==0) {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("로그인 실패").setMessage("아이디와 비밀번호를 확인해주세요");
                builder.show();
                login_id.setText("");
                login_pw.setText("");
                urlAddr = ConnectValue.baseUrl+"userInfo_Login.jsp?";
//                urlAddr = "http://192.168.4.119:8080/market/userInfo_Login.jsp?";
                login_id.requestFocus();

            }else {
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("id",id); //회원 아이디 넘겨주기
                Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
//                startActivity(intent);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
