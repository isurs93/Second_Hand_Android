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

public class MainActivity_Adapter extends BaseAdapter {
    Context context;
    int layout = 0;
    ArrayList<BoardInfo> boardInfos;
    LayoutInflater inflater;

    public MainActivity_Adapter(Context context, int layout, ArrayList<BoardInfo> boardInfos) {
        this.context = context;
        this.layout = layout;
        this.boardInfos = boardInfos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return boardInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return boardInfos.get(position).getSeqno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }

//        ImageView imageView = convertView.findViewById(R.id.mainList_image);
//        imageView.setImageResource(R.drawable.ic_launcher_foreground);

        WebView webView = (WebView) convertView.findViewById(R.id.mainList_image);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(40);
        webView.setFocusable(false);
        webView.setClickable(false);


        webView.loadUrl(ConnectValue.imageFTP+""+boardInfos.get(position).getImage());

        TextView tvTitle = convertView.findViewById(R.id.mainList_title);
        TextView tvPrice = convertView.findViewById(R.id.mainList_price);
        TextView tvHit = convertView.findViewById(R.id.mainList_hit);
        TextView tvLike = convertView.findViewById(R.id.mainList_like);

        ImageView ivLike = convertView.findViewById(R.id.mainList_imgLike);

        if (boardInfos.get(position).getLikeCheck() != 0){
            ivLike.setImageResource(R.drawable.ic_favorite_on);
        }else{
            ivLike.setImageResource(R.drawable.ic_favorite_off);
        }

        ImageView mainList_image_isDone = convertView.findViewById(R.id.mainList_image_isDone);

        if(boardInfos.get(position).getIsDone() != 0){
            mainList_image_isDone.setImageResource(R.drawable.img_soldout);
        }

        tvTitle.setText(boardInfos.get(position).getTitle());
        tvPrice.setText(boardInfos.get(position).getPrice());
        tvHit.setText(boardInfos.get(position).getHit()+"");
        tvLike.setText(boardInfos.get(position).getLikes()+"");

        return convertView;
    }
}
