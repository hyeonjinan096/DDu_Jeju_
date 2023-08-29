package com.lyj.direction;

import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lyj.direction.jeju_setplan2.MainActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class jeju_dayplan_Adapter extends RecyclerView.Adapter<jeju_dayplan_Adapter.CustomViewHolder> {

    private ArrayList<jeju_dayplan_Dictionary> mList;
    private String address,lodging,date,x,y;
    private String next_date;
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected EditText english;

        protected Button button;

        public CustomViewHolder(View view) {

            super(view);
            this.id = (TextView) view.findViewById(R.id.id_listitem);
            this.english = (EditText) view.findViewById(R.id.editText);

            this.button = (Button) view.findViewById(R.id.edit_button);

            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(button.getContext(), "엥", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(v.getContext(), com.lyj.direction.jeju_setplan2.MainActivity.class);
                    intent.putExtra("main_address",address);
                    intent.putExtra("main_lodging",lodging);
                    intent.putExtra("main_date",date); //next_date로 바꾸던지
                    intent.putExtra("main_x",x);
                    intent.putExtra("main_y",y);
                    v.getContext().startActivity(intent);







                    /*
                    Intent intent=new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("lodging_name",name);
                    intent.putExtra("lodging_address",address);
                    startActivity(intent);*/
                }
            });
        }

    }


    public jeju_dayplan_Adapter(ArrayList<jeju_dayplan_Dictionary> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.jeju_dayplan_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


        viewholder.id.setGravity(Gravity.CENTER);
        viewholder.english.setGravity(Gravity.CENTER);


        Log.d("position",""+position);

        viewholder.id.setText(mList.get(position).getId());
        viewholder.english.setText(mList.get(position).getEnglish());
        address=mList.get(position).getAddress();
        lodging=mList.get(position).getEnglish();
        Log.d("숙소",mList.get(position).getEnglish());
        date =mList.get(position).getDate();
        x=mList.get(position).getX();
        y =mList.get(position).getY();
        //next_date = String.valueOf(Integer.parseInt(date)+position-1);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}




