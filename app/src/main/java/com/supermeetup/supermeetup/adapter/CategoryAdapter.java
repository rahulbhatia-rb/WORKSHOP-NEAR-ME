package com.supermeetup.supermeetup.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.callback.UIActionCallback;
import com.supermeetup.supermeetup.databinding.ItemCategoryBinding;
import com.supermeetup.supermeetup.dialog.CategoryDialog;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.adapter.viewholder.CategoryViewHolder;

import java.util.ArrayList;

/**
 * Created by yuxin on 10/14/17.
 */

public class CategoryAdapter extends BaseAdapter {
    private static final int VIEWTYPE_ITEM = 0;
    private static final int VIEWTYPE_END = 1;

    private Context mContext;
    private int mSize;
    private UIActionCallback mCallback;

    public CategoryAdapter(Context context, ArrayList<Category> categories, UIActionCallback callback){
        this(context, categories, categories.size(), callback);
    }

    public CategoryAdapter(Context context, ArrayList<Category> categories, int size, UIActionCallback callback){
        mContext = context;
        mModels = categories;
        mSize = size;
        mCallback = callback;
    }

    @Override
    public int getItemViewType(int position){
        if(mSize == mModels.size()){
            return VIEWTYPE_ITEM;
        }else if(position < 7 ){
            return VIEWTYPE_ITEM;
        }else{
            return VIEWTYPE_END;
        }
    }

    @Override
    public int getItemCount() {
        if(mModels == null || mModels.size() == 0){
            return 0;
        }else if(mModels.size() == mSize){
            return mSize;
        }else if(mModels.size() > 7){
            return 8;
        }else{
            return mModels.size() + 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEWTYPE_END:
                return new CategoryEndViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false)));
            case VIEWTYPE_ITEM:
                return new CategoryViewHolder((ItemCategoryBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false), (ArrayList<Category>)mModels, mCallback);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType){
            case VIEWTYPE_ITEM:
                ((CategoryViewHolder) holder).bind((Category)mModels.get(position));
                break;
            case VIEWTYPE_END:
                ((CategoryEndViewHolder) holder).bind();
                break;

        }
    }

    class CategoryEndViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImage;
        private TextView mTitle;
        private View mItemView;

        public CategoryEndViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.category_image);
            mTitle = (TextView) itemView.findViewById(R.id.category_title);
            mItemView = itemView;
        }

        public void bind(){
            mImage.setImageResource(R.mipmap.ic_more);
            mTitle.setText(R.string.more);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CategoryDialog categoryDialog = new CategoryDialog((Activity)mContext, (ArrayList<Category>)mModels);
                    categoryDialog.show();
                }
            });
        }
    }
}
