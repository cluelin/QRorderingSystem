package com.example.cluelin.qrorderingsystem.Order.OrderList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by cluelin on 2016-05-09.
 */
public class OrderListAdapter extends BaseAdapter {


    Context context;
    public OrderListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return OrderListActivity.orderListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return OrderListActivity.orderListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        OrderListView orderListView;

        if(convertView == null){
            orderListView = new OrderListView(context, OrderListActivity.orderListItems.get(position));
        }else{
            orderListView = (OrderListView) convertView;
            orderListView.setOrder(OrderListActivity.orderListItems.get(position));
        }



        return orderListView;
    }
}
