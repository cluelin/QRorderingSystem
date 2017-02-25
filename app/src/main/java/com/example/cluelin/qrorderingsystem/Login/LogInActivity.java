package com.example.cluelin.qrorderingsystem.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cluelin.qrorderingsystem.HomeActivity;
import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.Order.OrderList.OrderListActivity;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionItem;
import com.example.cluelin.qrorderingsystem.PaymentOption.PaymentOptionListActivity;
import com.example.cluelin.qrorderingsystem.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    static final int ADD_ID_REQUEST = 1;


    EditText text_id;
    EditText text_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        text_id = (EditText) findViewById(R.id.editText_id);
        text_pass = (EditText) findViewById(R.id.editText_pass);

        text_id = (EditText) findViewById(R.id.editText_id);
        text_pass = (EditText) findViewById(R.id.editText_pass);

    }

    //회원가입 버튼 리스너.
    public void onClickSignUp(View v) {

//        회원가입창으로 이동.
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, ADD_ID_REQUEST);
    }

    //    회원 가입창에서 넘어온 ID객체를 핸들링
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        회원가입에서 넘어온 ID객체를 저장해줌.
        if (requestCode == ADD_ID_REQUEST && resultCode == RESULT_OK && data.hasExtra("newID")) {

            //새로만들어온 ID값 저장. (나중에 DB화 하던지...)
            IDObject newID = data.getParcelableExtra("newID");

        }
    }

    //로그인 버튼이 눌렷을때.
    public void onClickLogIn(View v) {
//        입력한 ID가 ID DB에 일치한것이 잇는지 확인하고, HOME activity로 해당 값 전송.


        //테스트 아이디.
        IDObject testID = new IDObject(text_id.getText().toString(), "test", "Tester", "0101", "010-9999-9999");

        //입력한 ID와 회원가입한 ID가 일치하는지 확인하는 method 필요.
        if (!matchingID(testID)) {

            Log.d("tag", "로그인 실패, 아이디 : " + testID.getId());
            Toast.makeText(this, "아이디를 다시 확인해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }


        Log.d("tag", "로그인 성공 : 로그인 아이디 : " + testID.getId());

        //로그인 성공.
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

        intent.putExtra("loginID", testID);


        reLoadData();           //기존데이터 추가
        startActivity(intent);

    }


    //기존 데이터 불러오기
    public void reLoadData() {

        readFile();
    }

    //파일 입출력
    public void readFile() {
        try {

            FileInputStream fileInputStream = openFileInput("paymentMethodList.txt");

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


            ArrayList<PaymentOptionItem> tempPaymentOptionList = (ArrayList) objectInputStream.readObject();
            ArrayList<OrderItem> tempOrderList = (ArrayList) objectInputStream.readObject();


            for (PaymentOptionItem obj : tempPaymentOptionList) {
                Log.d("fromServer", "기존 결제수단 읽어오기 : " + obj.getCardName() + " , " + obj.getCardNumber());
            }

            for (OrderItem obj : tempOrderList) {
                Log.d("fromServer", "기존 주문목록 읽어오기 : " + obj.getStoreName() + " , " + obj.isCheckout());
            }

            PaymentOptionListActivity.paymentOptionItems = (ArrayList) tempPaymentOptionList.clone();
            OrderListActivity.orderListItems = (ArrayList) tempOrderList.clone();


            objectInputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    //ID verification
    public boolean matchingID(IDObject ido) {

        if (ido.getId().equals("tester"))
            return true;
        else
            return false;

    }




}
