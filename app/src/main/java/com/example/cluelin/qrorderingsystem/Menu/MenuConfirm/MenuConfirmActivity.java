package com.example.cluelin.qrorderingsystem.Menu.MenuConfirm;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.cluelin.qrorderingsystem.CommunicationThread.OrderThread;
import com.example.cluelin.qrorderingsystem.CommunicationThread.ServerInformation;
import com.example.cluelin.qrorderingsystem.HomeActivity;
import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Menu.StoreMenuList.MenuActivity;
import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.Order.OrderList.OrderListActivity;
import com.example.cluelin.qrorderingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuConfirmActivity extends AppCompatActivity {

    String storeName;
    ListView selectedMenuListView;
    MenuConfirmListAdapter menuConfirmListAdapter;

    public static ArrayList<MenuItem> selectedMenuItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_confirm_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.menu_confirm_title);

        Intent intent = getIntent();

        if(intent.hasExtra("storeName")){
            storeName = intent.getStringExtra("storeName");
        }

        selectedMenuListView = (ListView) findViewById(R.id.selectedMenuList);

        menuConfirmListAdapter = new MenuConfirmListAdapter(this);

        selectedMenuListView.setAdapter(menuConfirmListAdapter);
    }

    //주문버튼 누르는 순간.
    public void onClickOrderButton(View v) {

        Date date = new Date();


        String orderNumber = sendOrder();

        Log.d("fromServer", "서버에서 발행한 주문번호 : " + orderNumber);

        //서버로 받아온 orderNumber을 포함한 새로운 order객체 생성

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        OrderItem newOrder = new OrderItem(storeName, simpleDateFormat.format(date), orderNumber, selectedMenuItems, false);
        OrderListActivity.orderListItems.add(newOrder);

        Log.d("orderItem", "새로 발생된 OrderItem" + newOrder.toString());


        //주문후 리스트 클리어.
        selectedMenuItems.clear();
        MenuActivity.storeMenuItems.clear();

        finish();
    }

    //서버로 오더 전송
    private String sendOrder(){

        JSONObject confirmedMenu = makeMenuToJSONObject();


        OrderThread orderThread = new OrderThread(confirmedMenu);
        orderThread.start();

        String orderNumber = "";
        try{

            orderThread.join();
            orderNumber = orderThread.getOrderNumber();
        }catch (Exception e){
            e.printStackTrace();
        }

        return orderNumber;
    }

    //서버로 주문 메뉴를 전송하기위한 JSON만들기
    public JSONObject makeMenuToJSONObject(){
        JSONArray jsonArray = new JSONArray();

        for(MenuItem m : selectedMenuItems){

            // {음식명, 수량} 배열로 만들기
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("name", m.getMenuName());
                jObj.put("num", "" + m.getQuantity());
                jsonArray.put(jObj);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }

        Log.d("toServer", "주문 목록 : " + jsonArray.toString());


        // {flag, {음식명, 수량}} 으로 만들기.
        JSONObject orderJSON = new JSONObject();
        try {
            orderJSON.put("flag", ServerInformation.ORDER);
            orderJSON.put("menu_list", jsonArray);
            orderJSON.put("phone_number", HomeActivity.loginID.getPhonenum());

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return orderJSON;
    }

    @Override
    protected void onPause() {
        super.onPause();

        selectedMenuItems.clear();
        MenuActivity.storeMenuItems.clear();

    }

}
