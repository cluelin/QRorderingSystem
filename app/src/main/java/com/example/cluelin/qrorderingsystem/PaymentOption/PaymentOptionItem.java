package com.example.cluelin.qrorderingsystem.PaymentOption;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by clue on 2016-04-02.
 */

//    PaymentOptionList의 한줄의 항목 데이터에 해당한다고 보면된다.
//    차후에 리스트의 하나의 항목에서 관리해야 하는 데이터가 늘어나거나 줄어들면 여길 관리해 줘야한다.
//    카드 추가에 사용되는 클래스랑 일치화 시킬것인지 분리시킬것인지..(보안문제) 한번 생각해봐야한다.
public class PaymentOptionItem implements Parcelable, Serializable {

    private String cardName;
    private String cardNumber;
    private String cardExpireMonth;
    private String cardExpireYear;
    private String cardCVCNumber;
    private String cardPassword;



    public PaymentOptionItem(String cardName, String cardNumber, String cardExpireMonth,
                             String cardExpireYear, String cardCVCNumber, String cardPassword) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardExpireMonth = cardExpireMonth;
        this.cardExpireYear = cardExpireYear;
        this.cardCVCNumber = cardCVCNumber;
        this.cardPassword = cardPassword;
    }

    public PaymentOptionItem(Parcel source){
        cardName = source.readString();
        cardNumber = source.readString();
        cardExpireMonth = source.readString();
        cardExpireYear = source.readString();
        cardCVCNumber = source.readString();
        cardPassword = source.readString();
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpireMonth() {
        return cardExpireMonth;
    }

    public String getCardExpireYear() {
        return cardExpireYear;
    }

    public String getCardCVCNumber() {
        return cardCVCNumber;
    }

    public String getCardPassword() {
        return cardPassword;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(cardName);
        dest.writeString(cardNumber);
        dest.writeString(cardExpireMonth);
        dest.writeString(cardExpireYear);
        dest.writeString(cardCVCNumber);
        dest.writeString(cardPassword);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public PaymentOptionItem createFromParcel(Parcel source) {
            return new PaymentOptionItem(source);
        }

        @Override
        public PaymentOptionItem[] newArray(int size) {
            return new PaymentOptionItem[size];
        }
    };


}
