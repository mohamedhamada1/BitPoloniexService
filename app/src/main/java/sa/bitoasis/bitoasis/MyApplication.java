package sa.bitoasis.bitoasis;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import sa.bitoasis.bitoasis.di.AppInjector;
import sa.bitoasis.bitoasis.utils.LocaleManager;


public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init(this);
       setUILanguage("en");

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public void setUILanguage(String lang) {
        LocaleManager.setNewLocale(getBaseContext(), lang);
    }

    @Override
    protected void attachBaseContext(Context base) {
      super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}
