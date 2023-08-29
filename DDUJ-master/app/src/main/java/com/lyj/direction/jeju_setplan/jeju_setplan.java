package com.lyj.direction.jeju_setplan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyj.direction.R;
import com.lyj.direction.search_view.lodging_MainActivity;
import com.lyj.direction.search_view.restaurant_MainActivity;
import com.lyj.direction.search_view.tour_MainActivity;

import java.util.ArrayList;


public class jeju_setplan extends AppCompatActivity {

    private ArrayList<jeju_setplan_Dictionary> mArrayList;
    private jeju_setplan_Adapter mAdapter;
    private int count = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeju_setplan);



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_jeju_list2);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();

        mAdapter = new jeju_setplan_Adapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);





        Button lodging_button = (Button)findViewById(R.id.lodging_button);
        lodging_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), lodging_MainActivity.class);//코멘트엑티비티자바랑 연결
                startActivityForResult(intent, 103);

                count++;

                jeju_setplan_Dictionary data = new jeju_setplan_Dictionary(count+"", count+"숙소");

                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
                mAdapter.notifyDataSetChanged();

            }
        });


        Button restaurant_button = (Button)findViewById(R.id.restaurant_button); //restaurant 눌렀을 때
        restaurant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), restaurant_MainActivity.class);//화면 이동
                startActivityForResult(intent, 104);

            }
        });

        Button tourist_button = (Button)findViewById(R.id.tourist_button); //tourist_button 눌렀을 떄
        tourist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), tour_MainActivity.class);//화면 이동
                startActivityForResult(intent, 102);

            }
        });

    }

}