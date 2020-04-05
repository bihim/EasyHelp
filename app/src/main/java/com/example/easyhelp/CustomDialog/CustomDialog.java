package com.example.easyhelp.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.example.easyhelp.R;

public class CustomDialog
{
    private Activity activity;
    private Dialog dialog;
    //..we need the context else we can not create the dialog so get context in constructor
    public CustomDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.dialog_loading);

        //...initialize the imageView form infalted layout


        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */

        //...now load that gif which we put inside the drawble folder here with the help of Glide


        //...finaly show it
        dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideDialog(){
        dialog.dismiss();
    }
}
