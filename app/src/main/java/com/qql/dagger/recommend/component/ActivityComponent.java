package com.qql.dagger.recommend.component;

import android.app.Activity;


import com.qql.dagger.recommend.activity.BBListActivity;
import com.qql.dagger.recommend.activity.DictManagerActivity;
import com.qql.dagger.recommend.activity.HomeActivity;
import com.qql.dagger.recommend.activity.ImageDetailActivity;
import com.qql.dagger.recommend.activity.JoinUsActivity;
import com.qql.dagger.recommend.activity.MainActivity;
import com.qql.dagger.recommend.annotation.ActivityScope;
import com.qql.dagger.recommend.module.ActivityModule;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(ImageDetailActivity activity);

    void inject(HomeActivity activity);

    void inject(BBListActivity activity);

    void inject(DictManagerActivity dictManagerActivity);

    void inject(JoinUsActivity joinUsActivity);
}
