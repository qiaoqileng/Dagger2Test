package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.Dict;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface JoinUsContract {
    interface View extends BaseView {

        void joinResult(boolean success);
    }

    interface Presenter extends BasePresenter<View> {

        void submit(Map<String,String> params);
    }
}
