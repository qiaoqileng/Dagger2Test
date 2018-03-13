package com.qql.dagger.recommend.presenter.contract;

import com.green.dao.output.Dict;
import com.qql.dagger.recommend.base.BasePresenter;
import com.qql.dagger.recommend.base.BaseView;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.CategoryBean;

import java.util.List;
import java.util.Map;

/**
 * Created by qql on 2016/12/22.
 */

public interface DictListContract {
    interface View extends BaseView {

        void showDictList(List<Dict> dicts);
    }

    interface Presenter extends BasePresenter<View> {

        void findDictList();
    }
}
