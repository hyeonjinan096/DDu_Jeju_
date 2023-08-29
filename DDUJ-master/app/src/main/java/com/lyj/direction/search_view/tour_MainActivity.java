package com.lyj.direction.search_view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyj.direction.R;

import java.util.ArrayList;
import java.util.List;

public class tour_MainActivity extends AppCompatActivity implements tour_ItemAdapter.onItemListener {

    private static final String TAG ="" ;
    private tour_ItemAdapter adapter;
    private List<tour_ItemModel> itemList;


    String[][] arr ={ {"요트투어", "제주특별자치도 제주시 진군남4길 5 2층", "1","33.2102","126.2569"},
            {"한라서적타운", "제주특별자치도 서귀포시 대청로25번길 4 4층 402호", "2","33.5054",	"126.5384"},
            {"휴애리수국축제", "제주특별자치도 서귀포시 남원읍 신례동로 256", "3","33.5054",	"126.5384"},
    };  //데이터 배열로 받고


    int page =0;
    //List<String> list = Arrays.asList(arr);  //리스트화 해서
    int time_hour=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_activity_main);

        //여기에 서버 연결 함수 쓸 것
        //connection.setRequestProperty("function","select");
        //connection.setRequestProperty("table_name", "tour_list");
        //arr에 받고 처리

        setUpRecyclerView();

    }

    /****************************************************
     리사이클러뷰, 어댑터 셋팅
     ***************************************************/
    private void setUpRecyclerView() {
        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //adapter
        itemList = new ArrayList<>(); //샘플테이터
        fillData();
        adapter = new tour_ItemAdapter(itemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //밑줄
        recyclerView.addItemDecoration(dividerItemDecoration);

        //데이터셋변경시
        //adapter.dataSetChanged(exampleList);

        //어댑터의 리스너 호출
        adapter.setOnClickListener(this);
    }




    private void fillData() {

        for(int i =0;i<3;i++){
            String imageName = "img" + (i + 1);//파일 이름 생성
            int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName()); // 이미지 리소스 ID 가져오기
            itemList.add(new tour_ItemModel(resourceId, arr[i][1], arr[i][0], arr[i][2], arr[i][3],arr[i][4]));
            Log.d(TAG,arr[i][0]);
        }


    }

    /****************************************************
     onCreateOptionsMenu SearchView  기능구현
     ***************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



//////////////////////
    /****************************************************
     리사이클러뷰 클릭이벤트 인터페이스 구현
     ***************************************************/
    @Override
    public void onItemClicked(int position) {
        //1.팝업에서 시간이랑 저장여부 묻기
        showNumberInputDialog(position);


    }



    private void showNumberInputDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.RoundedDialog);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.u_dialog_number_input, null);
        final EditText numberEditText = dialogView.findViewById(R.id.numberEditText);

        Button positiveButton = dialogView.findViewById(R.id.positiveButton);
        Button negativeButton = dialogView.findViewById(R.id.negativeButton);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberString = numberEditText.getText().toString();
                time_hour = Integer.parseInt(numberString);    //time_hour에 시간 저장함
                dialog.dismiss();

                //2.저장하기 (falg ==1)->intent데이터를 담고 넘어가기

                Log.d("position", "{" + position);
                Intent intent = new Intent();//코멘트엑티비티자바랑 연결
                intent.putExtra("tour_name", arr[position][0]);//array가 잘 넘어가는지모르곘음
                intent.putExtra("tour_address", arr[position][1]);
                intent.putExtra("number", arr[position][2]);
                //체류 시간 intent
                intent.putExtra("time_hour", time_hour);

                //경도,위도 intent
                intent.putExtra("x", arr[position][3]);
                intent.putExtra("y", arr[position][4]);

                setResult(104, intent);

                finish();



            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


}
