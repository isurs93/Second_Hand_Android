package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MarkerActivity extends AppCompatActivity {
    ListView markerList;
    Marker_Adapter adapter;
    //    ArrayList<BoardInfo> boardInfos;
    static public ArrayList<BoardInfo> showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markerlist);

        markerList = findViewById(R.id.markerList_datalist);
        markerList.setOnItemClickListener(onItemClickListener);
//        setMarkerList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMarkerList();
    }

    private void setMarkerList(){
//        MapsActivity mapsActivity = new MapsActivity();
//        mapsActivity.Load_BoardList();
//        adapter = new Marker_Adapter(MarkerActivity.this, R.layout.markerlist_layout, MapsActivity.showData);
//        markerList.setAdapter(adapter);

        String urlAddr = ConnectValue.baseUrl + "marker_List.jsp?user_Seqno=" + MainActivity.userInfos.get(0).getSeqno()
                +"&board_Latitude="+MapsActivity.lat+"&board_Longitude="+MapsActivity.lngt;
        Log.v("maps_data_check", urlAddr);
        try{
            MainActivityNetworkTask networkTask = new MainActivityNetworkTask(MarkerActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            showData = (ArrayList<BoardInfo>) obj;

            adapter = new Marker_Adapter(MarkerActivity.this, R.layout.markerlist_layout, showData);
            markerList.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        Intent intent = null;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<BoardInfo> data =showData;
            Log.v("Click Test", "seqno : " + data.get(position).getSeqno());
            Log.v("Click Test", "uSeqno : " + data.get(position).getuSeqno());
            Log.v("Click Test", "title : " + data.get(position).getTitle());
            Log.v("Click Test", "price : " + data.get(position).getPrice());
            Log.v("Click Test", "content : " + data.get(position).getContent());
            Log.v("Click Test", "Hit : " + data.get(position).getHit());
            Log.v("Click Test", "Sido : " + data.get(position).getSido());
            Log.v("Click Test", "latitude : " + data.get(position).getLatitude());
            Log.v("Click Test", "longitude : " + data.get(position).getLongtitude());
            Log.v("Click Test", "Likes : " + data.get(position).getLikes());
            Log.v("Click Test", "LikeCheck : " + data.get(position).getLikeCheck());
            Log.v("Click Test", "image : " + data.get(position).getImage());

            intent = new Intent(MarkerActivity.this, BoardActivity.class);
            intent.putExtra("select_bSeqno", Integer.toString(data.get(position).getSeqno()));
            intent.putExtra("select_uSeqno", Integer.toString(data.get(position).getuSeqno()));
            intent.putExtra("selectmarkerLayout_isDone_title", data.get(position).getTitle());
            intent.putExtra("select_price", data.get(position).getPrice());
            intent.putExtra("select_content", data.get(position).getContent());
            intent.putExtra("select_hit", Integer.toString(data.get(position).getHit()));
            intent.putExtra("select_image", data.get(position).getImage());
            intent.putExtra("select_likes", Integer.toString(data.get(position).getLikes()));
            intent.putExtra("select_likeCheck", Integer.toString(data.get(position).getLikeCheck()));
            intent.putExtra("select_sido", data.get(position).getSido());

//            uSeqno = intent.getStringExtra("select_uSeqno");
//            title = intent.getStringExtra("select_title");
//            price = intent.getStringExtra("select_price");
//            content = intent.getStringExtra("select_content");
//            hit = intent.getStringExtra("select_hit");
//            image = intent.getStringExtra("select_image");
//            likes = Integer.parseInt(intent.getStringExtra("select_likes"));
//            likeCheck = Integer.parseInt(intent.getStringExtra("select_likeCheck"));
            startActivity(intent);
        }
    };





}

