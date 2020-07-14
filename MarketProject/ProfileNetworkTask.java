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

public class ProfileNetworkTask extends AsyncTask<Integer, String, Object> {

    Context mContext;
    String mUrl;
    UserInfo myData;
    ProgressDialog progressDialog = null;

    public ProfileNetworkTask(Context mContext, String mUrl){
        this.mContext = mContext;
        this.mUrl = mUrl;
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

            Log.v("ProfileNetworkTask : ", mUrl);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while(true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                ParserUserInfo(stringBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.v("ProfileNetworkTask : ", "Fail in Load");
        }finally {
            try{

                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();

            }catch (Exception e){
                e.printStackTrace();
                Log.v("ProfileNetworkTask : ", "Fail in Close");
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

    private void ParserUserInfo(String s){
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("m_userinfo"));

            Log.v("ProfileNetworkTask : ", "for start.");
            for(int index=0; index < jsonArray.length(); index++){

                JSONObject jsonObject1 = (JSONObject) jsonArray.get(index);
                int seqno = jsonObject1.getInt("user_Seqno");
                String id = jsonObject1.getString("user_Id");
                String pw = jsonObject1.getString("user_Password");
                String name = jsonObject1.getString("user_Name");
                String telno = jsonObject1.getString("user_Telno");
                String email = jsonObject1.getString("user_Email");
            }
            Log.v("ProfileNetworkTask : ", "for process finish.");
        }catch (Exception e){
            e.printStackTrace();
            Log.v("ProfileNetworkTask : ", "Fail in Parser");
        }
    }
}
