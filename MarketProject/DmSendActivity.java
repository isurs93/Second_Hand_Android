package com.androidlec.marketproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DmSendActivity extends AppCompatActivity {
    Intent intent;

    int seqno;
    String title;
    String content;
    int to_Seqno;
    String to_Id;

    EditText edt_Title, edt_Content;
    Button btn_Reply, btn_Delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_send);

        intent = getIntent();
        to_Seqno = Integer.parseInt(intent.getStringExtra("to_bSend"));
        to_Id = intent.getStringExtra("to_SendId");

        edt_Title = findViewById(R.id.dm_send_title);
        edt_Content = findViewById(R.id.dm_send_Content);

        btn_Reply = findViewById(R.id.dm_send_btnSend);
        btn_Delete = findViewById(R.id.dm_send_btnCancle);

        btn_Reply.setOnClickListener(mClickListener);
        btn_Delete.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dm_send_btnSend:
                    SendDm();
                    break;

                case R.id.dm_send_btnCancle:
                    finish();
                    break;
            }
        }
    };

    private void SendDm(){
        String url = ConnectValue.baseUrl + "dm_send.jsp" +
                "?dm_bSend=" + MainActivity.userInfos.get(0).getSeqno() +
                "&dm_Title=" + edt_Title.getText().toString().trim() +
                "&dm_Content=" + edt_Content.getText().toString().trim() +
                "&dm_bReceive=" + to_Seqno;
        Log.v("send_dm_test", url);

        try{
            DmSendNetworkTask networtTask = new DmSendNetworkTask(DmSendActivity.this, url);
            networtTask.execute().get();

            if(networtTask.getCheckResult()){
                showSuccessDialog();
            }
            else
                Toast.makeText(DmSendActivity.this, "메세지 전송 실패.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(DmSendActivity.this, "메세지 전송 실패.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccessDialog(){
        new AlertDialog.Builder(DmSendActivity.this)
                .setTitle("알림.")
                .setMessage("전송 완료!")
                .setPositiveButton("닫기", dialogListener)
                .setCancelable(false)
                .show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
//            Intent intent = new Intent(DmSendActivity.this, MainActivity.class);
//            startActivity(intent);
            finish();
        }
    };


}
