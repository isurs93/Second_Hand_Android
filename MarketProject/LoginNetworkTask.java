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

public class LoginNetworkTask extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<UserInfo> userInfoArrayList;

    public LoginNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.userInfoArrayList = MainActivity.userInfos;
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get.........");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer=new StringBuffer();
        InputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;

        try{
            URL url=new URL(mAddr);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); //10초

            //jsp 소스가져오는것
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=httpURLConnection.getInputStream();
                inputStreamReader=new InputStreamReader(inputStream);
                bufferedReader=new BufferedReader(inputStreamReader);

                while (true){
                    String strline=bufferedReader.readLine();
                    if(strline==null)break;
                    stringBuffer.append(strline+"\n");//한줄씩 읽어서 data를 쌓아 논다
                }
                Parser(stringBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return userInfoArrayList;
    }

    private void Parser(String s){
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("login_info"));
            userInfoArrayList.clear();

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int user_Seqno = jsonObject1.getInt("user_Seqno");
                String user_Id = jsonObject1.getString("user_Id");
                String user_Pw = jsonObject1.getString("user_Password");
                String user_Name = jsonObject1.getString("user_Name");
                String user_Telno = jsonObject1.getString("user_Telno");
                String user_Email = jsonObject1.getString("user_Email");

                Log.v("check9", "Parser"+user_Seqno+"/"+user_Name+"/"+user_Telno);
                UserInfo userInfo = new UserInfo(user_Seqno, user_Id, user_Pw, user_Name, user_Telno, user_Email);
                userInfoArrayList.add(userInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
