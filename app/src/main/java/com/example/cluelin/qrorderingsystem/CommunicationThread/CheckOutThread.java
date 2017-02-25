package com.example.cluelin.qrorderingsystem.CommunicationThread;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by cluelin on 2016-05-26.
 */
public class CheckOutThread extends Thread{

    JSONObject checkoutObject;

    Scanner scanner;
    OutputStream outputStream;
    PrintStream print;

    private String confirmMessage = "";
    private boolean isCheckOuted;

    public CheckOutThread(JSONObject checkoutObject){

        this.checkoutObject = checkoutObject;
    }

    public void run(){

        try {
            scanner = new Scanner(ConnectThread.sock.getInputStream(), "UTF-8");
            outputStream = ConnectThread.sock.getOutputStream();
            print = new PrintStream(outputStream);

            sendCheckOutInformation(checkoutObject);
            isCheckOuted = receiveConfrimMessage();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void sendCheckOutInformation(JSONObject checkoutObject){

        print.println(checkoutObject);
        Log.d("toServer", "서버로 결제정보 보내기 : " + checkoutObject.toString());

    }

    public boolean receiveConfrimMessage(){
        try {

            confirmMessage = scanner.nextLine();

            Log.d("fromServer", "결제 확인 : " + confirmMessage);

            try {

                JSONObject orderNumberObject = new JSONObject(confirmMessage);

                confirmMessage = orderNumberObject.getString("orderConfirm");

                if(confirmMessage.equals("true")){
                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    public boolean getConfirmMessage(){

        return isCheckOuted;
    }

}
