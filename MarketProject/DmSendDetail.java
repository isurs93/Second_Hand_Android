package com.androidlec.marketproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DmSendDetail extends AppCompatActivity {
    Intent intent;

    int seqno;
    String title;
    String content;
    int send_Seqno;
    String send_Id;

    TextView tv_Title, tv_Content;
    Button btn_Delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_senddetail);

        intent = getIntent();
        seqno = Integer.parseInt(intent.getStringExtra("dm_Seqno"));
        title = intent.getStringExtra("dm_Title");
        content = intent.getStringExtra("dm_Content");
        send_Seqno = Integer.parseInt(intent.getStringExtra("dm_bReceive"));
        send_Id = intent.getStringExtra("dm_ReceiveId");

        tv_Title = findViewById(R.id.dm_sendDetail_title);
        tv_Title.setText(title);
        tv_Content = findViewById(R.id.dm_sendDetail_Content);
        tv_Content.setText(content);

        btn_Delete = findViewById(R.id.dm_sendDetail_btnDelete);
        btn_Delete.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dm_sendDetail_btnDelete:
                    DeleteDm();
                    break;
            }
        }
    };

    private void DeleteDm(){
        String url = ConnectValue.baseUrl + "dm_send_delete.jsp?dm_Seqno=" + seqno;

        try{
            DmDeleteNetwork networtTask = new DmDeleteNetwork(DmSendDetail.this, url);
            networtTask.execute().get();

            if(networtTask.getDeleteResult()){
                showSuccessDialog();
            }
            else
                Toast.makeText(DmSendDetail.this, "메세지 삭제 실패.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(DmSendDetail.this, "메세지 삭제 실패.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccessDialog(){
        new AlertDialog.Builder(DmSendDetail.this)
                .setTitle("알림.")
                .setMessage("삭제 완료!")
                .setPositiveButton("닫기", dialogListener)
                .setCancelable(false)
                .show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
//            Intent intent = new Intent(DmSendDetail.this, MainActivity.class);
//            startActivity(intent);
            finish();
        }
    };


}
