package com.androidlec.marketproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private final int REQ_CODE_SELECT_IMAGE = 100;
    EditText write_title, write_price, write_content;
    Button btn_getImage, btn_write, btn_write_back;
    ImageView iv_write;
    String wTitle, wContent, urlAddr, urlAddr2;
    String imageAddr, image;
    String wPrice;

    // Field 권한관련 함수
    private String[] permissions = { // 이곳에 추가할 권할을 넣어주면 됨
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    private static final int MULTIPLE_PERMISSIONS = 101; // 권한 동의 여부 문의 후 callback 함수에 쓰일 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        urlAddr = ConnectValue.baseUrl+"board_Write.jsp?";
        urlAddr2 = ConnectValue.baseUrl+"image_Write.jsp?";

        iv_write = findViewById(R.id.iv_write);

        write_title = findViewById(R.id.write_title);
        write_price = findViewById(R.id.write_price);
        write_content = findViewById(R.id.write_content);

        btn_getImage = findViewById(R.id.btn_getImage);
        btn_write = findViewById(R.id.btn_write);
        btn_write_back = findViewById(R.id.btn_write_back);

        btn_getImage.setOnClickListener(ocl);
        btn_write.setOnClickListener(ocl);
        btn_write_back.setOnClickListener(ocl);
    }



    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_getImage:
                    if(Build.VERSION.SDK_INT > 22) {
                        checkPermissions();
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    break;
                case R.id.btn_write_back:
                    finish();
                    break;
                case R.id.btn_write:
                    int seq = MainActivity.userInfos.get(0).getSeqno();
                    Log.v("check9","user Seq"+seq);
                    wTitle = write_title.getText().toString().trim();
                    wPrice = write_price.getText().toString().trim();
                    wContent = write_content.getText().toString().trim();
                    if(image == null){
                        Toast.makeText(WriteActivity.this, "판매하실 상품의\n이미지를 등록해주세요.", Toast.LENGTH_SHORT).show();
                    }else if(wTitle.length() == 0){
                        Toast.makeText(WriteActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        write_title.requestFocus();
                    }else if (wPrice.length() == 0){
                        Toast.makeText(WriteActivity.this, "가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        write_price.requestFocus();
                    }else if(wContent.length() == 0){
                        Toast.makeText(WriteActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        write_content.requestFocus();
                    }else{
                        // 시 , 위도 , 경도,

//                        urlAddr = urlAddr + "board_uSeqno=" + seq + "&board_Title=" + wTitle + "&board_Price="
//                        + wPrice + "&board_Content=" + wContent;

                        String latitude = Double.toString(MainActivity.myLocation.getMyLatitude());
                        String longitude = Double.toString(MainActivity.myLocation.getMyLongitude());
                        String sido = MainActivity.myLocation.getMyAddress();

//                        http://192.168.2.10:8080/market/Android/board_Write.jsp?board_uSeqno=2&board_Title=이거팜&board_Price=5000&board_Content=내용없음&board_Latitude=1234&board_Longitude=5678&board_Sido=서울임


                        urlAddr = urlAddr + "board_uSeqno=" + seq + "&board_Title=" + wTitle + "&board_Price="
                                + wPrice + "&board_Content=" + wContent + "&board_Latitude=" + latitude
                                + "&board_Longitude=" + longitude + "&board_Sido=" + sido;
                        Log.v("check999",urlAddr);

                        connectWriteData();

                        urlAddr2 = urlAddr2 + "image_String="+image;
                        connectImageData();

                        connectFTPData(imageAddr);
                        Toast.makeText(WriteActivity.this, "게시물이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent();
        Bitmap bitmap;
        if(requestCode == REQ_CODE_SELECT_IMAGE){
            switch (resultCode){
                case RESULT_OK:
                    try{
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                        Uri uri = data.getData();
//                        bitmap = resize(WriteActivity.this, uri, 200);

                        iv_write.setImageBitmap(bitmap);

                        imageAddr = getRealPathFromURI(uri);
                        image = getImageName(imageAddr);

                        Log.v("check9", "이미지 실제경로 : "+imageAddr);
                        Log.v("check9", "이미지 실제경로에서 이미지 이름만 가져온 정보 : "+image);

//                        Bitmap copyImage = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
//                        iv_write.setImageBitmap(copyImage);
                    }catch (Exception e){
                        e.printStackTrace();
                    }catch (OutOfMemoryError e){
                        Toast.makeText(this,"이미지 용량이 너무 큽니다.", Toast.LENGTH_SHORT).show();
                    }
                    setResult(RESULT_OK,intent);
                    break;
                case RESULT_CANCELED:
                    setResult(RESULT_CANCELED, intent);
                    finish();
                    break;
            }
        }
    }

    private String getRealPathFromURI(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst(); //****
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private String getImageName(String img) {
        int index = img.lastIndexOf("/");
        String imgName = img.substring(index + 1);
        Log.v("check9", img);
        Log.v("check9", imgName);
        return imgName;
    }

    private boolean checkPermissions(){

        int result;
        List<String> permissionList = new ArrayList<>();
        for(String pm: permissions){
            result = ContextCompat.checkSelfPermission(this, pm);
            if(result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if(!permissionList.isEmpty()){  // 권한이 추가되었으면 해당 리스트가 empty가 아니므로, request 즉 권한을 요청
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return  false;
        }
        return true;
    }


    private void connectWriteData(){
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(WriteActivity.this, urlAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void connectImageData(){
        try{
            JoinNetworkTask networkTask = new JoinNetworkTask(WriteActivity.this, urlAddr2);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void connectFTPData(String str){
        try {
            WriteNetworkTask writeNetworkTask = new WriteNetworkTask(WriteActivity.this, str);
            Log.v("imgTag","ftp Network imageAddr ----->>>>"+imageAddr);
            writeNetworkTask.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 권한 요청의 콜백 함수 PERMISSION_GRANTED로 권한을 획득하였는지를 확인
    // 아래에서는 !=를 사용했기에 권한 사용에 동의를 안했을 경우를 if문을 사용해서 코딩
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i=0; i<permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Bitmap resize(Context context, Uri uri, int resize){
        Bitmap resizeBitmap=null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 1번

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap=bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }


}
