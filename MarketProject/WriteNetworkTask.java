package com.androidlec.marketproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WriteNetworkTask extends AsyncTask<Integer, String, Void> {

    ConnectFTP connectFTP;
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog;

    public WriteNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Ins.........");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        connectFTP = new ConnectFTP();                 //FTP server
        int index = mAddr.lastIndexOf("/");     //전체 urladdress에서 이미지 파일명만 받기
        String imgName = mAddr.substring(index+1);  // 오류방지
        boolean status = false;                       // FTP server 연결여부


//        status = connectFTP.ftpConnect("192.168.0.23", "sungil", "qwer1234",21);
        status = connectFTP.ftpConnect(ConnectValue.baseIP, "sungil", "qwer1234",21);
        Log.v("check9","FTP connect is : " + status);
        try{
            if(status == true){
                boolean result = connectFTP.ftpUploadFile(mAddr, imgName,"market/");  // url주소, 이미지 이름 업로드해라 해당 파일에
                Log.v("check9","FTP Upload is : "+ result);
                // "http://js6858k.dothome.co.kr/img/"
            }else{
                Log.v("imgTag","FTP연결실패!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
