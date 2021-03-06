package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.model.bean.CategoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qql on 2017/3/30.
 */

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryBean> datas;
    private Context context;

    public CategoryAdapter(List<CategoryBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public CategoryBean getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_adapter_category,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CategoryBean  categoryBean = datas.get(i);
        String url = categoryBean.getUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).into(holder.imageView);
        } else {
            Glide.with(context).load(R.color.toast_bg).asBitmap().into(holder.imageView);
        }
        holder.textView.setText(categoryBean.getTitle());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.img_category)
        ImageView imageView;
        @BindView(R.id.txt_category)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
