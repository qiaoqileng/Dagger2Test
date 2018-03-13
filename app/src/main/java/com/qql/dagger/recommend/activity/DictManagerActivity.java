package com.qql.dagger.recommend.activity;

import android.support.v7.widget.RecyclerView;

import com.green.dao.output.Dict;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.presenter.DictListPresenter;
import com.qql.dagger.recommend.presenter.contract.DictListContract;
import com.qql.dagger.recommend.utils.CommonUtils;
import com.qql.dagger.recommend.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 词典管理页
 * Created by qql on 2017/12/15.
 */

public class DictManagerActivity extends BaseActivity<DictListPresenter> implements DictListContract.View{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.simple_recycler_view;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.findDictList();
    }

    @Override
    public void showDictList(List<Dict> dicts) {
        if (!CommonUtils.emptyList(dicts)){
            initView();
        }
    }

    private void initView() {

    }
}
