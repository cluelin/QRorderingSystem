package com.example.cluelin.qrorderingsystem.Menu.StoreMenuList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cluelin.qrorderingsystem.Menu.MenuConfirm.MenuConfirmActivity;
import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.R;

import java.util.ArrayList;

/**
 * Created by cluel on 2016-03-25.
 */
public class MenuActivity extends Activity {

    String storeName;
    ListView menuListView;

    public static MenuListAdapter menuListAdapter;
    public static ArrayList<MenuItem> storeMenuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Intent intent = getIntent();

        if(intent.hasExtra("storeName")){
            storeName = intent.getStringExtra("storeName");
        }

        menuListView = (ListView) findViewById(R.id.menuListView);
        menuListAdapter = new MenuListAdapter(this);

        menuListView.setAdapter(menuListAdapter);

        //메뉴 아이템이 클릭되었을때
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem selectedItem = (MenuItem) menuListAdapter.getItem(position);

                createSelection(selectedItem).show();

            }
        });

    }


// 메뉴아이템이 클릭되었을때
    private AlertDialog createSelection(final MenuItem selectedItem) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setTitle("알림");
        alt_bld.setMessage("주문하시겠습니까?");
        alt_bld.setPositiveButton("예", new DialogInterface.OnClickListener() {
            //주문표에 추가.

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (selectedItem.isSelected()) {

                    //갯수 수정 필요.
                } else {
                    //갯수 추가 필요.
                    selectedItem.setQuantity(1);
                    MenuConfirmActivity.selectedMenuItems.add(selectedItem);
                    selectedItem.setIsSelected(true);
                }
            }
        }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alt_bld.create();

        return dialog;
    }

    public static void dataChange(){
        menuListAdapter.notifyDataSetChanged();
    }


    //다음 버튼 클릭됨
    public void onClickNextButton(View v) {

        //선택한 아이템이 존재하지않을때 인텐트 넘기지 않음.
        if(MenuConfirmActivity.selectedMenuItems.size() == 0){

            Toast.makeText(this, "아이템을 선택해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        //주문 상세 페이지로 이동.
        Intent intent = new Intent(this, MenuConfirmActivity.class);
        intent.putExtra("storeName", storeName);
        startActivity(intent);

        finish();

    }


    //홈화면으로 귀환.
    public void onClickPreView(View v) {

        clearStoreMenuItem();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearStoreMenuItem();
    }

    //메뉴 리스트 초기화
    public void clearStoreMenuItem(){
        storeMenuItems.clear();
    }

}
