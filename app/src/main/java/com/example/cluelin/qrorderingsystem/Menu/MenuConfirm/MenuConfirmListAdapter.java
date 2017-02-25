package com.example.cluelin.qrorderingsystem.Menu.MenuConfirm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;

import java.util.ArrayList;

/**
 * Created by cluelin on 2016-05-09.
 */
public class MenuConfirmListAdapter extends BaseAdapter {

    private Context context;


    public MenuConfirmListAdapter(Context mcontext){
        context = mcontext;
    }

    @Override
    public int getCount() {
        return MenuConfirmActivity.selectedMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return MenuConfirmActivity.selectedMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuConfrimView menuConfrimView;

        if(convertView == null ){
            menuConfrimView = new MenuConfrimView(context, MenuConfirmActivity.selectedMenuItems.get(position));
        }else {

            menuConfrimView = (MenuConfrimView) convertView;

            menuConfrimView.setSelectedMenuInformation(MenuConfirmActivity.selectedMenuItems.get(position));

        }

        return menuConfrimView;
    }
}
