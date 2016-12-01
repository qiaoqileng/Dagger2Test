package com.qql.dagger.recommend.module;


import com.green.dao.GreenDaoManager;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.annotation.ContextLife;
import com.qql.dagger.recommend.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }

    @Provides
    @Singleton
    GreenDaoManager provideGreenDaoManager() {
        return new GreenDaoManager(application);
    }
}
