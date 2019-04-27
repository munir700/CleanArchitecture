package android.assignment.managers;

import android.app.Application;
import android.assignment.AppApplication;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

public class AppManager {

    private Context appContext;
    private Application application;
    private RefWatcher refWatcher;

    @Inject
    public AppManager(Application application) {
        this.application = application;
        appContext = application.getApplicationContext();
    }

    public Context getContext() {
        return appContext;
    }

    public Application getApplication() {
        return application;
    }

    public void mustDie(Object object) {
        if (refWatcher == null) {
            refWatcher = ((AppApplication) application).getRefWatcher();
        }
        if (refWatcher != null)
            refWatcher.watch(object);
    }


    public void setContext(Context appContext) {
        this.appContext = appContext;
    }

    public String getResourceString(int resourceName) {
        try {
            return getContext().getString(resourceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "dfgd";
    }
}
