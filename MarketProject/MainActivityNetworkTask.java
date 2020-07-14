package com.androidlec.marketproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivityNetworkTask extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    ArrayList<BoardInfo> data;

    public MainActivityNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.data = new ArrayList<BoardInfo>();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage(".....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while(true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                Parser(stringBuffer.toString());
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return data;
    }

    private void Parser(String s){
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("my_bList"));
            data.clear();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int seqno = jsonObject1.getInt("board_Seqno");
                int uSeqno = jsonObject1.getInt("board_uSeqno");
//                String id = jsonObject1.getString("board_uId");
                String title = jsonObject1.getString("board_Title");
                String price = jsonObject1.getString("board_Price");
                String content = jsonObject1.getString("board_Content");
                int hit = jsonObject1.getInt("board_Hit");
                String image = jsonObject1.getString("image_String");
                String sido = jsonObject1.getString("board_Sido");
                String latitude = jsonObject1.getString("board_Latitude");
                String longitude = jsonObject1.getString("board_Longitude");
                int isDone = jsonObject1.getInt("board_isDone");
                int likes = jsonObject1.getInt("likeCount");
                int likeCheck = jsonObject1.getInt("likeCheck");

                BoardInfo info = new BoardInfo(seqno, uSeqno, title, price, content, hit, image, sido, latitude, longitude, isDone, likes, likeCheck);
                data.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }
}
