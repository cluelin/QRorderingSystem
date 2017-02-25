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
public class ModifyThread extends Thread {

    JSONObject modifyObject;

    Scanner scanner;
    OutputStream outputStream;
    PrintStream print;


    private String confirmMessage = "";
    private boolean isMotified;

    public ModifyThread(JSONObject modifyArray){
        this.modifyObject = modifyArray;
    }

    @Override
    public void run() {

        try {
            scanner = new Scanner(ConnectThread.sock.getInputStream(), "UTF-8");
            outputStream = ConnectThread.sock.getOutputStream();
            print = new PrintStream(outputStream);

            sendModifyObject(modifyObject);

            isMotified = receiveConfrimMessage();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendModifyObject(JSONObject modifyArray){

        print.println(modifyArray);
        Log.d("toServer", "서버로 수정 메뉴 보내기 : " + modifyArray.toString());

    }

    private boolean receiveConfrimMessage(){
        try {

            confirmMessage = scanner.nextLine();

            Log.d("fromServer", "결제 확인 : " + confirmMessage);

            try {

                JSONObject orderNumberObject = new JSONObject(confirmMessage);

                confirmMessage = orderNumberObject.getString("modify");

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

        return isMotified;
    }
}
