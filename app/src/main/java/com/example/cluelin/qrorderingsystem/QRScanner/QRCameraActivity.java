package com.example.cluelin.qrorderingsystem.QRScanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cluelin.qrorderingsystem.CommunicationThread.ConnectThread;
import com.example.cluelin.qrorderingsystem.Menu.StoreMenuList.MenuActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCameraActivity extends AppCompatActivity {

    String storeName;
    String storeID;
    String tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //QR스캐너 사용.
        useQRscanner();

    }

    //  스캐너 사용.
    public void useQRscanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(QRScanner.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//qr코드에서 데이터받기
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        String QRString = result.getContents().toString();

        //155.230.52.66:김밥천국:2:1
        //QR코드 내부 데이터 포맷은 "서버IP:가게이름:가게ID:테이블넘버" 4개 레코드
        String arry[] = QRString.split(":");

        for (String s : arry) {
            Log.d("fromServer", "QR코드 에서 읽어낸 정보 : " + s);
        }

        //읽어낸 데이터를 각 String에 넘겨주고.
        String serverAddress = arry[0].trim();
        storeName = arry[1];
        storeID = arry[2];
        tableNumber = arry[3];

        //서버 주소 확인.
        Toast.makeText(this, serverAddress, Toast.LENGTH_SHORT).show();


        connectWithServer(serverAddress);


        openMenuActivity();



    }

//    서버로 연결
    private void connectWithServer(String serverAddress){

        try{

            //서버로 보낼 데이터 생성.(가게 정보)
            JSONObject storeInformation  =  makeStoreInformation();

            // serverAddress 정보로 쓰레드 생성.(서버와 연결)
            ConnectThread connectThread = new ConnectThread(serverAddress, storeInformation);
            connectThread.start();

            //쓰레드 종료되길 기다림.
            connectThread.join();

        }catch (Exception e){
            e.printStackTrace();
            Log.d("fromServer", "메뉴 읽어오는데 실패했습니다!");
            finish();
        }

    }

    //메뉴 정보 받아오기
    private void openMenuActivity(){

        //메뉴 액티비티 호출
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("storeName", storeName);
        startActivity(intent);
        finish();

    }


    //StoreInformation을 JSONObject로 만들어줌.
    public JSONObject makeStoreInformation(){
        //QR코드 정보 전송.
        JSONObject storeInformation = new JSONObject();
        try {
            storeInformation.put("restaurant_name", storeName);
            storeInformation.put("restaurant_id", storeID);
            storeInformation.put("table_num", tableNumber);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        Log.d("toServer", "가게 정보(JSONObject) : " +storeInformation.toString());

        return storeInformation;
    }
//
//    버튼 동작 폐기.
//    //버튼으로 동작하는경우.
//    public void onClickReceiveData(View v){
//
//        setMenuList();
//        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//        startActivity(intent);
//
//
//        finish();
//    }
//
//
//    //메뉴리스트 설정
//    private void setMenuList() {
//
//        //하드코딩
//
//        String menuStr = "[{\"name\":\"봉구스밥버거\",\"price\":\"1800\"},"+
//                    "{\"name\":\"햄밥버거\",\"price\":\"2300\"},"+
//                    "{\"name\":\"치즈밥버거\",\"price\":\"2300\"}]";
//
//        try {
//
//            JSONArray jarray = new JSONArray(menuStr);
//
//            for(int i = 0 ; i < jarray.length() ; i++){
//                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
//                String menuName = jObject.getString("name");
//                int menuPrice = Integer.parseInt(jObject.getString("price"));
//                MenuActivity.storeMenuItems.add(new MenuItem(menuName, menuPrice));
//            }
//
//
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//
//    }
}
