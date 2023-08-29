package com.lyj.direction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class jeju_main_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeju_main_page);

        Toast.makeText(jeju_main_page.this, "뚜벅 제주에 오신 걸 환영합니다.", Toast.LENGTH_LONG).show();


        Button buttonInsert = (Button)findViewById(R.id.creat_cose);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), jeju_calender_b.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 102);

            }
        });

        Button top_button = (Button)findViewById(R.id.top_button);
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), SurveyActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 103);

            }
        });

        Button login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 104);

            }
        });

    }

}