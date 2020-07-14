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

public class DmReceiveDetail extends AppCompatActivity {
    Intent intent;

    int seqno;
    String title;
    String content;
    int send_Seqno;
    String send_Id;

    TextView tv_Title, tv_Content;
    Button btn_Reply, btn_Delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_receivedetail);

        intent = getIntent();
        seqno = Integer.parseInt(intent.getStringExtra("dm_Seqno"));
        title = intent.getStringExtra("dm_Title");
        content = intent.getStringExtra("dm_Content");
        send_Seqno = Integer.parseInt(intent.getStringExtra("dm_bSend"));
        send_Id = intent.getStringExtra("dm_SendId");

        tv_Title = findViewById(R.id.dm_detail_title);
        tv_Title.setText(title);
        tv_Content = findViewById(R.id.dm_detail_Content);
        tv_Content.setText(content);

        btn_Reply = findViewById(R.id.dm_detail_btnReply);
        btn_Delete = findViewById(R.id.dm_detail_btnDelete);

        btn_Reply.setOnClickListener(mClickListener);
        btn_Delete.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dm_detail_btnReply:
                    intent = new Intent(DmReceiveDetail.this, DmSendActivity.class);
                    intent.putExtra("to_bSend", Integer.toString(send_Seqno));
                    intent.putExtra("to_SendId", send_Id);
                    startActivity(intent);
                    break;

                case R.id.dm_detail_btnDelete:
                    DeleteDm();
                    break;
            }
        }
    };

    private void DeleteDm(){
        String url = ConnectValue.baseUrl + "dm_receive_delete.jsp?dm_Seqno=" + seqno;

        try{
            DmDeleteNetwork networtTask = new DmDeleteNetwork(DmReceiveDetail.this, url);
            networtTask.execute().get();

            if(networtTask.getDeleteResult()){
                showSuccessDialog();
            }
            else
                Toast.makeText(DmReceiveDetail.this, "메세지 삭제 실패.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(DmReceiveDetail.this, "메세지 삭제 실패.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccessDialog(){
        new AlertDialog.Builder(DmReceiveDetail.this)
                .setTitle("알림.")
                .setMessage("삭제 완료!")
                .setPositiveButton("닫기", dialogListener)
                .setCancelable(false)
                .show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
//            Intent intent = new Intent(DmReceiveDetail.this, MainActivity.class);
//            startActivity(intent);
            finish();
        }
    };

}
