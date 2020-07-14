package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileUpdateActivity extends AppCompatActivity {
    Intent intent;

    EditText edit_Id, edit_Pw, edit_Pw2, edit_Name, edit_Telno, edit_Email;

    Button btn_Back, btn_Update;

    String urlAddr;

    UserInfo myData;

    int pwCheck = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        urlAddr = "http://192.168.4.119:8080/market/userInfo_SignUp.jsp?";

        edit_Id = findViewById(R.id.profile_editid);
        edit_Pw = findViewById(R.id.profile_editpw);
        edit_Pw2 = findViewById(R.id.profile_editpw2);
        edit_Name = findViewById(R.id.profile_editname);
        edit_Telno = findViewById(R.id.profile_edittelno);
        edit_Email = findViewById(R.id.profile_editemail);

        edit_Pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edit_Pw.getText().toString().equals(edit_Pw2.getText().toString())){
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

    private void Load_userInfo(){
        // jsp 연결 할 것!
        String urlAddr = ConnectValue.baseUrl;

        try{
            MyPageNetworkTask networkTask = new MyPageNetworkTask(ProfileUpdateActivity.this, urlAddr);
            Object object = networkTask.execute().get();
            myData = (UserInfo) object;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btn_back:
                    finish();
                    break;

                case R.id.btn_join:
                    String id = edit_Id.getText().toString().trim();
                    String pw = edit_Pw.getText().toString().trim();
                    String pw2 = edit_Pw2.getText().toString().trim();
                    String name = edit_Name.getText().toString().trim();
                    String telno = edit_Telno.getText().toString().trim();
                    String email = edit_Email.getText().toString().trim();

                    if(id.length() == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        edit_Id.requestFocus();
                        return;
                    }else if(pw.length() == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        edit_Pw.requestFocus();
                        return;
                    }else if(name.length() == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        edit_Name.requestFocus();
                        return;
                    }else if(telno.length() == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        edit_Telno.requestFocus();
                        return;
                    }else if(email.length() == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        edit_Email.requestFocus();
                        return;
                    }else if(pwCheck == 0) {
                        Toast.makeText(ProfileUpdateActivity.this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                        edit_Pw.requestFocus();
                        return;
                    }else{
                        // user_Seqno 입력하는 부분 수정하기
                        urlAddr = urlAddr + "?user_Seqno=" + 1 + "&user_Pw=" + pw + "&user_Name=" + name + "&user_Telno=" + telno + "&user_Email=" + email;
                        Toast.makeText(ProfileUpdateActivity.this, "정보수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };
}
