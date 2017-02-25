package com.example.cluelin.qrorderingsystem.Order.OrderDetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;

import java.util.ArrayList;

/**
 * Created by cluelin on 2016-05-12.
 */
public class OrderDetailAdapter extends BaseAdapter {

    private Context context;
    ArrayList<MenuItem> orderedMenuItems;


    public OrderDetailAdapter(Context _context, ArrayList<MenuItem> orderedMenuItems ) {
        context = _context;
        this.orderedMenuItems = orderedMenuItems;
    }

    @Override
    public int getCount() {
        return orderedMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderedMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //메뉴 한줄에 해당하는 뷰를 정의.
        OrderDetailView orderDetailView;

        if(convertView == null){

//            생성자로 context, 메뉴 리스트에서 순서대로 하나의 행씩 만들어준다.
            orderDetailView = new OrderDetailView(context, orderedMenuItems.get(position));;
        }else {
            orderDetailView = (OrderDetailView) convertView;
            orderDetailView.setSelectedMenuInformation(orderedMenuItems.get(position));
        }

        return orderDetailView;
    }
}
