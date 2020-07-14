package com.androidlec.marketproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Marker_Adapter extends BaseAdapter {
    Context mContext = null; // 이것은 Activity
    int layout = 0;
    ArrayList<BoardInfo> data = null;
    LayoutInflater inflater = null;

    private WebSettings mWebSettings; //웹뷰세팅

//    public Marker_Adapter(Context mContext, int layout){
//        this.mContext = mContext;
//        this.layout = layout;
//        this.data = MapsActivity.showData; // 여기 MainActivity 를 변경한 클래스 이름으로 변경 할 것!
//        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    public Marker_Adapter(Context mContext, int layout, ArrayList<BoardInfo> data){
        this.mContext = mContext;
        this.layout = layout;
        this.data = data; // 여기 MainActivity 를 변경한 클래스 이름으로 변경 할 것!
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getSeqno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // if에서는 프레임 만드는 작업을 한다.
        if(convertView == null){
            // 데이터 프레임 하나를 만듬.
            convertView = inflater.inflate(this.layout, parent, false);
        }

        WebView webView = convertView.findViewById(R.id.markerLayout_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(40);
        webView.setFocusable(false);
        webView.setClickable(false);

//        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
//        mWebSettings = webImage.getSettings(); //세부 세팅 등록
//        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
//        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
//        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
//        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
//        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
//        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
//        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
//        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
//        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
//        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

//        webView.loadUrl(data.get(position).getImage()); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
          webView.loadUrl(ConnectValue.imageFTP+""+data.get(position).getImage());

        TextView title = convertView.findViewById(R.id.markerLayout_Title);
        TextView price = convertView.findViewById(R.id.markerLayout_Price);
        TextView likeCount = convertView.findViewById(R.id.markerLayout_likeCount);
        TextView viewCount = convertView.findViewById(R.id.markerLayout_viewCount);
        ImageView imgLikes = convertView.findViewById(R.id.markerList_imgLikes);

        if (data.get(position).getLikeCheck() == 0){
            imgLikes.setImageResource(R.drawable.ic_favorite_off);
        }else{
            imgLikes.setImageResource(R.drawable.ic_favorite_on);
        }

        ImageView markerLayout_isDone = convertView.findViewById(R.id.markerLayout_isDone);

        if(data.get(position).getIsDone() != 0){
            markerLayout_isDone.setImageResource(R.drawable.img_soldout);
        }

        title.setText(data.get(position).getTitle());
        price.setText(data.get(position).getPrice());
        likeCount.setText(Integer.toString(data.get(position).getLikes()));
        viewCount.setText(Integer.toString(data.get(position).getHit()));

        return convertView;
    }
}
