package com.example.cluelin.qrorderingsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.Login.IDObject;
import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.Order.OrderList.OrderListActivity;
import com.example.cluelin.qrorderingsystem.PaymentOption.ManagePaymentOptionActivity;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionItem;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionListActivity;
import com.example.cluelin.qrorderingsystem.QRScanner.QRCameraActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class HomeActivity extends AppCompatActivity {

    Intent loginIntent;
    public static IDObject loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.home_title);

        loginIntent = getIntent();


        if (loginIntent.hasExtra("loginID")) {
            loginID = loginIntent.getParcelableExtra("loginID");

            TextView IDText = (TextView) findViewById(R.id.loginIDText);
            TextView nameText = (TextView) findViewById(R.id.loginNameText);

            IDText.setText(loginID.getId());
            nameText.setText(loginID.getName());
        }

    }

    //  메인 엑티비티에 있는 카드 리스트 버튼을 클릭했을때
//   카드 리스트 액티비티를 호출.
    public void onCardListButtonClicked(View v) {

        openListPayment();
    }

    //카드
    //카드 리스트 액티비티 오픈.
    public void openListPayment() {

        Intent intent = new Intent(getApplicationContext(), PaymentOptionListActivity.class);
        startActivity(intent);

    }

    //QR코드 실행
    public void onClickQRCamera(View v) {

        Intent intent = new Intent(getApplicationContext(), QRCameraActivity.class);
        startActivity(intent);
    }

    //주문내역 확인
    public void onClickOrderList(View v) {
        Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
        startActivity(intent);

    }

    //    로그아웃
    public void onClickLogOut(View v) {

        logOutAction();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    //로그아웃전에 해줘야 할 동작들.
    public void logOutAction() {

        writeData();


        //카드 리스트 삭제
        PaymentOptionListActivity.paymentOptionItems.clear();
        OrderListActivity.orderListItems.clear();

    }

    public void writeData() {
        try {

            FileOutputStream outputStream = openFileOutput("paymentMethodList.txt", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);



            Log.d("toServer", "데이터 전송 시작");
            Log.d("toServer", "" + getFilesDir());

            objectOutputStream.writeObject(PaymentOptionListActivity.paymentOptionItems);
            objectOutputStream.writeObject(OrderListActivity.orderListItems);

            for( PaymentOptionItem obj : PaymentOptionListActivity.paymentOptionItems){
                Log.d("toServer", "기존 결제수단 저장 : " + obj.getCardName() + " , " + obj.getCardNumber() );
            }

            for(OrderItem orderItem : OrderListActivity.orderListItems){
                Log.d("toServer", "기존 주문목록 저장 : " + orderItem.getStoreName());
            }




            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


