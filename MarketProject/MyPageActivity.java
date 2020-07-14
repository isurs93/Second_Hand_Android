package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {
    ImageButton btn1, btn3, btn4, btn5;
    Button btnProfile, btnSendDm, btnReceiveDm;
    Intent intent;
    ListView myList;

    ArrayList<BoardInfo> ListData;

    BoardInfo_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        btnProfile = findViewById(R.id.mypage_btnprofile);
        btnSendDm = findViewById(R.id.mypage_btnSendDm);
        btnReceiveDm = findViewById(R.id.mypage_btnReceiveDm);

        myList = findViewById(R.id.mypage_listview);

        btn1 = findViewById(R.id.btn1);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        btnSendDm.setOnClickListener(ocl);
        btnReceiveDm.setOnClickListener(ocl);


        btn1.setOnClickListener(ocl);
        btn3.setOnClickListener(ocl);
        btn4.setOnClickListener(ocl);
        btn5.setOnClickListener(ocl);

    }

    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn1:
                    intent = new Intent(MyPageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn3:
                    intent = new Intent(MyPageActivity.this, WriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn4:
                    intent = new Intent(MyPageActivity.this, LikesActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn5:
                    onResume();
                    break;
                case R.id.mypage_btnprofile:
                    intent = new Intent(MyPageActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.mypage_btnSendDm: //  보낸쪽지
                    intent = new Intent(MyPageActivity.this, DmSendListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.mypage_btnReceiveDm:  //  받은쪽지
                    intent = new Intent(MyPageActivity.this, DmReceiveListActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        LoadMyStory();
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String seqno = Integer.toString(ListData.get(position).getSeqno());
            String title = ListData.get(position).getTitle();
            String price = ListData.get(position).getPrice();
            String content = ListData.get(position).getContent();
            String image = ListData.get(position).getImage();
            intent = new Intent(MyPageActivity.this, MyBoardUpdateActivity.class);
            intent.putExtra("my_bSeqno", seqno);
            intent.putExtra("my_bTitle", title);
            intent.putExtra("my_bPrice", price);
            intent.putExtra("my_bContent", content);
            intent.putExtra("image_String", image);
            startActivity(intent);
        }
    };

    private void LoadMyStory(){

        // jsp 연결 할 것!
//        String urlAddr = ConnectValue.baseUrl;
        String urlAddr = ConnectValue.baseUrl+"board_List_location.jsp?board_uSeqno="+MainActivity.userInfos.get(0).getSeqno();
        try{
            MyPageNetworkTask networkTask = new MyPageNetworkTask(MyPageActivity.this, urlAddr);
            Object object = networkTask.execute().get();
            ListData = (ArrayList<BoardInfo>) object;
            adapter = new BoardInfo_Adapter(MyPageActivity.this, R.layout.boardinfo_layout, ListData);
            myList.setAdapter(adapter);
            myList.setOnItemClickListener(onItemClickListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
