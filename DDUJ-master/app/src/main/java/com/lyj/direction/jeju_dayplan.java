package com.lyj.direction;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class jeju_dayplan extends AppCompatActivity {

    ///intent 실험 코드

    ///
    String lodging_name;
    String lodging_address;
    String start_date;
    String last_date;
    String next_date;
    String x,y;

    private ArrayList<jeju_dayplan_Dictionary> mArrayList;
    private jeju_dayplan_Adapter mAdapter;
    private int list_count = 0;

    int f=0; //임시 flag
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeju_dayplan);

        //intent테스트
        Intent intent = getIntent();
        start_date = intent.getStringExtra("date1");
        //Log.d("시작 날짜",start_date);
        last_date = intent.getStringExtra("date2");  //날짜 전달 받음
        lodging_name = intent.getStringExtra("lodging_name");
        lodging_address = intent.getStringExtra("lodging_address"); //메인 숙소랑 주소 전달 받음
        x = intent.getStringExtra("x");
        y = intent.getStringExtra("y");
        Log.d("dayplan에서 x",x);
        long day_gap = intent.getLongExtra("day_gap",0);


        //daterange1.setText(date1);
        //daterange2.setText(Integer.toString((int)day_gap));

        //day_gab 제대로 왔는지 확인-완료
        //Toast.makeText(getApplicationContext(),String.valueOf(day_gap),Toast.LENGTH_SHORT).show();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();


        mAdapter = new jeju_dayplan_Adapter( mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        //Toast.makeText(getApplicationContext(),String.valueOf(day_gap),Toast.LENGTH_SHORT).show();
        for (int i = 1; i <= (int) day_gap; i++) {
            add_list();
        }



        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_list();

            }
        });



    }


    public void add_list(){
        list_count++;
        //Log.d("start",start_date);
        next_date = String.valueOf(Integer.parseInt(start_date)+list_count-1);
        //Log.d("next_date",next_date);  //이부분에서 반복이되는데 이유가 뭔지 확인 할 것
        jeju_dayplan_Dictionary data = new jeju_dayplan_Dictionary("day "+list_count, " "+lodging_name+" ",lodging_address,start_date,x,y);

        //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

        mAdapter.notifyDataSetChanged();
    }



}