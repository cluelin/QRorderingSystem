package com.example.cluelin.qrorderingsystem.Login;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cluelin.qrorderingsystem.R;

public class SignUpActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.sign_up_title);

        intent = getIntent();
    }

    public void onClick_login(View v){

        EditText text_id = (EditText) findViewById(R.id.editText_id);
        EditText text_pass = (EditText) findViewById(R.id.editText_pass);
        EditText text_name = (EditText) findViewById(R.id.editText_name);
        EditText text_birth = (EditText) findViewById(R.id.editText_birth);
        EditText text_num = (EditText) findViewById(R.id.editText_phonenum);


        String id = text_id.getText().toString();
        String pw = text_pass.getText().toString();
        String name = text_name.getText().toString();
        String birth = text_birth.getText().toString();
        String phonenum = text_num.getText().toString();


        //새로운 아이디 생성
        IDObject newIDObject = new IDObject(id, pw, name, birth, phonenum);


        intent.putExtra("newID", newIDObject);

        setResult(RESULT_OK, intent);

        finish();


    }

    public void onClickPreView(View v){

        finish();
    }


}
