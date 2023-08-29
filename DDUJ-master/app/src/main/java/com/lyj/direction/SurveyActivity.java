package com.lyj.direction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    private EditText editAge, editGroupSize;
    private RadioGroup radioGender;
    private CheckBox checkbox_1, checkbox_2, checkbox_3, checkbox_4,checkbox_5,checkbox_6,checkbox_7,checkbox_8,checkbox_9,checkbox_10,checkbox_11;
    private Button btnSubmit;
    String result_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        editAge = findViewById(R.id.edit_age);
        editGroupSize = findViewById(R.id.edit_group_size);
        radioGender = findViewById(R.id.radio_gender);
        checkbox_1 = findViewById(R.id.checkbox_1);
        checkbox_2 = findViewById(R.id.checkbox_2);
        checkbox_3 = findViewById(R.id.checkbox_3);
        checkbox_4 = findViewById(R.id.checkbox_4);
        checkbox_5 = findViewById(R.id.checkbox_5);
        checkbox_6 = findViewById(R.id.checkbox_6);
        checkbox_7 = findViewById(R.id.checkbox_7);
        checkbox_8 = findViewById(R.id.checkbox_8);
        checkbox_9 = findViewById(R.id.checkbox_9);
        checkbox_10 = findViewById(R.id.checkbox_10);
        checkbox_11 = findViewById(R.id.checkbox_11);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설문조사 결과를 저장할 문자열
                StringBuilder result = new StringBuilder();

                // 첫번째 질문: 나이
                String age = editAge.getText().toString().trim();
                if (age.isEmpty()) {
                    Toast.makeText(SurveyActivity.this, "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                result.append("").append(age);

                // 두번째 질문: 성별
                int selectedGenderId = radioGender.getCheckedRadioButtonId();
                if (selectedGenderId == -1) {
                    Toast.makeText(SurveyActivity.this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectedGender = findViewById(selectedGenderId);
                result.append(" ").append(selectedGender.getText().toString());

                // 세번째 질문: 인원 수
                String groupSize = editGroupSize.getText().toString().trim();
                if (groupSize.isEmpty()) {
                    Toast.makeText(SurveyActivity.this, "인원 수를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                result.append(" ").append(groupSize);

                // 네번째 질문: 여행 스타일
                StringBuilder travelStyles = new StringBuilder();

                if (checkbox_1.isChecked()) {
                    travelStyles.append("힐링 ");
                }
                if (checkbox_2.isChecked()) {
                    travelStyles.append("액티비티 ");
                }
                if (checkbox_3.isChecked()) {
                    travelStyles.append("관광 ");
                }
                if (checkbox_4.isChecked()) {
                    travelStyles.append("맛집 ");
                }
                if (checkbox_5.isChecked()) {
                    travelStyles.append("힐링 ");
                }
                if (checkbox_6.isChecked()) {
                    travelStyles.append("액티비티 ");
                }
                if (checkbox_7.isChecked()) {
                    travelStyles.append("관광 ");
                }
                if (checkbox_8.isChecked()) {
                    travelStyles.append("맛집 ");
                }
                if (checkbox_9.isChecked()) {
                    travelStyles.append("액티비티 ");
                }
                if (checkbox_10.isChecked()) {
                    travelStyles.append("관광 ");
                }
                if (checkbox_11.isChecked()) {
                    travelStyles.append("맛집 ");
                }

                // 선택된 여행 스타일이 없을 경우
                if (travelStyles.length() == 0) {
                    Toast.makeText(SurveyActivity.this, "여행 스타일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                result.append(" ").append(travelStyles.toString().trim());

                // 설문조사 결과 출력
                Log.d("Survey Result", result.toString());


                Intent intent=new Intent(SurveyActivity.this,jeju_main_page.class);
                startActivity(intent);


                // MainActivity로 결과 전달
                //Intent intent = new Intent();
                //intent.putExtra("result", result.toString());
                //setResult(RESULT_OK, intent);
                //finish();
            }
        });
    }
}
