package com.supermeetup.supermeetup.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.common.Util;

/**
 * Created by Irene on 10/17/17.
 */

public class LoadingDialog extends ProgressDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setMessage(Util.getString(getContext(), R.string.load_data));
    }
}
