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

public class DmReceiveDataNetworkTask extends AsyncTask<Integer, String, Object> {

    Context mContext;
    String mUrl;
    ArrayList<DmReceiveInfo> myData;
    ProgressDialog progressDialog = null;

    public DmReceiveDataNetworkTask(Context mContext, String mUrl){
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.myData = new ArrayList<DmReceiveInfo>();
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

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while(true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                Log.v("item_test", stringBuffer.toString());
                ParserBoardInfo(stringBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();

            }catch (Exception e){
                e.printStackTrace();
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("dm_receive"));

            myData.clear();

            for(int index=0; index < jsonArray.length(); index++){

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(index);

                // Add - by psj
                int seqno = jsonObject1.getInt("dm_Seqno");
                String title = jsonObject1.getString("dm_Title");
                String content = jsonObject1.getString("dm_Content");
                int send_Seqno = jsonObject1.getInt("dm_bSend");
                String send_Id = jsonObject1.getString("user_SendId");

                DmReceiveInfo data = new DmReceiveInfo(seqno, title, content, send_Seqno, send_Id);
                myData.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
