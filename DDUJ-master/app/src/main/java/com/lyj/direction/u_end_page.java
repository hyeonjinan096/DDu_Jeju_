package com.lyj.direction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class u_end_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.u_end_page);


        Button top_button = (Button)findViewById(R.id.next_button);
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent =new Intent(getApplicationContext(), jeju_dayplan.class);//코멘트엑티비티자바랑 연결

                //startActivityForResult(intent, 103);

            }
        }); //dqyplan 으로 넘어가기


    }

}