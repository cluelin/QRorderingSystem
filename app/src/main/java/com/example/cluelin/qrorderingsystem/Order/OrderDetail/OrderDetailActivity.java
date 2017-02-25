package com.example.cluelin.qrorderingsystem.Order.OrderDetail;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluelin.qrorderingsystem.CommunicationThread.CheckOutThread;
import com.example.cluelin.qrorderingsystem.CommunicationThread.ModifyThread;
import com.example.cluelin.qrorderingsystem.CommunicationThread.ServerInformation;
import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.Order.OrderList.OrderListActivity;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionItem;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionListActivity;
import com.example.cluelin.qrorderingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    Intent intent;

    public static ArrayList<MenuItem> modifiedMenuItems = new ArrayList<>();


    public static OrderItem selectedOrder;
    //해당 화면에서 리스트로 보여주는 메뉴 목록들
    public static ArrayList<MenuItem> selectedOrderMenuItems;
    public static ArrayList<MenuItem> tempOrderMenuItems = new ArrayList<>();

    //메뉴를 보여주기위한 리스트와 어댑터.
    ListView orderDetailList;
    OrderDetailAdapter orderDetailAdapter;

    //액티비티에 존재하는 버튼들.
    static Button payButton;
    static Button modifyButton;
    static TextView totalPriceText;

    PaymentOptionItem selectedPaymentOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.order_detail_title);

        //레이아웃 아이디 가져오기.
        payButton = (Button) findViewById(R.id.payButton);
        modifyButton = (Button) findViewById(R.id.modifyButton);
        totalPriceText = (TextView) findViewById(R.id.totalPriceText);

        orderDetailList = (ListView) findViewById(R.id.orderDetailList);

        intent = getIntent();

        //인텐트에 포지션 정보가 존재한다면, 해당 포지션에 존재하는 주문정보를 가져온다.
        if (intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);

            //오더 리스트 뷰에서 하나의 오더 아이템을 가지고온다.
            selectedOrder = OrderListActivity.orderListItems.get(position);
            selectedOrderMenuItems = selectedOrder.getOrderedMenuItems();

            //결제 완료된 목록은 결제버튼 비활성화 해준다.
            if(selectedOrder.isCheckout()){
                doneCheckOut();
            }


            Log.d("tag", "원본 사이즈 확인 : " + selectedOrderMenuItems.size());
            tempOrderMenuItems = new ArrayList<>();
            //원본 저장.
            for (int i = 0; i < selectedOrderMenuItems.size(); i++) {

                tempOrderMenuItems.add(new MenuItem(selectedOrderMenuItems.get(i)));
            }

            //어댑터를 하나 만들어서 붙인다.
            orderDetailAdapter = new OrderDetailAdapter(this, selectedOrderMenuItems);
            orderDetailList.setAdapter(orderDetailAdapter);

//            orderDetailList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    return false;
//                }
//            });

            //현재 존재하는 정보를 기반으로 총금액을 계산해서 넣어준다.
            totalPriceText.setText("" + selectedOrder.getTotalPrice());
        }

    }

    //결제완료된 경우 버튼 무력화
    private void doneCheckOut(){

        payButton.setVisibility(View.INVISIBLE);
        modifyButton.setVisibility(View.VISIBLE);
        modifyButton.setText("결제완료");
        modifyButton.setClickable(false);

    }

    //앞페이지로
    public void onClickPreView(View v) {

        selectedOrder.setOrderedMenuItems(tempOrderMenuItems);
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        selectedOrder.setOrderedMenuItems(tempOrderMenuItems);

    }

    //결제 버튼 클릭되었을때.
    public void onClickPayItem(View v) {

        //카드 선택 다이얼로그 생성.
        Dialog payDialog = makePayDialog();

        //다이얼로그 보이기
        payDialog.show();


    }

    //카드 선택 다이얼로그
    private Dialog makePayDialog() {

        final ArrayAdapter<String> adapter;

        final Dialog paymentOptionSelectDialog = new Dialog(this);
        paymentOptionSelectDialog.setContentView(R.layout.select_payment_option_dialog);

        final Spinner spinner = (Spinner) paymentOptionSelectDialog.findViewById(R.id.spinner);
        Button checkOutCancel = (Button) paymentOptionSelectDialog.findViewById(R.id.checkOutCancelButton);
        Button ConfirmCheckOut = (Button) paymentOptionSelectDialog.findViewById(R.id.confirmCheckOutButton);

        //취소버튼.
        checkOutCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                paymentOptionSelectDialog.dismiss();
            }
        });


        //확인버튼.
        ConfirmCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //카드 선택후 확인 버튼이 눌렷을때 PaymentOptionList에서 해당 카드를 가져온다.
                selectedPaymentOption = PaymentOptionListActivity.paymentOptionItems.get(spinner.getSelectedItemPosition());

                Log.d("selectedPaymentOption", selectedPaymentOption.getCardName());

                paymentOptionSelectDialog.dismiss();


                JSONObject checkOutObject = makeCheckOutJSONObject();

                //서버로 데이터 전송 구현 필요.
                CheckOutThread checkOutThread = new CheckOutThread(checkOutObject);
                checkOutThread.start();

                try {

                    checkOutThread.join();

                    if(checkOutThread.getConfirmMessage()){
                        //해당 orderItem에서 결제 여부를 true로 변경.
                        selectedOrder.setCheckout();
                        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다. ", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "결제에 실패 하였습니다. ", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


                //OrderDetailActivity 종료.
                finish();
            }
        });



        //스피너에 카드 목록 등록시켜주는 과정
        ArrayList<String> list = new ArrayList<String>();

        for (PaymentOptionItem item : PaymentOptionListActivity.paymentOptionItems) {
            list.add(item.getCardName());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter); // OK!!

        return paymentOptionSelectDialog;

    }


    //주문 정보 + 결제정보를 JSONObject로 만들어준다.
    public JSONObject makeCheckOutJSONObject() {

        JSONObject checkOutObject = new JSONObject();

        try {
            checkOutObject.put("flag", ServerInformation.CHECK_OUT);
            checkOutObject.put("orderNumber", selectedOrder.getOrderNumber());
            checkOutObject.put("cardNumber", selectedPaymentOption.getCardNumber());
            checkOutObject.put("CVCNumber", selectedPaymentOption.getCardCVCNumber());

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        Log.d("toServer", "결제 된 주문(JSONObject) : " + checkOutObject.toString());

        return checkOutObject;
    }

    //수정 요청용 JSON Object.
    public JSONObject makeModifyJSONObject() {

        JSONObject modifiedObject = new JSONObject();
        JSONArray modifiedObjectArray = new JSONArray();

        try {
            for (MenuItem aItem : modifiedMenuItems) {

                JSONObject JObject = new JSONObject();
                JObject.put("orderNumber", selectedOrder.getOrderNumber());
                JObject.put("name", aItem.getMenuName());
                JObject.put("num", aItem.getQuantity());
                modifiedObjectArray.put(JObject);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        try {
            modifiedObject.put("flag", ServerInformation.MODIFY);
            modifiedObject.put("menu_list", modifiedObjectArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("toServer", "수정 요청 주문(JSONArray) : " + modifiedObject.toString());


        //수정항목 없애기
        modifiedMenuItems.clear();
        return modifiedObject;

    }


    //데이터가 수정되었는지 확인.
    public static void dataModified() {

        for (int i = 0; i < tempOrderMenuItems.size(); i++) {
            //두 주문의 수량 불일치.
            if (tempOrderMenuItems.get(i).getQuantity() != selectedOrderMenuItems.get(i).getQuantity()) {

                //이미 해당 아이템이 들어가잇으면 추가하지않음.
                if (!modifiedMenuItems.contains((MenuItem) selectedOrderMenuItems.get(i))) {
                    modifiedMenuItems.add(selectedOrderMenuItems.get(i));
                    Log.d("toServer", "수정된 아이템 추가 : " + selectedOrderMenuItems.get(i).getMenuName());
                    Log.d("toServer", "수정된 아이템 추가 : " + selectedOrderMenuItems.get(i).getQuantity());
                }
                payButton.setVisibility(View.INVISIBLE);
                modifyButton.setVisibility(View.VISIBLE);

            }
        }
    }

    // 수정 버튼이 눌렷을때의 동작.
    public void onClickModifyItem(View v) {

        JSONObject modifiedObject = makeModifyJSONObject();

        //서버로 수정 요청.
        ModifyThread modifyThread = new ModifyThread(modifiedObject);
        modifyThread.start();


        try {
            modifyThread.join();

            if(modifyThread.getConfirmMessage()){

                Toast.makeText(getApplicationContext(), "주문 내용 수정이 성공 하였습니다.", Toast.LENGTH_SHORT).show();
                tempOrderMenuItems = selectedOrderMenuItems;

            }else {
                Toast.makeText(getApplicationContext(), "주문 내용 수정에 실패 하였습니다. ", Toast.LENGTH_LONG).show();

                //주문을 원래대로 복구.
                selectedOrderMenuItems = tempOrderMenuItems;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        payButton.setVisibility(View.VISIBLE);
        modifyButton.setVisibility(View.INVISIBLE);


    }

    //수정에 따른 총계 재계산.
    public static void resetTotalPrice() {

        totalPriceText.setText("" + selectedOrder.getTotalPrice());
    }
}
