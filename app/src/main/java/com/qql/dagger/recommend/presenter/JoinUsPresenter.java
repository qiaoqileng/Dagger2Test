package com.qql.dagger.recommend.presenter;

import com.green.dao.GreenDaoManager;
import com.green.dao.output.Dict;
import com.green.dao.output.DictDao;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.model.http.GankHttpResponse;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.DictListContract;
import com.qql.dagger.recommend.presenter.contract.JoinUsContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by qql on 2017/7/29.
 */

public class JoinUsPresenter extends RxPresenter<JoinUsContract.View> implements JoinUsContract.Presenter{
    private final RetrofitHelper mRetrofitHelper;
    private final GreenDaoManager greenDaoManager;

    @Inject
    public JoinUsPresenter(RetrofitHelper mRetrofitHelper, GreenDaoManager greenDaoManager) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.greenDaoManager = greenDaoManager;
    }

    @Override
    public void submit(Map<String, String> params) {
        //  2018/2/27 提交
        Subscription rxSubscription = mRetrofitHelper.joinUs(params)
                .compose(RxUtil.<GankHttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        mView.joinResult("success".equals(result));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("数据提交失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
