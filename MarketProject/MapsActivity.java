package com.androidlec.marketproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // 나타낼 데이터들을 위한 변수들.
    ArrayList<BoardInfo> listData = null;                   // board 테이블의 전체 데이터 저장 할 배열.
//    static public ArrayList<BoardInfo> showData = null;     // 마커 클릭 시 해당 좌표의 데이터만 저장 할 배열.

    // Naver Maps 띄우기 위한 변수.
    private MapView mapView;
    public static String lat, lngt;

    // 현재 위치 표시하기 위한 변수들.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.v("Map test", "onCreate");
        // board 테이블에 있는 모든 데이터를 불러온다.
        Load_BoardList();

        // NaverMaps Api를 사용하기 위한 셋팅.
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("jnk4i8epb7"));

        // Activity_main 과 연결한 변수.
        mapView = findViewById(R.id.maps_mapView);
        mapView.onCreate(savedInstanceState);

        // Naver Maps를 나타내기위해 Frament와 연결 작업.
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.maps_mapView);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.maps_mapView, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lat=""; lngt="";
        Load_BoardList();
    }

    // OnMapReadyCallback 상속 받은 후 생성 된 메소드.
    // 지도 생성시 실행하는 메소드.
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.v("Map test", "onMapReady");
        Log.v("load_test" , "onMapReady");

        // naverMaps UI 및 클릭 이벤트를 위한 변수들.
        UiSettings uiSettings = naverMap.getUiSettings();

        uiSettings.setTiltGesturesEnabled(false);       // 틸트 제스쳐 사용 가능 여부 설정.
        uiSettings.setRotateGesturesEnabled(false);     // 회전 제스쳐 사용 가능 여부 설정.
        uiSettings.setLocationButtonEnabled(true);      // 현재 위치 이동 버튼 활성화 여부 설정.

        // 이 위치는 학원 근처? 위치이다.
        double latitude = 37.505;
        double longitude = 127.03;

        //double latitude = MainActivity.myLocation.getMyLatitude();
        //double longitude = MainActivity.myLocation.getMyLongitude();

        // 테스트를 위한 임의의 위치 설정.
        LatLng testLocation = new LatLng(latitude, longitude);

        // 마커 클릭이 발생 시킬 이벤트 준비 작업.
//        InfoWindow infoWindow = new InfoWindow();

        int listCount = listData.size(); // 전체 데이터 개수 만큼 마커를 생성한다.
        // 마커 생성 반복문 작업.
        for(int index = 0; index < listCount; index++){
            double board_Latitude = Double.parseDouble(listData.get(index).getLatitude());
            double board_Logitude = Double.parseDouble(listData.get(index).getLongtitude());

            LatLng location = new LatLng(board_Latitude, board_Logitude);
            Marker marker = new Marker();
            marker.setPosition(location);               // 마커 위치 설정.

//            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_dm));
            marker.setIcon(MarkerIcons.BLACK);
            marker.setIconTintColor(Color.RED);          // 마커 색상 설정.
            marker.setWidth(100);                        // 마커 너비 설정.
            marker.setHeight(160);                       // 마커 높이 설정.
            marker.setOnClickListener(overlay -> {       // 마커 클릭 시 이벤트.

                double check_Latitude = marker.getPosition().latitude;      // 클릭한 마커의 위도.
                double check_Longitude = marker.getPosition().longitude;    // 클릭한 마커의 경도.

                // 보여줄 배열이 가지고 있는 데이터 초기화 작업.
//                this.showData = null;
//                this.showData = new ArrayList<BoardInfo>();

                // 클릭한 마커의 좌표값과 똑같은 좌표값을 가진 게시글만 전체 데이터 배열에서 뽑아온다.
                for(int checkIndex = 0; checkIndex<listCount; checkIndex++){
                    // 전체 데이터 배열의 checkIndex번째의 데이터 위도.
                    double compare_Latitude = Double.parseDouble(listData.get(checkIndex).getLatitude());
                    // 전체 데이터 배열의 checkIndex번째의 데이터 경도.
                    double compare_Logitude = Double.parseDouble(listData.get(checkIndex).getLongtitude());

                    // checkIndex번째의 좌표와 마커의 좌표가 같다면 showData 즉, 보여줄 배열에 데이터를 추가한다.
                    if(compare_Latitude == check_Latitude && compare_Logitude == check_Longitude){
//                        this.showData.add(listData.get(checkIndex));
                        lat = (""+check_Latitude); lngt = (""+check_Longitude);
                    }
                }

                LatLng destination = new LatLng(check_Latitude, check_Longitude); // 카메라 이동 위한 좌표 설정.
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(destination)  // 카메라 이동 메소드.
                        .animate(CameraAnimation.Fly)
                        .finishCallback(new CameraUpdate.FinishCallback() {
                            @Override
                            public void onCameraUpdateFinish() {
                                //Toast.makeText(MainActivity.this, "카메라 이동 완료", Toast.LENGTH_SHORT).show();
                            }
                        });

                // 마커 클릭시 클릭한 마커로 카메라를 이동 시킨다.
                naverMap.setLocationSource(locationSource);
                naverMap.moveCamera(cameraUpdate);

//                Toast.makeText(this,
//                        check_Latitude + ", " + check_Longitude + "의 게시글 개수 : " + this.showData.size(),
//                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MapsActivity.this, MarkerActivity.class);
                startActivity(intent);
                return true;
            });
            marker.setMap(naverMap);
        } // for문 종료.

        /*
            None: 애니메이션 없이 이동합니다. 기본값입니다.
            Linear: 일정한 속도로 이동합니다.
            Easing: 부드럽게 가감속하며 이동합니다. 가까운 거리를 이동할 때 적합합니다.
            Fly: 부드럽게 축소됐다가 확대되며 이동합니다. 먼 거리를 이동할 때 적합합니다.
         */

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(testLocation)
                .animate(CameraAnimation.Fly)
                .finishCallback(new CameraUpdate.FinishCallback() {
                    @Override
                    public void onCameraUpdateFinish() {
                        Toast.makeText(MapsActivity.this, "카메라 이동 완료", Toast.LENGTH_SHORT).show();
                    }
                })
                .cancelCallback(new CameraUpdate.CancelCallback() {
                    @Override
                    public void onCameraUpdateCancel() {
                        Toast.makeText(MapsActivity.this, "카메라 이동 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        naverMap.setLocationSource(locationSource);
        naverMap.moveCamera(cameraUpdate);
    }

    public void Load_BoardList(){
        Log.v("Map test", "Load_BoardList");
        String urlAddr = ConnectValue.baseUrl + "board_List_location.jsp?";
        try{
            MainActivityNetworkTask networkTask = new MainActivityNetworkTask(MapsActivity.this, urlAddr);
            Object object = networkTask.execute().get();

//            Load_AllData networkTask = new Load_AllData(MapsActivity.this, urlAddr);
//            Object object = networkTask.execute().get();
            listData = (ArrayList<BoardInfo>) object;

            Log.v("load_test" , "listData size  : " +listData.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
