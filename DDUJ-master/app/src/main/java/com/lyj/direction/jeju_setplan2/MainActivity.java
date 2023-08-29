package com.lyj.direction.jeju_setplan2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyj.direction.DirectionActivity;
import com.lyj.direction.R;
import com.lyj.direction.search_view.lodging_MainActivity;
import com.lyj.direction.search_view.restaurant_MainActivity;
import com.lyj.direction.search_view.tour_MainActivity;
import com.lyj.direction.u_map1;
import com.lyj.direction.u_map2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RecyclerViewAdapter adapter;

    String x;  // 위도, 경도
    String y;
    String main_x,main_y;


    private static final String TAG = "a";
    RecyclerView recyclerView;
    ArrayList<ItemModel> list = new ArrayList(); //앞에 private없앰
    private int count = -1;
    String start_time,start_time2;
    String last_time,date,last_time2;
    String result_time,result_list;
    int cnt =0; //map1과 map2로 구별해서 가기 위한 변수

    //데이터 리스트 저장을 위한 코드
    private SharedPreferences sharedPreferences;
    private final String PREFS_NAME = "MyPrefs"; // SharedPreferences 이름
    private final String KEY_DATA_LIST = "dataList"; // 데이터 리스트의 키 값
    //
    String main_lodging,main_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeju_setplan2);

        //메인 숙소 초기화 코드
        Intent intent = getIntent();
        main_lodging = intent.getStringExtra("main_lodging");
        main_address = intent.getStringExtra("main_address");
        date =intent.getStringExtra("main_date");
        main_x = intent.getStringExtra("main_x");
        main_y = intent.getStringExtra("main_y");

        Log.d("여행일자",date);




        //버튼 눌른 후 코드
        Button lodging_insert = (Button)findViewById(R.id.lodging_button);//숙소0
        Button tourist_insert = (Button)findViewById(R.id.tourist_button);//관광지1
        Button restaurant_insert = (Button)findViewById(R.id.restaurant_button);//맛집 2
        Button next_button =(Button)findViewById(R.id.next_button);//지도로 넘어가기
        /////시작 시간 다이얼
        final EditText et_time = (EditText) findViewById(R.id.Time);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY) + 9;                //한국시간 : +9
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        start_time =state + " " + selectedHour + "시 " + selectedMinute + "분";
                        start_time2 =state + "_" + selectedHour + "_" + selectedMinute;
                        et_time.setText(start_time);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("출발 시간을 설정하세요.");
                mTimePicker.show();
            }
        });
        /////종료 시간 다이얼
        /*
        final EditText let_time = (EditText) findViewById(R.id.last_Time);
        let_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY) + 9;                //한국시간 : +9
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        last_time =state + " " + selectedHour + "시 " + selectedMinute + "분";
                        last_time2 =state + "_" + selectedHour + "_" + selectedMinute;
                        let_time.setText(last_time);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("종료 시간을 설정하세요.");
                mTimePicker.show();
            }
        });
        */

        /////
        //지도로 보내는 코드(서버 연결)
        //

        //최종서버/ 보내야하는 str
        //1. list = "순서 체류시간 place_id .... 순서 타입...."
        //2. result_list = "여행일자(YYYYMMDD) visit_time_start(PM_h_m)"

        //"사용자아이디 장소아이디a   끝시간 "
        //result_list:장바구니 순서데로 정보 담음
        //result_time ="visit_procedure 타입 이름 주소 체류시간 place_id  .... 순서 타입...."
        //date+start_time(str)+last_time(str) "여행날짜+시작시간+종료시간"정보 따로 보내주기





        next_button.setOnClickListener(v -> {
            saveDataList(list);

            StringBuilder sb = new StringBuilder();
            sb.append("");
            for (int i = 0; i < list.size(); i++) {
                ItemModel item = list.get(i);
                sb.append("순서: "+i) //순서
                        .append("체류시간: ").append(item.getHour())  //체류시간
                        .append("장소아이디: ").append(item.getNumber()) //장소 아이디
                        .append("");
                if (i < list.size() - 1) {
                    sb.append(" ");
                }
            }
            sb.append("");

            result_list = sb.toString();
            Log.d("데이터 목록", result_list);
            //Toast.makeText(MainActivity.this, result_list, Toast.LENGTH_LONG).show();

            result_time =date+" " + start_time2;
            Log.d("time ",result_time);

            //지도로 넘어가기
            Intent intent2=new Intent(MainActivity.this, DirectionActivity.class);
            startActivity(intent2);



        });







        //데이터리스트 저장을 위한 코드

        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter();


        lodging_insert.setOnClickListener(v -> {
            Intent intent2 =new Intent(getApplicationContext(), lodging_MainActivity.class);//코멘트엑티비티자바랑 연결
            startActivityForResult(intent2, 103);



        });
        tourist_insert.setOnClickListener(v -> {
            Intent intent2 =new Intent(getApplicationContext(), tour_MainActivity.class);//화면 이동
            startActivityForResult(intent2, 104);

        });
        restaurant_insert.setOnClickListener(v -> {

            Intent intent2 =new Intent(getApplicationContext(), restaurant_MainActivity.class);//화면 이동
            startActivityForResult(intent2, 105);
            /*
            count++;
            //Toast.makeText(v.getContext(), "맛집", Toast.LENGTH_LONG).show();
            list.add(new ItemModel(2, count+"맛집","주소"));
            adapter.setDataList(list);
            recyclerView.setAdapter(adapter);

             */

        });

        list.add(new ItemModel(0, main_lodging,main_address,0 ,"-1",main_x,main_y)); //숙소 기본 제공
        adapter.setDataList(list);

        ItemTouchHelper.Callback callback = new RecyclerRowMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 103){
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String number = data.getStringExtra("number");
            int time_hour = data.getIntExtra("time_hour",1);
            x = data.getStringExtra("x");
            y = data.getStringExtra("y");

            count++;
            //Toast.makeText(v.getContext(), "숙소", Toast.LENGTH_LONG).show();
            list.add(new ItemModel(0, name,address,time_hour,number,x,y));
            adapter.setDataList(list);
            recyclerView.setAdapter(adapter);
        }
        else if(requestCode == 105){
            String restaurant_name = data.getStringExtra("restaurant_name");
            String restaurant_address = data.getStringExtra("restaurant_address");
            String number = data.getStringExtra("number");
            int time_hour = data.getIntExtra("time_hour",1);
            x = data.getStringExtra("x");
            y = data.getStringExtra("y");

            count++;
            //Toast.makeText(v.getContext(), "숙소", Toast.LENGTH_LONG).show();
            list.add(new ItemModel(1, restaurant_name,restaurant_address,time_hour,number,x,y));
            adapter.setDataList(list);
            recyclerView.setAdapter(adapter);
        }
        else if(requestCode == 104){
            String tour_name = data.getStringExtra("tour_name");
            String tour_address = data.getStringExtra("tour_address");
            String number = data.getStringExtra("number");
            int time_hour = data.getIntExtra("time_hour",1);
            x = data.getStringExtra("x");
            y = data.getStringExtra("y");


            count++;
            //Toast.makeText(v.getContext(), "숙소", Toast.LENGTH_LONG).show();
            list.add(new ItemModel(2, tour_name,tour_address,time_hour,number,x,y));
            adapter.setDataList(list);
            recyclerView.setAdapter(adapter);
        }
    }

    private void saveDataList(ArrayList<ItemModel> dataList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        Log.d("JSOn",json);
        editor.putString("dataList", json); //KEY_DATA_LIST이름으로 json 리스트 저장
        editor.apply();

        Log.d("save",sharedPreferences.getString("dataList", "defaultValue"));
    }





    private ArrayList<ItemModel> loadDataList() {
        String json = sharedPreferences.getString(KEY_DATA_LIST, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            Gson gson = new Gson();

            return gson.fromJson(json, type);
        } else {
            return new ArrayList<ItemModel>();
        }
    }

//


    //array이 받아서 list에 추가하는 함수
    public void setadapter(String type, String name , String adress){
        count++;
        //Toast.makeText(v.getContext(), "맛집", Toast.LENGTH_LONG).show();
        list.add(new ItemModel(Integer.parseInt(type), count+name,adress,11,"1","33.4868","126.3918"));
        return;
    }
    //



    //map으로 intent해야되는 거-> list 나눠서 인텐트,time은 (오전,오후)(시간)(분)으로 나눠서

}