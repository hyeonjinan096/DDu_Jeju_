package com.lyj.direction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lyj.direction.search_view.lodging_MainActivity;
import com.lyj.direction.search_view.restaurant_MainActivity;
import com.lyj.direction.search_view.tour_MainActivity;

public class main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        Button buttonInsert = (Button)findViewById(R.id.tour);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), tour_MainActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 102);

            }
        });

        Button top_button = (Button)findViewById(R.id.lodging);
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), lodging_MainActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 103);

            }
        });

        Button bb = (Button)findViewById(R.id.restaurant);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), restaurant_MainActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 104);

            }
        });

    }

}