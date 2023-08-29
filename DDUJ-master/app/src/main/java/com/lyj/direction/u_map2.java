package com.lyj.direction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class u_map2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.u_map2);



        Button buttonInsert = (Button)findViewById(R.id.backbutton);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent =new Intent(getApplicationContext(), tour_MainActivity.class);//코멘트엑티비티자바랑 연결
                //startActivityForResult(intent, 102);

            }
        }); //동선 재설정하기

        Button top_button = (Button)findViewById(R.id.next_button);
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), u_end_page.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 103);

            }
        }); //동선 확정하기


    }

}