package com.supermeetup.supermeetup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.CategoryAdapter;
import com.supermeetup.supermeetup.callback.UIActionCallback;
import com.supermeetup.supermeetup.databinding.DialogCategoryBinding;
import com.supermeetup.supermeetup.model.Category;

import java.util.ArrayList;

/**
 * Created by Irene on 10/18/17.
 */

public class CategoryDialog extends Dialog implements UIActionCallback {

    private Activity mActivity;
    private DialogCategoryBinding mBinding;

    public CategoryDialog(@NonNull Activity activity, ArrayList<Category> categories) {
        super(activity, R.style.AppTheme_transparent);
        mActivity = activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_category, null, false);
        mBinding.categoryList.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.categoryList.setAdapter(new CategoryAdapter(getContext(), categories, this));
        setContentView(mBinding.getRoot());
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void close() {
        dismiss();
    }
}
