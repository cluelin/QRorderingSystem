package com.example.cluelin.qrorderingsystem.Order;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by cluelin on 2016-05-09.
 */
public class OrderItem implements Parcelable, Serializable{

    private String storeName;
    private String orderDate;
    private String orderNumber;
    private ArrayList orderedMenuItems = new ArrayList();
    private boolean isCheckout = false;



    public OrderItem(Parcel source){
        storeName = source.readString();
        orderDate = source.readString();
        orderNumber = source.readString();
        source.readTypedList(orderedMenuItems, MenuItem.CREATOR);


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(storeName );
        dest.writeString(orderDate );
        dest.writeString(orderNumber);
        dest.writeTypedList(orderedMenuItems);


    }

    public OrderItem(String _storeName, String _orderDate, String _orderNumber, ArrayList<MenuItem> _orderedMenuItems, boolean _isCheckout){

        storeName = _storeName;
        //새로운 order메뉴아이템 리스트를 하나 만들어준다.
        orderedMenuItems = (ArrayList< MenuItem>) _orderedMenuItems.clone();
        orderNumber = _orderNumber;
        orderDate = _orderDate;
        isCheckout = _isCheckout;

    }

    public ArrayList<MenuItem> getOrderedMenuItems() {
        return orderedMenuItems;
    }

    public void setOrderedMenuItems(ArrayList orderedMenuItems) {
        this.orderedMenuItems = orderedMenuItems;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setCheckout() {

        isCheckout = true;
    }

    public boolean isCheckout() {
        return isCheckout;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getTotalPrice() {

        int totalPrice = 0;

        //order내부에있는 메뉴 아이템 리스트에서 모든 아이템 가격 * 아이템 개수로 총 합계를 계산한다.
        for(int i = 0 ; i < getOrderedMenuItems().size() ; i++){

            Log.d("tag" , getOrderedMenuItems().get(i).getQuantity() + "*" + getOrderedMenuItems().get(i).getMenuPrice());
            totalPrice= totalPrice + ( getOrderedMenuItems().get(i).getQuantity() * getOrderedMenuItems().get(i).getMenuPrice() );
        }
        return totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }




    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object[] newArray(int size) {
            return new OrderItem[size];
        }

        @Override
        public Object createFromParcel(Parcel source) {
            return new OrderItem(source);
        }
    };
}
