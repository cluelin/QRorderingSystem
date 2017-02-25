package com.example.cluelin.qrorderingsystem.Menu.StoreMenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.R;

/**
 * Created by clue on 2016-04-03.
 */
public class MenuView extends LinearLayout {

    TextView menuNameText;
    TextView menuPriceText;

    public MenuView(Context context, MenuItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.menu_item , this, true);

        menuNameText = (TextView) findViewById(R.id.menuNameText);
        menuPriceText = (TextView) findViewById(R.id.menuPriceText);

        setMenuInformation(aItem);
    }

    public void setMenuInformation(MenuItem menuItem){

        menuNameText.setText(menuItem.getMenuName());
        menuPriceText.setText(String.valueOf( menuItem.getMenuPrice()));

    }

}
