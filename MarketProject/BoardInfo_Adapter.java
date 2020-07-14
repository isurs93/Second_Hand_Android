package com.androidlec.marketproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardInfo_Adapter extends BaseAdapter {


    // Context는 Activity다.
    Context mContext = null;
    int layout = 0;
    ArrayList<BoardInfo> data = null;
    LayoutInflater inflater = null;

    ImageView[] imgMenu;

    public BoardInfo_Adapter(Context mContext, int layout, ArrayList<BoardInfo> data){

        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).seqno;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // if에서는 프레임 만드는 작업을 한다.
        if(convertView == null){
            // 데이터 프레임 하나를 만듬.
            convertView = inflater.inflate(this.layout, parent, false);
        }

//        ImageView imgThumnail = (ImageView) convertView.findViewById(R.id.bInfoLayout_Image);

        WebView webView = convertView.findViewById(R.id.bInfoLayout_Image);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(40);
        webView.setFocusable(false);
//        webView.setClickable(false);

        webView.loadUrl(ConnectValue.imageFTP+data.get(position).getImage());

        TextView tv_Title = convertView.findViewById(R.id.bInfoLayout_Title);
        TextView tv_Hit = convertView.findViewById(R.id.bInfoLayout_Hit);

//        imgThumnail.setImageResource(R.drawable.ic_launcher_foreground);


        tv_Title.setText(data.get(position).getTitle());
//        tv_Hit.setText(data.get(position).getHit());

        return convertView;
    }
}
