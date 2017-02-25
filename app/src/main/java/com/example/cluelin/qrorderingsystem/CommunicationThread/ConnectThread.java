package com.example.cluelin.qrorderingsystem.CommunicationThread;

import android.content.Intent;
import android.util.Log;

import com.example.cluelin.qrorderingsystem.Menu.MenuItem;
import com.example.cluelin.qrorderingsystem.Menu.StoreMenuList.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectThread extends Thread {

    final String serverAddress;
    JSONObject storeInformation;


    static Socket sock;
    Scanner scanner;
    OutputStream outputStream;
    static PrintStream print;

    public ConnectThread(String sAddr, JSONObject storeInformation) {
        serverAddress = sAddr;
        this.storeInformation = storeInformation;

    }

    public void run() {

        try {
            Log.d("toServer", "소켓 접속 시도");
            sock = new Socket(serverAddress, ServerInformation.port);
            Log.d("fromServer", "소켓 접속 성공");

            outputStream = sock.getOutputStream();
            print = new PrintStream(outputStream);
            scanner = new Scanner(sock.getInputStream(), "UTF-8");

            sendStoreInformation(storeInformation);

            receiveStoreMenu();

            MenuActivity.dataChange();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //서버로 QR코드의 정보(가게의 정보)를 전송.
    public void sendStoreInformation(JSONObject storeInforamtion) {
        try {
            print.println(storeInforamtion);
            Log.d("toServer", "소켓으로 가게정보 보내기 : " + storeInforamtion.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //서버에서 가게 메뉴 받아오기.
    public void receiveStoreMenu() {

        try {

            Log.d("fromServer", "서버에서 받아오는 데이터 : " + scanner.hasNext());

            String menuStr = scanner.nextLine();

            Log.d("fromServer", menuStr);

            try {

                JSONArray jarray = new JSONArray(menuStr);

                Log.d("fromServer", "메뉴 데이터(JSON array형태) : " + jarray);

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                    String menuName = jObject.getString("name");
                    int menuPrice = Integer.parseInt(jObject.getString("price"));
                    MenuActivity.storeMenuItems.add(new MenuItem(menuName, menuPrice));
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
