package com.example.cluelin.qrorderingsystem.PaymentOption;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cluelin.qrorderingsystem.R;

/**
 * Created by cluel on 2016-03-25.
 */
public class ManagePaymentOptionActivity extends Activity {         //결제 수단 관리 액티비티.

    static final int ADD_PAYMENT_OPTION_REQUEST = 1;
    static final int PAYMENT_OPTION_LIST_REQUEST = 1;

    Intent intent;
    PaymentOptionItem selectedItem = null;

    EditText cardNameEdit;

    EditText cardNumberEdit[] = new EditText[4];

    EditText expireMonThEditText, expireYearEditText;
    EditText CVCvalueEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option_manage_layout);

        intent = getIntent();

        //카드이름
        cardNameEdit = (EditText) findViewById(R.id.inputCardNameEditText);

        //카드 넘버
        cardNumberEdit[0] = (EditText) findViewById(R.id.inputCardNumberEdit1);
        cardNumberEdit[1] = (EditText) findViewById(R.id.inputCardNumberEdit2);
        cardNumberEdit[2] = (EditText) findViewById(R.id.inputCardNumberEdit3);
        cardNumberEdit[3] = (EditText) findViewById(R.id.inputCardNumberEdit4);

        //유효기간
        expireMonThEditText = (EditText) findViewById(R.id.inputExpireMonth);
        expireYearEditText = (EditText) findViewById(R.id.inputExpireYear);

        //CVC, 비밀번호
        CVCvalueEditText = (EditText) findViewById(R.id.inputCVCEditText);
        passwordEditText = (EditText) findViewById(R.id.inputPassword);

        //편집이라면..
        if (intent.hasExtra("selectedItem")) {

            loadRegistedCardData();


        }
    }

    private void loadRegistedCardData(){

        selectedItem = intent.getParcelableExtra("selectedItem");

        cardNameEdit.setText(selectedItem.getCardName());

        String tempCardNumber = selectedItem.getCardNumber().replaceAll("\\s", "");

        for (int i = 0; i < 4; i++) {
            cardNumberEdit[i].setText(tempCardNumber.substring(0 + 4 * i, 4 + 4 * i));
        }

        expireMonThEditText.setText(selectedItem.getCardExpireMonth());
        expireYearEditText.setText(selectedItem.getCardExpireYear());

        CVCvalueEditText.setText(selectedItem.getCardCVCNumber());
        passwordEditText.setText(selectedItem.getCardPassword());

    }


    //입력된 카드 이름을 받아온다.
    public String getCardName() {
        return cardNameEdit.getText().toString();
    }

    //입력받은 카드 번호를 받아온다.
    public String getCardNumber() {
//      네개의 EditText로 입력받은 카드번호를 하나의 문자열로 만들어서 반환.
        String cardNumber = "";

        for (int i = 0; i < 4; i++) {
            cardNumber += cardNumberEdit[i].getText().toString() + " ";
        }
        Log.d("tag", cardNumber);
        return cardNumber;
    }

    //유효기간(월)을 받아온다.
    public String getExpireMonth() {
        return expireMonThEditText.getText().toString();
    }

    //유효기간(연도)을 받아온다.
    public String getExpireYear() {

        return expireYearEditText.getText().toString();
    }

    //CVC번호를 바아온다.
    public String getCVCvalue() {
        return CVCvalueEditText.getText().toString();
    }

    //비밀번호를 받아온다.
    public String getPassword() {
        return passwordEditText.getText().toString();
    }


    //저장취소 버튼 클릭
    public void onCancelButtonClicked(View v) {

        finish();
    }

    //저장 버튼이 눌렸을때
    public void onSaveButtonClicked(View v) {

        //입력값 validation.
        if (!inputValidation()) {
            return;
        }

        //invalidation에서 카드 번호를 검증하고 나서는 버려버리는게 좋지않나..? 아닌가 결제모듈로 정보를 보내야되니까 보관해야하나
        PaymentOptionItem newItem = new PaymentOptionItem(getCardName(), getCardNumber(),
                getExpireMonth(), getExpireYear(),
                getCVCvalue(), getPassword());

        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
        toPaymentOptionListActivity(newItem);
        finish();

    }

    //입력항목중 잘못된 항목이 있는지 검사
    public boolean inputValidation() {


        for (int i = 0; i < 4; i++) {
            if (cardNumberEdit[i].getText().toString().length() != 4) {
                Toast.makeText(this, "카드 번호 항목이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        //유효기간 영역 (수정필요)
        if (Integer.parseInt(getExpireMonth()) < 1 || Integer.parseInt(getExpireMonth()) > 12 ||
                Integer.parseInt(getExpireYear()) < 1) {

            Toast.makeText(this, "유효기간 항목이 잘못되었습니다.", Toast.LENGTH_LONG).show();
            return false;
        }


        //CVC영역
        if (getCVCvalue().length() != 3) {
            Toast.makeText(this, "CVC 항목이 잘못되었습니다.", Toast.LENGTH_LONG).show();

            return false;
        }

        //Password
        if (getPassword().length() != 4) {

            Toast.makeText(this, "비밀번호 항목이 잘못되었습니다.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    //새로 만든 결제정보를 리스트 액티비티로 넘김
    public void toPaymentOptionListActivity(PaymentOptionItem newItem) {
//        이전의 엑티비티로 새로운 카드 객체 송신.(시리얼라이제이션 필요)
        intent.putExtra("PaymentOptionItem", (Parcelable) newItem);

        if(intent.hasExtra("position")){
            intent.putExtra("position", intent.getIntExtra("position",-1));
        }
        setResult(RESULT_OK, intent);
    }
}
