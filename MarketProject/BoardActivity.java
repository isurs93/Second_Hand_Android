package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    Intent intent;
    WebView wv_Image;
    TextView tv_Price, tv_Title, tv_Time, tv_Hit, tv_Likes, tv_Content, tv_Location, tv_writer;
    ImageButton btn_Dm, btn_back;
    ImageView iv_likeAction;
    int likes, likeCheck, hit;
    String bSeqno, uSeqno, id, title, price, content, image, sido;
    String likeInsAddr, likeDelAddr, urlAddr;
    ArrayList<BoardInfo> boardInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        intent = getIntent();
        uSeqno = intent.getStringExtra("select_uSeqno");
//        id = intent.getStringExtra("select_uId");
        title = intent.getStringExtra("select_title");
        price = intent.getStringExtra("select_price");
        content = intent.getStringExtra("select_content");
        hit = Integer.parseInt(intent.getStringExtra("select_hit"));
        image = intent.getStringExtra("select_image");
        likes = Integer.parseInt(intent.getStringExtra("select_likes"));
        likeCheck = Integer.parseInt(intent.getStringExtra("select_likeCheck"));
        sido = intent.getStringExtra("select_sido");

        wv_Image = findViewById(R.id.wb_bImage);
        tv_Price = findViewById(R.id.tv_bPrice);
        tv_Title = findViewById(R.id.tv_bTitle);
        tv_Time = findViewById(R.id.tv_time);
        tv_Hit = findViewById(R.id.tv_hit);
        tv_Likes = findViewById(R.id.tv_likes);
        tv_Content = findViewById(R.id.tv_bContent);
        tv_Location = findViewById(R.id.tv_bLocation);
        tv_writer = findViewById(R.id.tv_writer);

        iv_likeAction = findViewById(R.id.iv_likeAction);
        likesOnOff();

        btn_Dm = findViewById(R.id.btn_bDm);
        btn_back = findViewById(R.id.btn_bBack);

        iv_likeAction.setOnClickListener(ocListener);
        btn_Dm.setOnClickListener(ocListener);
        btn_back.setOnClickListener(ocListener);

        wv_Image.getSettings().setJavaScriptEnabled(true);
        wv_Image.getSettings().setLoadWithOverviewMode(true);
        wv_Image.getSettings().setUseWideViewPort(true);
//        wv_Image.getSettings().setBuiltInZoomControls(true);
//        wv_Image.getSettings().setSupportZoom(true);
        wv_Image.setHorizontalScrollBarEnabled(false);
        wv_Image.setVerticalScrollBarEnabled(false);
        wv_Image.setInitialScale(1);

        wv_Image.setFocusable(false);
        wv_Image.setClickable(false);

        wv_Image.loadUrl(ConnectValue.imageFTP+""+image);

        tv_Price.setText(price);
        tv_Title.setText(title);
        tv_Content.setText(content);
        tv_Hit.setText(Integer.toString(hit+1));
        tv_Likes.setText(likes+"");
        tv_Location.setText(sido);
//        tv_writer.setText(id);

    }
    private void likesOnOff(){
        if(likeCheck!=0){
            iv_likeAction.setImageResource(R.drawable.ic_favorite_on);
        }else{
            iv_likeAction.setImageResource(R.drawable.ic_favorite_off);
        }
    }

    View.OnClickListener ocListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_likeAction:
                    if(likeCheck != 0){
                        likes -= 1; likeCheck = 0;
                        likeDelAction();
                    }else{
                        likes += 1; likeCheck = 1;
                        likeInsAction();
                    }
                    likesOnOff();
                    tv_Likes.setText(likes+"");
                    break;

                case R.id.btn_bBack:
                    finish();
                    break;
                case R.id.btn_bDm:
                    intent = new Intent(BoardActivity.this, DmSendActivity.class);
                    intent.putExtra("to_bSend", uSeqno);
                    intent.putExtra("to_SendId", MainActivity.userInfos.get(0).getId());
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        hitAction();
    }

    private void likeDelAction(){
        likeDelAddr = ConnectValue.baseUrl+"likes_delete.jsp?board_Seqno="+bSeqno+"&user_Seqno="+uSeqno;
        Log.v("check99",""+likeDelAddr);
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(BoardActivity.this, likeDelAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void likeInsAction(){
        likeInsAddr = ConnectValue.baseUrl+"likes_insert.jsp?board_Seqno="+bSeqno+"&user_Seqno="+uSeqno;
        Log.v("check99",""+likeInsAddr);
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(BoardActivity.this, likeInsAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void hitAction(){
        intent = getIntent();
        bSeqno = intent.getStringExtra("select_bSeqno");
        urlAddr = ConnectValue.baseUrl+"board_view_up.jsp?board_Seqno="+bSeqno;
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(BoardActivity.this, urlAddr);
            networkTask.execute().get();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
