package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LikesActivity extends AppCompatActivity {
    ImageButton btn1, btn3, btn4, btn5;
    Intent intent;
    String urlAddr;
    ArrayList<BoardInfo> boardInfos;
    MainActivity_Adapter adapter;
    ListView lv_likes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        lv_likes = findViewById(R.id.lv_likes);

        btn1 = findViewById(R.id.btn1);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        btn1.setOnClickListener(ocl);
        btn3.setOnClickListener(ocl);
        btn4.setOnClickListener(ocl);
        btn5.setOnClickListener(ocl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        urlAddr = ConnectValue.baseUrl+"board_List_likes.jsp?user_Seqno="+MainActivity.userInfos.get(0).getSeqno();
        try{
            MainActivityNetworkTask networkTask = new MainActivityNetworkTask(LikesActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            boardInfos = (ArrayList<BoardInfo>) obj;

            adapter = new MainActivity_Adapter(LikesActivity.this, R.layout.mainlist_layout, boardInfos);
            lv_likes.setAdapter(adapter);
//            listView.setOnItemClickListener(oicl);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn1:
                    finish();
                    break;
                case R.id.btn3:
                    intent = new Intent(LikesActivity.this, WriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn4:
                    onResume();
                    break;
                case R.id.btn5:
                    intent = new Intent(LikesActivity.this, MyPageActivity.class);
//                    btn1.setImageResource(R.drawable.img_home_off);
                    startActivity(intent);
                    break;
            }
        }
    };


}
