package com.example.cluelin.qrorderingsystem.CommunicationThread;

import android.util.Log;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Menu.StoreMenuList.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by cluelin on 2016-05-26.
 */
public class OrderThread extends Thread {

    Scanner scanner;
    OutputStream outputStream;
    PrintStream print;
    JSONObject confirmedMenu;

    static String orderNumber = "주문번호";

    public OrderThread(JSONObject confirmedMenu){
        this.confirmedMenu = confirmedMenu;
    }

    @Override
    public void run() {

        try {
            scanner = new Scanner(ConnectThread.sock.getInputStream(), "UTF-8");
            outputStream = ConnectThread.sock.getOutputStream();
            print = new PrintStream(outputStream);

            sendConfirmedMenu(confirmedMenu);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

        //서버로 주문메뉴 전달하기.
    public void sendConfirmedMenu(JSONObject confirmedMenu){

        print.println(confirmedMenu);
        print.flush();
        Log.d("toServer", "소켓으로 메뉴 보내기 : " + confirmedMenu.toString());

        //메뉴 전송후에 orderNumber받아오기.
        getOrderNumberFromServer();



    }

    private void getOrderNumberFromServer(){

        //여기서 서버에서 주문번호 받아오는 코드.
        try {

            String orderNumberFromServer = scanner.nextLine();

            Log.d("fromServer", "주문 번호 : " + orderNumberFromServer);

            try {

                JSONObject orderNumberObject = new JSONObject(orderNumberFromServer);

                //orderNumber라는 JSON객체에 들어잇는거 빼와서 orderNumber할당해줌
                orderNumber = orderNumberObject.getString("orderNumber");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static String getOrderNumber() {
        return orderNumber;
    }
}
