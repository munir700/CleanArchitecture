package android.assignment;

import android.app.Activity;
import android.app.Application;
import android.assignment.di.DaggerNetComponent;
import android.content.Context;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AppApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        DaggerNetComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }


    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }
}
