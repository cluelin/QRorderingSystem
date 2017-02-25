package com.example.cluelin.qrorderingsystem.Menu.StoreMenuList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;

import java.util.List;

/**
 * Created by clue on 2016-04-03.
 */
public class MenuListAdapter extends BaseAdapter {

    private Context mContext;


    public MenuListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(MenuItem item){
        MenuActivity.storeMenuItems.add(item);
    }

    @Override
    public int getCount() {
        return MenuActivity.storeMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return MenuActivity.storeMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MenuView menuView;

        if(convertView == null ){
            menuView = new MenuView(mContext, MenuActivity.storeMenuItems.get(position));
        }else {

            menuView = (MenuView) convertView;

            menuView.setMenuInformation(MenuActivity.storeMenuItems.get(position));

        }

        return menuView;
    }
}
