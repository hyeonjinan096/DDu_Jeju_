package com.lyj.direction.TMAP;//drF1J0gc4F9EIlwm9ZJxS6xnDCRgImWO9KybwxgN

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lyj.direction.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
public class TMapActivity extends AppCompatActivity {
    TMapView tMapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       View view= findViewById(R.id.tmap);
       // setContentView(linearLayoutTmap);
        setContentView(R.layout.activity_tmap);
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("drF1J0gc4F9EIlwm9ZJxS6xnDCRgImWO9KybwxgN");
       // view.addView(tMapView);


       FindWay();
    }


    void FindWay() {
        TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
        TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)

        try {
            TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
            tMapPolyLine.setLineColor(Color.BLUE);
            tMapPolyLine.setLineWidth(2);
            tMapView.addTMapPolyLine("Line1", tMapPolyLine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
