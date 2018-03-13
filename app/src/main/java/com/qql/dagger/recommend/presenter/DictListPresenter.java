package com.qql.dagger.recommend.presenter;

import com.green.dao.GreenDaoManager;
import com.green.dao.output.Dict;
import com.green.dao.output.DictDao;
import com.green.dao.output.MyBook;
import com.green.dao.output.MyBookDao;
import com.qql.dagger.recommend.base.RxPresenter;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.presenter.contract.BookSelfContract;
import com.qql.dagger.recommend.presenter.contract.DictListContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.utils.RxUtil;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.BookQuery;
import org.geometerplus.fbreader.book.Filter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by qql on 2017/7/29.
 */

public class DictListPresenter extends RxPresenter<DictListContract.View> implements DictListContract.Presenter{
    private final RetrofitHelper mRetrofitHelper;
    private final GreenDaoManager greenDaoManager;

    @Inject
    public DictListPresenter(RetrofitHelper mRetrofitHelper, GreenDaoManager greenDaoManager) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.greenDaoManager = greenDaoManager;
    }

    @Override
    public void findDictList() {
        DictDao dao = greenDaoManager.getDaoSession().getDictDao();
        Subscription rxSubscription = dao.rx().loadAll()
                .compose(RxUtil.<List<Dict>>rxSchedulerHelper())
                .subscribe(new Action1<List<Dict>>() {
                    @Override
                    public void call(List<Dict> dicts) {
                        LogUtil.d(dicts);
                        mView.showDictList(dicts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.printException(throwable);
                        mView.showError("尚未加入任何词库");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
