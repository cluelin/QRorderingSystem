package com.example.cluelin.qrorderingsystem.PaymentOption;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cluelin.qrorderingsystem.R;

import java.util.ArrayList;

/**
 * Created by cluel on 2016-03-28.
 */
public class PaymentOptionListActivity extends AppCompatActivity {

    static final int ADD_PAYMENT_OPTION_REQUEST = 1;
    static final int EDIT_PAYMENT_OPTION_REQUEST = 2;

    ListView paymentOptionList;
    PaymentOptionListAdapter paymentOptionListAdapter;
    public static ArrayList<PaymentOptionItem> paymentOptionItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.payment_option_title);


        paymentOptionList = (ListView) findViewById(R.id.paymentOptionListView);

        paymentOptionListAdapter = new PaymentOptionListAdapter(this);   // 어댑터 생성하면서 context넘기기.


        paymentOptionList.setAdapter(paymentOptionListAdapter);



        //리스트의 아이템이 클릭 되었을때의 동작.
        paymentOptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PaymentOptionItem selectedItem = (PaymentOptionItem) paymentOptionListAdapter.getItem(position);      //선택된 Item을 가져온다.

                openManagePaymentOptionActivity(selectedItem, position);                                       //선택된 Item 수정.


            }
        });

        //아이템을 롱클릭 했을때.
        paymentOptionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removePaymentItem(position);
                return true;
            }
        });


    }

    //삭제를 위한 다이얼로그
    public void removePaymentItem(final int position){


        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle("경고");
        deleteDialog.setMessage("삭제 하시겠습니까?");

        deleteDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        deleteDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                paymentOptionItems.remove(position);

                //지우고 난뒤에 어댑터에 알리기
                paymentOptionListAdapter.notifyDataSetChanged();
            }
        });

        deleteDialog.show();

    }


    //카드 추가 버튼 클릭
    public void onAddPaymentButtonClicked(View v){      //Item 추가 버튼 클릭
        //결제수단 추가할때.
        Intent intent = new Intent(getApplicationContext(), ManagePaymentOptionActivity.class);
        startActivityForResult(intent, ADD_PAYMENT_OPTION_REQUEST);

    }


    //결제수단 편집할때.
    public void openManagePaymentOptionActivity(PaymentOptionItem selectedItem, int position){

        //선택된 카드 객체를 같이 넘김.
        Intent intent = new Intent(this, ManagePaymentOptionActivity.class);
        intent.putExtra("selectedItem", (Parcelable)selectedItem);
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_PAYMENT_OPTION_REQUEST);

    }


    //카드가 추가 혹은 편집되어서 돌아올때.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//      카드가 추가되거나 편집되어서 이화면으로 돌아왔을때
//        이부분도 추가랑 편집 나뉘어야한다.
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_PAYMENT_OPTION_REQUEST && resultCode == RESULT_OK){

            PaymentOptionItem newPaymentOptionItem = (PaymentOptionItem) data.getParcelableExtra("PaymentOptionItem");

            paymentOptionListAdapter.addItem(newPaymentOptionItem);
            paymentOptionListAdapter.notifyDataSetChanged();

        }else if(requestCode ==  EDIT_PAYMENT_OPTION_REQUEST && resultCode == RESULT_OK){

            PaymentOptionItem editedPaymentOptionItem = (PaymentOptionItem) data.getParcelableExtra("PaymentOptionItem");
            int position = data.getIntExtra("position",-1);
            paymentOptionListAdapter.getPaymentOptionItems().set(position,editedPaymentOptionItem );
            paymentOptionListAdapter.notifyDataSetChanged();

        }
    }

    //이전화면으로 귀환.
    public void onClickPreView(View v){

        finish();
    }
}
