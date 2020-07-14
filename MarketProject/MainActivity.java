package com.androidlec.marketproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton btn1, btn3, btn4, btn5, main_btnMaps, main_btnDM;
    Intent intent;
    MainActivity_Adapter adapter;
    ListView listView;
    int check = 0;
    ArrayList<BoardInfo> boardInfos;
    static ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
    String urlAddr;
    ImageView mainList_image_isGone;

    static MyLocation myLocation = null;    // 유저의 위도, 경도, 주소 저장하기 위한 전역 클래스 변수.

    //
    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_listView);

        btn1=findViewById(R.id.btn1);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        main_btnMaps = findViewById(R.id.main_btnMaps);

        btn1.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        main_btnMaps.setOnClickListener(onClickListener);

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }
        getMyLocation();

        Log.v("check9","udrtInfo size"+userInfos.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        btn1.setImageResource(R.drawable.img_home_on);
        checkAction();
        getMyLocation();
    }

    private void checkAction(){
        if(userInfos.size()==0){
            check = 0;
        }else{
            check = 1;
        }

        switch (check){
            case 0:
                intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent = new Intent(MainActivity.this, BoardActivity.class);
                startActivity(intent);
                break;
            case 1:
                connectGetData();
                break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn1:
                    onResume();
                    break;
                case R.id.btn3:
                    intent = new Intent(MainActivity.this, WriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn4:
                    intent = new Intent(MainActivity.this, LikesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn5:
                    intent = new Intent(MainActivity.this, MyPageActivity.class);
//                    btn1.setImageResource(R.drawable.img_home_off);
                    startActivity(intent);
                    break;
                case R.id.main_btnMaps:
                    intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener oicl = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String bSeqno = Integer.toString(boardInfos.get(position).getSeqno());
            String uSeqno = Integer.toString(boardInfos.get(position).getuSeqno());
//            String uId = boardInfos.get(position).getId();
            String title = boardInfos.get(position).getTitle();
            String price = boardInfos.get(position).getPrice();
            String content = boardInfos.get(position).getContent();
            String hit = Integer.toString(boardInfos.get(position).getHit());
            String image = boardInfos.get(position).getImage();
            String likes = Integer.toString(boardInfos.get(position).getLikes());
            String likeCheck = Integer.toString(boardInfos.get(position).getLikeCheck());
            String sido = boardInfos.get(position).getSido();
            intent = new Intent(MainActivity.this, BoardActivity.class);
            intent.putExtra("select_bSeqno", bSeqno);
            intent.putExtra("select_uSeqno", uSeqno);
//            intent.putExtra("select_uId", uId);
            intent.putExtra("select_title", title);
            intent.putExtra("select_price", price);
            intent.putExtra("select_content", content);
            intent.putExtra("select_hit", hit);
            intent.putExtra("select_image", image);
            intent.putExtra("select_likes", likes);
            intent.putExtra("select_likeCheck", likeCheck);
            intent.putExtra("select_sido", sido);
            startActivity(intent);
        }
    };

    private void connectGetData(){
        urlAddr = ConnectValue.baseUrl+"board_List_location.jsp?user_Seqno="+MainActivity.userInfos.get(0).getSeqno();
        Log.v("check99", urlAddr);
        try{
            MainActivityNetworkTask networkTask = new MainActivityNetworkTask(MainActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            boardInfos = (ArrayList<BoardInfo>) obj;

            adapter = new MainActivity_Adapter(MainActivity.this, R.layout.mainlist_layout, boardInfos);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(oicl);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getMyLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);

        Log.v("my Location", "My latitude : " + latitude);
        Log.v("my Location", "My longitude : " +longitude);
        Log.v("my Location", "My Address : " + address);

        myLocation = new MyLocation(latitude, longitude, address);
    }

    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if ( check_result ) {
                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }


}
