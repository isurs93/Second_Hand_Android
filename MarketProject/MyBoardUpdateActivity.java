package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MyBoardUpdateActivity extends AppCompatActivity {

    Intent intent;
    String mybSeqno, myTitle, myPrice, myContent, myImage, urlAddr, urlAddr2, check;
    EditText et_title, et_price, et_content;
    Button btn_update, btn_delete;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myboardupdate);

        urlAddr = ConnectValue.baseUrl+"board_update.jsp?";
        urlAddr2 = ConnectValue.baseUrl+"board_delete.jsp?";

        intent = getIntent();
        mybSeqno = intent.getStringExtra("my_bSeqno");
        myTitle = intent.getStringExtra("my_bTitle");
        myPrice = intent.getStringExtra("my_bPrice");
        myContent = intent.getStringExtra("my_bContent");
        myImage = intent.getStringExtra("image_String");

        et_title = findViewById(R.id.bUpdate_title);
        et_price = findViewById(R.id.bUpdate_price);
        et_content = findViewById(R.id.bUpdate_content);
        webView = findViewById(R.id.wv_myBoard);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(40);
        webView.setFocusable(false);
        webView.setClickable(false);

        webView.loadUrl(ConnectValue.imageFTP+""+myImage);


        et_title.setText(myTitle);
        et_price.setText(myPrice);
        et_content.setText(myContent);

        btn_update = findViewById(R.id.btn_bUpdate);
        btn_delete = findViewById(R.id.btn_bDelete);

        btn_update.setOnClickListener(ocl);
        btn_delete.setOnClickListener(ocl);

    }

    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_bUpdate:
                    String upTitle = et_title.getText().toString().trim();
                    String upPrice = et_price.getText().toString().trim();
                    String upContent = et_content.getText().toString();

                    Log.v("mbl upCheck", "myT: "+myTitle+", upT: "+upTitle+"\n myP: "+myPrice+", upP: "+upPrice+"\n myC: "+myContent+", upC: "+upContent);
                    if (myTitle.equals(upTitle) && myPrice.equals(upPrice) && myContent.equals(upContent)){
                        finish();
                    }else{
                        urlAddr = urlAddr + "board_Seqno="+mybSeqno+"&board_Title="+upTitle+"&board_Price="+upPrice+"&board_Content="+upContent;
                        Log.v("mbupdate check", urlAddr);
                        connectUpdateData(urlAddr);

                        Toast.makeText(MyBoardUpdateActivity.this, "정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
                case R.id.btn_bDelete:
                    urlAddr2 = urlAddr2 + "board_Seqno=" + mybSeqno;
                    connectUpdateData(urlAddr2);
                    Toast.makeText(MyBoardUpdateActivity.this, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();

                    break;
            }
        }
    };
    private void connectUpdateData(String url){
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(MyBoardUpdateActivity.this, url);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
