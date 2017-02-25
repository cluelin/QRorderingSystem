package com.example.cluelin.qrorderingsystem.Order.OrderList;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.R;

import java.util.zip.Inflater;

/**
 * Created by cluelin on 2016-05-09.
 */
public class OrderListView extends LinearLayout {

    TextView dateText;
    TextView checkOutText;
    TextView storeNameText;
    TextView totalPriceText;

    public OrderListView(Context context, OrderItem orderItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.order_list_item, this, true);

        dateText = (TextView) findViewById(R.id.dateText);
        checkOutText = (TextView) findViewById(R.id.checkOutText);
        storeNameText = (TextView) findViewById(R.id.storeNameText);
        totalPriceText = (TextView) findViewById(R.id.totalPriceText);

        setOrder(orderItem);
    }

    public void setOrder(OrderItem orderItem){

        dateText.setText(orderItem.getOrderDate());
        storeNameText.setText(orderItem.getStoreName());

        if(orderItem.isCheckout()){
            checkOutText.setText("결제완료");
        }else
            checkOutText.setText("결제요망");

        totalPriceText.setText("" + orderItem.getTotalPrice());

    }
}
