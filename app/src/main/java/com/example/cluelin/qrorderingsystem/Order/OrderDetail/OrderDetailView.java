package com.example.cluelin.qrorderingsystem.Order.OrderDetail;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Order.OrderList.OrderListActivity;
import com.example.cluelin.qrorderingsystem.R;

/**
 * Created by cluelin on 2016-05-12.
 */
public class OrderDetailView extends LinearLayout {

    TextView menuNameText;
    TextView menuPriceText;
    EditText quantityEdit;
    TextView subTotalPriceText;
    MenuItem currentItem;


    public OrderDetailView(Context context, MenuItem menuItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_confrimed_item, this, true);

        //한줄에 있는 모든 항목을 설정해준다.
        menuNameText = (TextView) findViewById(R.id.menuNameTextView);
        menuPriceText = (TextView) findViewById(R.id.menuPriceTextView);
        quantityEdit = (EditText) findViewById(R.id.quentityEditText);
        subTotalPriceText = (TextView) findViewById(R.id.subTotalPriceTextView);

        currentItem = menuItem;

        CustomTextWhatcher customTextWhatcher = new CustomTextWhatcher();
        quantityEdit.addTextChangedListener(customTextWhatcher);

        //먼저 들어있던 개수를 설정해준다.
        quantityEdit.setText("" + menuItem.getQuantity());

        setSelectedMenuInformation(menuItem);

    }

    public void setSelectedMenuInformation(MenuItem aItem) {


        //각 메뉴의 이름과 가격을 설정해준다.
        menuNameText.setText(aItem.getMenuName());
        menuPriceText.setText("" + aItem.getMenuPrice());


    }

    class CustomTextWhatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //Quantity 변경.
        @Override
        public void afterTextChanged(Editable s) {

            int quantity = 0;
            try {
                //개수가 수정된 다음에 한번 값을 읽어와본다.
                quantity = Integer.parseInt(quantityEdit.getText().toString());


            } catch (Exception e) {
//                Int로 변형되지않을때의 Text 형태.
                Log.d("tag", quantityEdit.getText().toString());
            }

//             EditText에서 읽어온 값을 Quantity로 설정해준다.
            currentItem.setQuantity(quantity);

            Log.d("dataModified", "토탈 값 재설정");
            OrderDetailActivity.dataModified();

            OrderDetailActivity.resetTotalPrice();

            //소계는 수정된 개수 * 해당 아이템의 가격으로 구한다.
            subTotalPriceText.setText("" + currentItem.getQuantity() * currentItem.getMenuPrice());


        }
    }
}


