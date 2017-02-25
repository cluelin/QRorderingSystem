package com.example.cluelin.qrorderingsystem.Menu.MenuConfirm;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Order.OrderDetail.OrderDetailActivity;
import com.example.cluelin.qrorderingsystem.R;

public class MenuConfrimView extends LinearLayout {

    TextView menuNameText;
    TextView menuPriceText;
    EditText quantityEdit;
    TextView totalPriceText;

    MenuItem currentItem;

    public MenuConfrimView(Context context, MenuItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.menu_confrimed_item, this, true);

        menuNameText = (TextView) findViewById(R.id.menuNameTextView);
        menuPriceText = (TextView) findViewById(R.id.menuPriceTextView);
        quantityEdit = (EditText) findViewById(R.id.quentityEditText);
        totalPriceText = (TextView) findViewById(R.id.subTotalPriceTextView);


        CustomTextWhatcher customTextWhatcher = new CustomTextWhatcher();
        quantityEdit.addTextChangedListener(customTextWhatcher);

        currentItem = aItem;

        quantityEdit.setText("" + aItem.getQuantity());
        setSelectedMenuInformation(aItem);
    }

    public void setSelectedMenuInformation(MenuItem aItem){


        menuNameText.setText(aItem.getMenuName());
        menuPriceText.setText(""+aItem.getMenuPrice());

//        int quantity = Integer.parseInt(quantityEdit.getText().toString());
//
//        aItem.setQuantity(quantity);


        quantityEdit.setText(""+aItem.getQuantity());
        totalPriceText.setText("" + aItem.getQuantity()* aItem.getMenuPrice());

    }

    class CustomTextWhatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            int quantity = 0;
            try {
                //개수가 수정된 다음에 한번 값을 읽어와본다.
                quantity = Integer.parseInt(quantityEdit.getText().toString());

            }catch (Exception e){
            }


            currentItem.setQuantity(quantity);
        }
    }
}
