package com.example.cluelin.qrorderingsystem.Order.OrderList;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cluelin.qrorderingsystem.Order.OrderDetail.OrderDetailActivity;
import com.example.cluelin.qrorderingsystem.Order.OrderItem;
import com.example.cluelin.qrorderingsystem.R;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    ListView orderList;
    OrderListAdapter orderListAdapter;
    public static ArrayList<OrderItem> orderListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.order_list_title);


        orderList = (ListView) findViewById(R.id.orderList);
        orderListAdapter = new OrderListAdapter(this);

        orderList.setAdapter(orderListAdapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);

                OrderItem selectedOrder = (OrderItem) orderListAdapter.getItem(position);
                intent.putExtra("selectedOrder", (Parcelable)selectedOrder);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Log.d("tag", " 개수 확인 : " + orderListItems.get(0).getOrderedMenuItems().get(0).getQuantity());
        orderListAdapter.notifyDataSetChanged();

    }

    //이전화면
    public void onClickPreView(View v){

        finish();
    }

    //폐기예정
//    public void onClickOrderItem(View v){
//        Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
//        startActivity(intent);
//
//        finish();
//    }
}
