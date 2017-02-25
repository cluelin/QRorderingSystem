package com.example.cluelin.qrorderingsystem.PaymentOption;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by clue on 2016-04-03.
 */

//payment optionList를 위한 어댑터 클래스.
public class PaymentOptionListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PaymentOptionItem> paymentOptionItems = PaymentOptionListActivity.paymentOptionItems;    //결제 수단 목록 PaymentOptionItem이 element로 들어간다

    public PaymentOptionListAdapter(Context context) {
        this.mContext = context;
    }

    public void addItem(PaymentOptionItem item) {
        paymentOptionItems.add(item);                                                               //결제 수단 추가.
    }

    @Override
    public int getCount() {
        return paymentOptionItems.size();                                                           //현재 리스트에 존재하는 item 갯수.
    }

    @Override
    public Object getItem(int position) {

        return paymentOptionItems.get(position);                                                    //해당 포지션에 있는 item반환
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PaymentOptionView paymentOptionView;

        if (convertView == null) {                                                                  //convertView는 리스트뷰에서 놀고잇는 뷰에 다시 할당하는.. 최적화작업

            paymentOptionView = new PaymentOptionView(mContext, paymentOptionItems.get(position));  //리스트의 한줄에 해당하는 paymentOptionView를 생성

        } else {

            paymentOptionView = (PaymentOptionView) convertView;

            paymentOptionView.setCardInformation(paymentOptionItems.get(position));
        }

        return paymentOptionView;
    }

    public ArrayList<PaymentOptionItem> getPaymentOptionItems() {
        return paymentOptionItems;
    }
}
