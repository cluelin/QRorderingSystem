package com.example.cluelin.qrorderingsystem.PaymentOption;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.R;

/**
 * Created by clue on 2016-04-03.
 */

//    PaymentOptionList에 들어가는 한줄의 View이다.
//    데이터가 추가되면 각 항목에 데이터를 대입시켜주는것만 하면된다.
//    레이아웃은 차후에 바꿔야할 필요가 잇긴하다.
//    PaymentOptionItem에 들어있는 정보를 layout과 붙여주는 객체.
public class PaymentOptionView extends LinearLayout {

    TextView cardNameText;
    TextView cardNumberText;


    public PaymentOptionView(Context context, PaymentOptionItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.payment_option_item, this, true);

        //list_payment_option_item의 정보를 읽어와서 그 레이아웃 안에 있는 textView의 정보를 얻는다.
        cardNameText = (TextView) findViewById(R.id.cardName);
        cardNumberText = (TextView)findViewById(R.id.cardNumber);

        setCardInformation(aItem);


    }


    //카드 정보 출력
    public void setCardInformation(PaymentOptionItem paymentMethod){

        cardNameText.setText(paymentMethod.getCardName());
        cardNumberText.setText(cardInformationHiding(paymentMethod));


    }

    //출력전에 카드 번호 가리기
    private String cardInformationHiding(PaymentOptionItem paymentMethod){

        String array[] = paymentMethod.getCardNumber().split(" ");
        String number = array[ 0 ] + " **** **** " +array[3];
        return number;

    }

}
