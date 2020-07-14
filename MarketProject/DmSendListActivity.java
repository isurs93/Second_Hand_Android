package com.androidlec.marketproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DmSendListActivity extends AppCompatActivity {
    ArrayList<DmSendInfo> sendData;

    ArrayList<String> Items;
    ArrayAdapter<String> Adapter;
    ListView list;

    int selectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_sendlist);
        Load_DmData();

        Adapter = new  ArrayAdapter<String>(this, android.R.layout.select_dialog_item, Items);
        list = findViewById(R.id.dm_sendlist_list);
        list.setOnItemClickListener(mItemClickListener);

        list.setAdapter(Adapter);
        //list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setChoiceMode(1);

    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(DmSendListActivity.this, DmSendDetail.class);
            intent.putExtra("dm_Seqno", Integer.toString(sendData.get(position).getDm_Seqno()));
            intent.putExtra("dm_Title", sendData.get(position).getDm_Title());
            intent.putExtra("dm_Content", sendData.get(position).getDm_Content());
            intent.putExtra("dm_bReceive", Integer.toString(sendData.get(position).getDm_bReceive()));
            intent.putExtra("dm_ReceiveId", sendData.get(position).getDm_ReceiveId());

            Log.v("Dm_SendList_Click", Integer.toString(sendData.get(position).getDm_Seqno()));
            Log.v("Dm_SendList_Click", sendData.get(position).getDm_Title());
            Log.v("Dm_SendList_Click", sendData.get(position).getDm_Content());
            Log.v("Dm_SendList_Click", Integer.toString(sendData.get(position).getDm_bReceive()));
            Log.v("Dm_SendList_Click", sendData.get(position).getDm_ReceiveId());

            startActivity(intent);
        }
    };


    private void Load_DmData(){

        //String urlAddr = ConnectValue.baseUrl + "board_List_location.jsp?";
        String urlAddr = ConnectValue.baseUrl + "dm_load_send.jsp?user_Seqno=" +
                MainActivity.userInfos.get(0).seqno;

        try{
            Items = new ArrayList<String>();
            DmSendDataNetworkTask networkTask = new DmSendDataNetworkTask(DmSendListActivity.this, urlAddr);
            Object object = networkTask.execute().get();
            sendData = (ArrayList<DmSendInfo>) object;

            for(int index=0; index < sendData.size(); index++){
                Items.add(sendData.get(index).getDm_Title());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

