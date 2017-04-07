package com.qql.dagger.recommend.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;
import com.qql.dagger.recommend.presenter.BBPresenter;
import com.qql.dagger.recommend.presenter.MainPresenter;
import com.qql.dagger.recommend.presenter.contract.BBListContract;
import com.qql.dagger.recommend.presenter.contract.MainContract;

import java.util.List;

public class BBListActivity extends BaseActivity<BBPresenter> implements BBListContract.View{

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bb_list;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getCategories();
    }

    @Override
    public void showBBList(List<BBBean> bbs) {

    }

    @Override
    public void showCategories(List<CategoryBean> categories) {

    }

    @Override
    public void showError(String msg) {

    }
}
