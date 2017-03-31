package com.qql.dagger.recommend.presenter.contract;

import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.BBBean;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface BBListContract {
    interface View extends BaseView {

        void showBBList(List<BBBean> bbs);

    }

    interface Presenter extends BasePresenter<View> {

        void findBB(Map<String,String> params);

    }
}
