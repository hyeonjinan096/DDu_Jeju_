package com.lyj.direction.jeju_setplan;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyj.direction.R;

import java.util.ArrayList;


public class jeju_setplan_Adapter extends RecyclerView.Adapter<jeju_setplan_Adapter.CustomViewHolder> {

    private ArrayList<jeju_setplan_Dictionary> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView english;

        protected Button button;

        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.jeju_orange);
            this.english = (TextView) view.findViewById(R.id.jeju_listitem);

            this.button = (Button) view.findViewById(R.id.edit_button);


        }

    }


    public jeju_setplan_Adapter(ArrayList<jeju_setplan_Dictionary> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.jeju_setplan_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);


        viewholder.id.setGravity(Gravity.CENTER);
        viewholder.english.setGravity(Gravity.CENTER);




        viewholder.id.setText(mList.get(position).getId());
        viewholder.english.setText(mList.get(position).getEnglish());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}




