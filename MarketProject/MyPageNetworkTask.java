package com.androidlec.marketproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyPageNetworkTask extends AsyncTask<Integer, String, Object> {

    Context mContext;
    String mUrl;
    ArrayList<BoardInfo> myData;
    ProgressDialog progressDialog = null;

    int mOption;

    public MyPageNetworkTask(Context mContext, String mUrl){
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.myData = new ArrayList<BoardInfo>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            Log.v("BoardInfo_Parser : ", mUrl);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while(true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                ParserBoardInfo(stringBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.v("BoardInfo_Parser : ", "Fail in Load");
        }finally {
            try{

                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();

            }catch (Exception e){
                e.printStackTrace();
                Log.v("BoardInfo_Parser : ", "Fail in Close");
            }
        }

        return myData;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private void ParserBoardInfo(String s){
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("my_bList"));

            myData.clear();
            Log.v("BoardInfo_Parser : ", "myData.clear()");

            Log.v("BoardInfo_Parser : ", "for start.");
            for(int index=0; index < jsonArray.length()-1; index++){

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(index);
                int seqno = jsonObject1.getInt("board_Seqno");
                int uSeqno = jsonObject1.getInt("board_uSeqno");
//                String id = jsonObject1.getString("board_uId");
                String title = jsonObject1.getString("board_Title");
                String price = jsonObject1.getString("board_Price");
                String content = jsonObject1.getString("board_Content");
                int hit = jsonObject1.getInt("board_Hit");
                String image = jsonObject1.getString("image_String");
                String sido = jsonObject1.getString("image_Sido");
                String latitude = jsonObject1.getString("board_Latitude");
                String longitude = jsonObject1.getString("board_Longitude");
                int isDone = jsonObject1.getInt("board_isDone");
                int likes = jsonObject1.getInt("likeCount");
                int likeCheck = jsonObject1.getInt("likeCheck");

                BoardInfo data = new BoardInfo(seqno, uSeqno, title, price, content, hit, image, sido, latitude, longitude, isDone, likes, likeCheck);

                myData.add(data);
            }
            Log.v("content_Parser_test : ", "for process finish.");
        }catch (Exception e){
            e.printStackTrace();
            Log.v("content_Parser_test : ", "Fail in Parser");
        }
    }
}
