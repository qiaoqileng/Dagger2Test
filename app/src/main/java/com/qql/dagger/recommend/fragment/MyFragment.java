package com.qql.dagger.recommend.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.activity.DictManagerActivity;
import com.qql.dagger.recommend.activity.JoinUsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qql on 2016/12/22.
 */

public class MyFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.login)
    ImageView imageView;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.inflateMenu(R.menu.menu_scrolling);
        mToolbar.bringToFront();
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setTitle("登录");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(android.R.style.TextAppearance_DialogWindowTitle);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(android.R.style.TextAppearance_DialogWindowTitle);
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.login,R.id.join_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                // TODO: 2017/12/15 登陆
                break;
            case R.id.join_in:
                //  2017/12/15 申请入驻
                startActivity(new Intent(getActivity(),JoinUsActivity.class));
                break;
        }
    }
}
