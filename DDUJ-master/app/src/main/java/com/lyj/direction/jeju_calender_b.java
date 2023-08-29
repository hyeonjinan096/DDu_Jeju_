package com.lyj.direction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.lyj.direction.jeju_setplan2.ItemModel;
import com.lyj.direction.search_view.lodging_MainActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class jeju_calender_b extends AppCompatActivity {
    TextView datePickerText;
    TextView lodgingText;
    TextView d_day_Text;
    Calendar calendar;
    Button next_button;
    String dateString1;
    String dateString2;
    String start_date;
    String last_date;
    String name;
    String address;
    String y,x;
    long day_gap;
    long d_day;
    Button datePicker;
    private ArrayList<ItemModel> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeju_calender_b);

        datePickerText = findViewById(R.id.date_picker_text);

        lodgingText = findViewById(R.id.my_lodging);
        d_day_Text = findViewById(R.id.d_day);

        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        next_button = findViewById(R.id.next_button);


        /////
        Drawable drawable = getResources().getDrawable(R.drawable.jeju_mini_button);
        //////
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(jeju_calender_b.this,jeju_dayplan.class);
                intent.putExtra("date1",start_date);
                Log.d("date1",dateString1);
                intent.putExtra("date2",last_date);//선택 안했을 때나 날짜 선택했을 때 오류 날 거임
                intent.putExtra("day_gap",day_gap);
                intent.putExtra("lodging_name",name);
                intent.putExtra("lodging_address",address);
                intent.putExtra("x","33.4868");
                intent.putExtra("y","126.3918");
                startActivity(intent);
            }
        });


        //오늘 날짜
        Long today = MaterialDatePicker.todayInUtcMilliseconds();
        //숙소 선택
        datePicker= findViewById(R.id.date_picker_btn);
        datePicker.setBackground(drawable);
        datePicker.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(), lodging_MainActivity.class);//코멘트엑티비티자바랑 연결
            intent.putExtra("calender",200);
            startActivityForResult(intent, 200);
        });

        /*
        //날짜 선택 버튼
        Button datePicker = findViewById(R.id.date_picker_btn);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTheme(R.style.ThemeMaterialCalendar)
                        .setTitleText("여행일자를 선택해주세요")
                        .setSelection(today).build(); //오늘 날짜 셋팅

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Date date = new Date();
                        date.setTime(selection);

                        String dateString = simpleDateFormat.format(date);

                        datePickerText.setText(dateString);
                    }
                });
            }
        });
        */
        //기간 선택 버튼
        Button dateRangePicker = findViewById(R.id.date_range_picker_btn);
        dateRangePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.ThemeMaterialCalendar);

                builder.setTitleText("여행 일자를 선택해주세요");

                //미리 날짜 선택
                builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));

                MaterialDatePicker materialDatePicker = builder.build();

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        SimpleDateFormat simpleDateFormat2_s = new SimpleDateFormat("yyyyMMdd");
                        Date date1 = new Date();
                        Date date2 = new Date();

                        date1.setTime(selection.first);
                        date2.setTime(selection.second);

                        start_date=simpleDateFormat2_s.format(date1);
                        last_date=simpleDateFormat2_s.format(date2);

                        dateString1 = simpleDateFormat.format(date1);
                        dateString2 = simpleDateFormat.format(date2);


                        //일수 계산 코드
                        Date format1 = null;
                        try {
                            format1 = new SimpleDateFormat("yyyy년 MM월 dd일").parse(dateString2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date format2 = null;
                        try {
                            format2 = new SimpleDateFormat("yyyy년 MM월 dd일").parse(dateString1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long diffSec = (format1.getTime() - format2.getTime()) / 1000;
                        day_gap = (diffSec / (24*60*60))+1;

                        Date today = calendar.getTime();
                        long d_day_c = (format2.getTime() - today.getTime()) / 1000;
                        d_day = (d_day_c / (24*60*60))+1;


                        datePickerText.setText(dateString1 + " ~ " + dateString2);
                        d_day_Text.setText("(D-"+d_day+")");

                        dateRangePicker.setText("일정 담기 ☑");
                        dateRangePicker.setTextColor(Color.parseColor("#7E7D7D"));

                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200){
            name = data.getStringExtra("name");
            address = data.getStringExtra("address");
            x = data.getStringExtra("x");
            y = data.getStringExtra("y");
            lodgingText.setText(name);
            datePicker.setText("숙소 담기 ☑");
            datePicker.setTextColor(Color.parseColor("#7E7D7D"));

            //Toast.makeText(v.getContext(), "숙소", Toast.LENGTH_LONG).show();
            list.add(new ItemModel(0, name,address,11,"2",x,y));

        }

    }
}