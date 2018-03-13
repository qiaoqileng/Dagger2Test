package com.qql.dagger.recommend;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.qql.dagger.recommend.component.AppComponent;
import com.qql.dagger.recommend.component.DaggerAppComponent;
import com.qql.dagger.recommend.module.AppModule;
import com.umeng.analytics.MobclickAgent;

import org.geometerplus.android.fbreader.FBReaderApplication;

/**
 * Created by qiao on 2016/11/23.
 */

public class App extends FBReaderApplication {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化屏幕宽高
        getScreenSize();
        //友盟参数初始化
        initUMConfig();
        //数据库初始化
        initDB();
        //第三方登陆初始化
        initThirdSdk();
    }

    private void initThirdSdk() {
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    private void initUMConfig() {
        //友盟统计使用activity和fragment混合统计
        //MobclickAgent.openActivityDurationTrack(true);
        //友盟错误日志捕获
        MobclickAgent.setCatchUncaughtExceptions(true);
        //场景设置
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void initDB() {
    }

    public static AppComponent getAppComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    public void exitApp() {
        ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            mActivityManager.killBackgroundProcesses(this.getPackageName());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
