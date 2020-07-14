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

public class DmReceiveListActivity extends AppCompatActivity {
    ArrayList<DmReceiveInfo> receiveData;

    ArrayList<String> Items;
    ArrayAdapter<String> Adapter;
    ListView list;

    int selectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_receivelist);
        Load_DmData();

        Adapter = new  ArrayAdapter<String>(this, android.R.layout.select_dialog_item, Items);
        list = findViewById(R.id.dm_receive_list);
        list.setOnItemClickListener(mItemClickListener);

        list.setAdapter(Adapter);
        //list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setChoiceMode(1);

    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(DmReceiveListActivity.this, DmReceiveDetail.class);
            intent.putExtra("dm_Seqno", Integer.toString(receiveData.get(position).getDm_Seqno()));
            intent.putExtra("dm_Title", receiveData.get(position).getDm_Title());
            intent.putExtra("dm_Content", receiveData.get(position).getDm_Content());
            intent.putExtra("dm_bSend", Integer.toString(receiveData.get(position).getDm_bSend()));
            intent.putExtra("dm_SendId", receiveData.get(position).getDm_SendId());

            Log.v("Dm_ReceiveList_Click", Integer.toString(receiveData.get(position).getDm_Seqno()));
            Log.v("Dm_ReceiveList_Click", receiveData.get(position).getDm_Title());
            Log.v("Dm_ReceiveList_Click", receiveData.get(position).getDm_Content());
            Log.v("Dm_ReceiveList_Click", Integer.toString(receiveData.get(position).getDm_bSend()));
            Log.v("Dm_ReceiveList_Click", receiveData.get(position).getDm_SendId());

            startActivity(intent);
        }
    };


    private void Load_DmData(){

        //String urlAddr = ConnectValue.baseUrl + "board_List_location.jsp?";
        String urlAddr = ConnectValue.baseUrl + "dm_load_receive.jsp?user_Seqno=" +
                MainActivity.userInfos.get(0).seqno;

        try{
            Items = new ArrayList<String>();
            DmReceiveDataNetworkTask networkTask = new DmReceiveDataNetworkTask(DmReceiveListActivity.this, urlAddr);
            Object object = networkTask.execute().get();
            receiveData = (ArrayList<DmReceiveInfo>) object;

            for(int index=0; index < receiveData.size(); index++){
                Items.add(receiveData.get(index).getDm_Title());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
