package com.example.cluelin.qrorderingsystem.Menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by clue on 2016-04-03.
 */
public class MenuItem implements Parcelable, Cloneable , Serializable{

    private String menuName;
    private int menuPrice;
    private boolean isSelected = false;
    private int quantity;

    public MenuItem(MenuItem menuItem){

        this.menuName = menuItem.getMenuName();
        this.menuPrice = menuItem.getMenuPrice();
        this.isSelected = menuItem.isSelected();
        this.quantity = menuItem.getQuantity();

    }

    public MenuItem(Parcel source) {
        menuName = source.readString();
        menuPrice = source.readInt();
        quantity = source.readInt();
    }

    public MenuItem(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.quantity = 0;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuName);
        dest.writeInt(menuPrice);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object[] newArray(int size) {
            return new MenuItem[size];
        }

        @Override
        public Object createFromParcel(Parcel source) {
            return new MenuItem(source);
        }
    };

    @Override
    protected MenuItem clone() throws CloneNotSupportedException {

        MenuItem menuItem = (MenuItem) super.clone();

        menuItem.menuName = this.getMenuName();
        menuItem.menuPrice = this.getMenuPrice();
        menuItem.isSelected = this.isSelected();
        menuItem.quantity = this.getQuantity();

        return menuItem;


    }
}
