package com.lyj.direction;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.lyj.direction.R;
import com.lyj.direction.databinding.DialogLayoutBinding;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;
    public LoadingDialog(Activity activity){
        this.activity=activity;
    }
    public void startLoading(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity,R.style.loadingDialogStyle);
        DialogLayoutBinding binding=DialogLayoutBinding.inflate(LayoutInflater.from(activity));
        builder.setView(binding.getRoot());
        builder.setCancelable(false);
    }
    public void stopLoading(){
        if(alertDialog!=null&&alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
