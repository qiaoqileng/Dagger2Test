package com.qql.dagger.recommend.component;

import android.content.Context;

import com.green.dao.GreenDaoManager;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.annotation.ContextLife;
import com.qql.dagger.recommend.cache.DataCache;
import com.qql.dagger.recommend.model.http.RetrofitHelper;
import com.qql.dagger.recommend.module.AppModule;
import com.qql.dagger.recommend.repository.UserRepository;
import com.tencent.tauth.Tencent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by qiao on 2016/11/23.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    GreenDaoManager provideGreenDaoManager();    //提供数据库帮助类

    DataCache provideDataCache();    //提供缓存类

    Tencent provideThirdSDK(); // 第三方登陆
}